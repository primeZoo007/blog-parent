package com.zyg.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zyg.blog.dao.mapper.SysUserMapper;
import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.service.LoginService;
import com.zyg.blog.service.SysUserService;
import com.zyg.blog.utils.JWTUtils;
import com.zyg.blog.vo.ErrorCode;
import com.zyg.blog.vo.LoginUserVo;
import com.zyg.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private LoginService loginService;
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

    @Override
    public Result findUserByToken(String token) {
        /**
         * 首先token 合法性校验
         * 1。是否为空，redis是否存在，解析是否成功
         *
         *
         */
        SysUser sysUser = loginService.checkToken(token);
        System.out.println(sysUser);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvator(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit "+ 1);
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        // id 会自动生成
        // 用韵雪花算法
        this.sysUserMapper.insert(sysUser);
    }
}
