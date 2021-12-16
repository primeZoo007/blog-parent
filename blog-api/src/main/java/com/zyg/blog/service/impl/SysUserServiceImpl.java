package com.zyg.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyg.blog.dao.mapper.SysUserMapper;
import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.service.SysUserService;
import com.zyg.blog.vo.Result;
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

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        // 判断是否有一样的
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getNickname,SysUser::getAvatar);
        queryWrapper.last("limit "+ 1);
        return sysUserMapper.selectOne(queryWrapper);
    }
}
