package com.zyg.blog.service;

import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.params.LoginParams;

public interface LoginService {
    Result login(LoginParams loginParams);
}
