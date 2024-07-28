package gift.controller;

import gift.service.KakaoApiService;
import gift.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/kakao")
public class KakaoApiController {
    private final KakaoApiService kakaoApiService;
    private final UserService userService;

    public KakaoApiController(KakaoApiService kakaoApiService, UserService userService) {
        this.kakaoApiService = kakaoApiService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String kakaoLogin() {
        return "redirect:" + kakaoApiService.kakaoLogin();
    }

    @GetMapping
    public String kakaoLoginVerify(@RequestParam("code") String authorizationCode, Model model) {
        String accessToken = kakaoApiService.getAccessToken(authorizationCode);
        String email = kakaoApiService.getEmailFromToken(accessToken);
        userService.kakaoSign(email);
        model.addAttribute("accessToken", accessToken);

        return "kakaoLoginSuccess";
    }

}
