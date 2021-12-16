package com.zyg.blog.service;

import com.zyg.blog.dao.pojo.SysUser;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);
}
