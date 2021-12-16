package com.zyg.blog.service;

import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);
}
