package com.zyg.blog.service;

import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.UserVo;

public interface SysUserService {
    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long authorId);
}
