package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

    // 로그인 페이지
    @GetMapping("/auth/signin")
    public String signInForm() {

        return "users/signInForm";
    }

    // 회원가입 페이지
    @GetMapping("/auth/signup")
    public String signUpForm() {

        return "users/signUpForm";
    }

    // 회원 정보 수정 페이지
    @GetMapping("/users/form")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal, Model model) {

        model.addAttribute("principals", principal);

        return "users/updateForm";
    }


}
