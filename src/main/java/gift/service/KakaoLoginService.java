package gift.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import gift.config.KakaoProperties;
import gift.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
public class KakaoLoginService {
    @Autowired
    private KakaoProperties properties;

    private RestTemplate client = new RestTemplateBuilder().build();

    public String kakaoLogin() {
        return properties.loginUrl() + properties.redirectUrl()
                + "&client_id=" + properties.clientId();
    }

    public String getAccessToken(String authorizationCode) {
        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        var body = new LinkedMultiValueMap<String, String>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", properties.clientId());
        body.add("redirect_uri", properties.redirectUrl());
        body.add("code", authorizationCode);

        var request = new RequestEntity<>(body, headers, HttpMethod.POST, URI.create(properties.tokenUrl()));
        var response = client.exchange(request, String.class);

        if (!response.getStatusCode().equals(HttpStatus.OK)) {
            HttpStatus httpStatus = HttpStatus.resolve(response.getStatusCode().value());
            throw CustomException.invalidAPIException(httpStatus);
        }

        return extractAccessToken(response.getBody());
    }

    private String extractAccessToken(String responseBody) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);
        return jsonObject.get("access_token").getAsString();
    }
}
