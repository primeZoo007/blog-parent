package com.zyg.blog.service;

import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.params.LoginParams;

public interface LoginService {
    Result login(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);
}
