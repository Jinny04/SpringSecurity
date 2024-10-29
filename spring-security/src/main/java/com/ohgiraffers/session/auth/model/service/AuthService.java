package com.ohgiraffers.session.auth.model.service;

import com.ohgiraffers.session.user.model.dto.UserDTO;
import com.ohgiraffers.session.user.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/* 설명. UserDetailsService:
 *  Spring Security에서 사용자의 아이디(username)를 인증하기 위해 제공하는 인터페이스다.
 *  loadUserByUsername() 메소드를 필수로 구현해야 하며
 *  로그인 인증 시 해당 메소드에 login을 요청할 때 전달된 사용자의 아이디(form 태그의 username)을 매개변수로 하여
 *  DB에서 사용자 정보를 조회한다.
 *  */
@Service
public class AuthService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public AuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UserDTO foundUser = userService.findByName(username);

        if (Objects.isNull(foundUser)) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        return foundUser;
    }
}
