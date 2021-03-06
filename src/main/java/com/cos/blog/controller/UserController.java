package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용

@Api(description = "사용자 Controller")
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    // 로그인 페이지
    @ApiOperation(value = "로그인 페이지", notes = "로그인 페이지로 이동합니다.")
    @GetMapping("/auth/signin")
    public String signInForm(Model model) {

        return "users/signInForm";
    }

    // 회원가입 페이지
    @ApiOperation(value = "회원가입 페이지", notes = "회원가입 페이지로 이동합니다.")
    @GetMapping("/auth/signup")
    public String signUpForm() {

        return "users/signUpForm";
    }

    // 회원 정보 수정 페이지
    @ApiOperation(value = "회원 정보수정 페이지", notes = "회원 정보수정 페이지로 이동합니다.")
    @GetMapping("/users/form")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principal, Model model) {

        model.addAttribute("principals", principal);

        return "users/updateForm";
    }


}

    /*private static final String KAKAO_REDIRECT_URI = "http://localhost:8000/auth/kakao/callback";
    private static final String KAKAO_RESPONSE_TYPE = "code";
    private static final String KAKAO_TOKEN_REQUEST = "https://kauth.kakao.com/oauth/token";
    private static final String KAKAO_PROFILE_REQUEST = "https://kapi.kakao.com/v2/user/me";

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Value("${kakao-client.key}")
    private String KAKAO_CLIENT_KEY;

    // 카카오 로그인 Callback
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {

        // POST 방식으로 key=value 데이터를 요청 (카카오 서버로)
        // Retrofit2, OkHttp, RestTemplate
        // 토큰 요청
        RestTemplate restTemplate = new RestTemplate();

        // HttpHeader 오브젝트 생성 (헤더에 데이터 타입을 명시)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpBody 오브젝트 생성
        MultiValueMap<String , String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_CLIENT_KEY);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", code);

        // HeadHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String ,String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        // Http 요청 - POST 방식 - Response 변수의 응답 받음
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                KAKAO_TOKEN_REQUEST,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(responseEntity.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("카카오 액세스 토큰 : " + oAuthToken.getAccess_token());

        // 프로필 요청
        RestTemplate restTemplate2 = new RestTemplate();

        // HttpHeader 오브젝트 생성 (헤더에 데이터 타입을 명시)
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + oAuthToken.getAccess_token());
        headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HeadHeader를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String ,String>> kakaoProfileRequest = new HttpEntity<>(headers2);

        // Http 요청 - POST 방식 - Response 변수의 응답 받음
        ResponseEntity<String> responseEntity2 = restTemplate2.exchange(
                KAKAO_PROFILE_REQUEST,
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        System.out.println(responseEntity2.getBody());

        // Gson, Json Simple, ObjectMapper
        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;
        try {
            kakaoProfile = objectMapper2.readValue(responseEntity2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 카카오에서 뽑아올 내용
        System.out.println("카카오 시퀀스 번호 : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());
        System.out.println("카카오 닉네임 : " + kakaoProfile.getKakao_account().getProfile().getNickname());

        // 블로그 서버에 저장할 내용
        System.out.println("블로그 서버 이메일 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
        System.out.println("블로그 서버 패스워드 : " + cosKey);
        System.out.println("블로그 서버 닉네임 : " + kakaoProfile.getKakao_account().getProfile().getNickname());

        String kakaoEmail = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();
        String kakaoPassword = cosKey;
        String kakaoUsername = kakaoProfile.getKakao_account().getProfile().getNickname();
        String kakaoOauth = "KAKAO";

        UserSaveRequestDto kakaoDto = new UserSaveRequestDto();
        kakaoDto.getKakaoInfo(kakaoEmail, kakaoPassword, kakaoUsername, kakaoOauth);

        // 가입자 혹은 비가입자 체크 해서 처리
        User user = userService.findByEmail(kakaoDto.getEmail());

        // 빈 객체를 가져오는 경우 == 신규 회원
        if(user.getEmail() == null) {
            System.out.println("새로운 회원 : 회원가입 처리");
            userService.signUp(kakaoDto);
        }

        // 자동 로그인 처리
        System.out.println("자동 로그인 처리");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoDto.getEmail(), cosKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }*/
