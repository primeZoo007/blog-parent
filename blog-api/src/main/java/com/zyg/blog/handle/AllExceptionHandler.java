package com.zyg.blog.handle;

import com.zyg.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

// 对加了controller注解的方法进行拦截处理 AOP实现
@ControllerAdvice
public class
AllExceptionHandler {
    //进行一场处理处理exception。class的一场
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doExceptiion(Exception ex){
        ex.printStackTrace();
        return Result.fail(-999,"系统一场");
    }
}
