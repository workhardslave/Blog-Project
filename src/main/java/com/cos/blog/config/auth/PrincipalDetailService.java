package com.cos.blog.config.auth;

import com.cos.blog.domain.User;
import com.cos.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Bean 등록
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌 때, username(email), password 변수 2개를 가로채는데,
    // password 부분 처리는 알아서 함
    // 해당 username(email)이 DB에 있는지만 확인해서 return
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("email : " + email); // 요거 해결
        // 람다식
        User principal = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다 : " + email));
        System.out.println("principal.getEmail() = " + principal.getEmail());
        return new PrincipalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장이 된다.
    }
}
