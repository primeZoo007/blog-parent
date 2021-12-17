package com.zyg.blog.controller;

import com.zyg.blog.service.LoginService;
import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;
    @PostMapping
    public Result register(@RequestBody LoginParams loginParams){
        // 实现sso
        return loginService.register(loginParams);
    }
}
