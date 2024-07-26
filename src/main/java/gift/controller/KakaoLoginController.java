package gift.controller;

import gift.service.KakaoLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/kakao")
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    public KakaoLoginController(KakaoLoginService kakaoLoginService) {
        this.kakaoLoginService = kakaoLoginService;
    }

    @GetMapping("/login")
    public String kakaoLogin() {
        return "redirect:" + kakaoLoginService.kakaoLogin();
    }

    @GetMapping
    public String kakaoLoginVerify(@RequestParam("code") String authorizationCode, Model model) {
        String accessToken = kakaoLoginService.getAccessToken(authorizationCode);
        model.addAttribute("accessToken", accessToken);

        return "kakaoLogin";
    }

}
