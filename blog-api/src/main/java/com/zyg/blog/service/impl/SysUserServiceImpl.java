package com.zyg.blog.service.impl;

import com.zyg.blog.dao.mapper.SysUserMapper;
import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public SysUser findUserById(Long id){
        return sysUserMapper.selectById(id);
    }
}
