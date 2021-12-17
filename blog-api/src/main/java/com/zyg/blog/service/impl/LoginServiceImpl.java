package com.zyg.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.service.LoginService;
import com.zyg.blog.service.SysUserService;
import com.zyg.blog.utils.JWTUtils;
import com.zyg.blog.vo.ErrorCode;
import com.zyg.blog.vo.Result;
import com.zyg.blog.vo.params.LoginParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String,String>  redisTemplate;
    private static final String slat = "#$%zyg950829";
    @Override
    public Result login(LoginParams loginParams) {
        /**
         * 1.检查 参数是否合法
         * 2。根据用户命和密码去user表中哈熏是否存在
         * 3。如果不存在登陆失败
         * 4。如果存在使用jwt生成token 返回给前端
         * 5。token放入redis中，redis token user 信息设置过期时间
         *
         *
         */
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        password = DigestUtils.md5Hex(password+slat);
        System.out.println(password);
        SysUser sysUser = sysUserService.findUser(account,password);
        if(sysUser == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(),ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());
        redisTemplate.opsForValue().set("Token_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    @Override
    public SysUser checkToken(String token) {
        if (StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("Token_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("Token"+token);
        return Result.success(null);
    }

    @Override
    /**
     * 注册功能
     * 1。判断参数是否合法
     * 2。判断账户是否存在，账户已经被注册
     * 3。如果账户不存在，注册用户
     * 4。生成token
     * 5。存入redis，并返回
     * 6。注意加上事务，一旦中间出现问题，注册的用户需要回滚
     */
    public Result register(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        if(StringUtils.isBlank(account)||
                StringUtils.isBlank(password)||
                StringUtils.isBlank(nickname)){
            // 判断参数是否为空
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
       SysUser sysUser = sysUserService.findUserByAccount(account);
        if(sysUser!=null){
            return Result.fail(1005,"当前用户名已存在");
        }
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+slat));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setAvatar("/static/img/logo.b3a48c0.png");
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt("");
        sysUser.setStatus("");
        sysUser.setEmail("");
        this.sysUserService.save(sysUser);

        //token
        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("Token_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);
    }

    public static void main(String[] arg){
        String pass = DigestUtils.md5Hex("zyg950829"+"#$%zyg950829");
        System.out.println(pass);
    }
}
