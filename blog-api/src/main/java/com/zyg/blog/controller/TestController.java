package com.zyg.blog.controller;
import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.utils.UserThreadLocal;
import com.zyg.blog.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping
    public Result test(){
        SysUser sysUser = UserThreadLocal.get();
        System.out.println(sysUser);
        return Result.success(null);
    }
}

