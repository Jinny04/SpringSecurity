package com.ohgiraffers.session.config;

import com.ohgiraffers.session.auth.model.UserRole;
import com.ohgiraffers.session.exception.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/* 설명. Spring Security 사용자 정의 (customization):
 *  Spring Security를 활성화 시키면 디폴트 설정에 의해 Root Context(/) 요청 시, 디폴트 로그인 페이지로 강제 리다이렉트 된다.
 *  이를 수정하고 우리만의 보안 규칙을 정의하고자 Spring Security 전용 설정 클래스를 정의할 것이다.
 * */

/* 설명. 결국 Spring Security 관련 설정도 IoC 컨테이너가 인식해야 하는 설정 정보이므로 해당 어노테이션이 필요하다. */
@Configuration
/* 설명. @EnableWebSecurity
 *  Spring Security 설정을 활성화시키는 어노테이션.
 *  (모든 요청 URL이 Spring Security의 감시 하에 놓이며, Spring Security Filter-Chain에 따라 특정 요청을 직접 제어하게 됨)
 *  기본적인 웹 기반 보안 기능들을 제공하며, 개발자는 HttpSecurity를 사용해 보안 설정을 커스터마이징 할 수 있다.
 *  이를 위해 아래에서 filterChainConfigure() 메소드를 구현할 것이다.
 * */
@EnableWebSecurity
public class SecurityConfig {

    private AuthFailHandler authFailHandler;

    @Autowired
    public SecurityConfig(AuthFailHandler authFailHandler) {
        this.authFailHandler = authFailHandler;
    }

    /* 목차. 1-1. 사용자의 비밀번호를 BCrypt 암호화하기 위한 Bean 설정
     *  =================================================================================================
     *  BCryptPasswordEncoder를 사용한 이유(면접 질문 대비용)
     *   : BCrypt는 비밀번호 해싱(hashing)에 가장 많이 사용되는 알고리즘 중 하나이다.
     *   1. 보안성 : 해시 함수에 무작위 솔트(salt)를 적용하여 생성한다.
     *   2. 비용 증가 : 매개변수에 값을 주면 암호 생성 시간을 조절할 수 있어 무차별 공격을 어렵게 한다.
     *   3. 호환성 : 높은 보안 수준 및 데이터베이스에 저장하기 쉬운 특징.
     *   4. 알고리즘 신뢰성 : 논의 평가를 거친 알고리즘으로 보안 관련 로직에 문제없이 계속 사용 중.
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /* 목차. 1-2. @Bean WebSecurityCustomizer:
     *  WebSecurityCustomizer는 Spring Security의 WebSecurity 객체를 커스터마이징하는데 사용된다.
     *  일반적인 위치에 있는 정적 리소스에 대한 요청을 Spring Security에서 무시하도록 하는 설정.
     *  이는 CSS, JavaScript, 이미지 파일 등 정적 리소스에 대한 접근을 항상 허용되도록 설정하는 것이 일반적이다.
     *  (메서드 내에서 web 변수로 WebSecurity 객체에 접근할 수 있다)
     * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        /* 설명. 여기서 말하는 정적 리소스의 위치는 : "src/main/resources/static"*/
        return web -> web.ignoring()
                         .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /* 목차. 1-3. 해당 메서드에서 애플리케이션에 특정한 보안 설정을 정의한다.
     *  HttpSecurity 객체를 사용하여 다양한 보안 설정을 커스터마이징 할 수 있지만, 여기서는 아래와 같은 설정만 진행한다.
     *   1. 접근 제어 : 어떤 URL이 보호되는지, 어떤 역할/권한이 어떤 URL에 접근할 수 있는지 등
     *   2. 로그인 관리 : 사용자 정의 로그인 페이지, 로그인 성공/실패 핸들러 설정 등
     *   3. 로그아웃 관리 : 로그아웃 URL, 성공 핸들러, 로그아웃 후 쿠키 삭제 등
     *   4. 세션 관리 : 세션 고정 보호, 세션 만료, 세션 최대 허용 수 등
     *   5. CSRF : Cross-Site Request Forgery 보호 설정
     * */
    @Bean
    public SecurityFilterChain filterChainConfigure(HttpSecurity http) throws Exception {

        // #1. 접근 제어 : 서버의 리소스에 접근 가능한 권한을 URL 별로 매칭하여 설정.
        http.authorizeHttpRequests(auth -> {
            // 로그인, 회원가입, 실패 페이지와 Root Context는 모두에게 허용
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail", "/").permitAll();
            // "/admin/*" 엔드포인트는 "ADMIN" 권한을 가진 사용자만 접근 허용
            auth.requestMatchers("/admin/*").hasAnyAuthority(UserRole.ADMIN.getRole());
            // "/user/*" 엔드포인트는 "USER" 권한을 가진 사용자만 접근 허용
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole(), UserRole.ADMIN.getRole());
            // 나머지 요청은 모두 인증된(로그인한) 사용자만 접근 가능
            auth.anyRequest().authenticated();
        // #2. 로그인 관리 : <form>태그를 사용한 로그인(form-login) 관련 설정
        }).formLogin(login -> {
            // 로그인 페이지 경로 설정(로그인 페이지에 해당되는 핸들러 매핑이 존재해야 함)
            login.loginPage("/auth/login");
            // 사용자 ID 입력 필드(form 데이터 input의 name 속성과 일치)
            login.usernameParameter("username");
            // 사용자 PW 입력 필드(form 데이터 input의 name 속성과 일치)
            login.passwordParameter("password");
            // 로그인 성공 시 이동할 기본 페이지(로그인 성공 페이지에 해당되는 핸들러 매핑이 존재해야 함)
            login.defaultSuccessUrl("/", true);
            // 로그인 실패 시, 해당 예외를 처리할 핸들러 지정(직접 제작한 핸들러 사용)
            login.failureHandler(authFailHandler);
        // #3. 로그아웃 관리 :  로그아웃 요청 시 관련 처리 설정
        }).logout(logout -> {
            // 로그아웃 경로 설정
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            // 로그아웃 시, 클라이언트의 JSESSIONID 쿠키 삭제
            logout.deleteCookies("JSESSIONID");
            // 세션을 무효화(소멸)하는 설정
            logout.invalidateHttpSession(true);
            // 로그아웃 성공 시 이동할 페이지의 URL 경로
            logout.logoutSuccessUrl("/");
        // #4. 세션 관리 : 애플리케이션 내 세션 관리 설정
        }).sessionManagement(session -> {
            // 동시 세션 수(허용 개수)를 1개로 제한
            session.maximumSessions(1);
            // 세션 만료 시 이동할 페이지 URL 경로
            session.invalidSessionUrl("/");
        // #5. CSRF 설정
        }).csrf(csrf ->
            // CSRF 보호 비활성화
            csrf.disable()
        );

        return http.build();
    }
}
