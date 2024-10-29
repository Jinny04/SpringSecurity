package com.ohgiraffers.session.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping(value = {"/", "/main"})
    public ModelAndView main(ModelAndView mv) {
        mv.setViewName("main/main");
        return mv;
    }

    /* 설명. 관리자 권한 설정 체크 */
    @GetMapping("/admin/page")
    public ModelAndView adminPage(ModelAndView mv) {
        mv.setViewName("admin/admin");
        return mv;
    }

    /* 설명. 유저 권한 설정 체크 */
    @GetMapping("/user/page")
    public ModelAndView user(ModelAndView mv) {
        mv.setViewName("user/user");
        return mv;
    }
}
