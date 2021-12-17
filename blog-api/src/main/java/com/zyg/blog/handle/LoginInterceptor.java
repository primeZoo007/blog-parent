package com.zyg.blog.handle;

import com.alibaba.fastjson.JSON;
import com.zyg.blog.dao.pojo.SysUser;
import com.zyg.blog.service.LoginService;
import com.zyg.blog.service.SysUserService;
import com.zyg.blog.utils.UserThreadLocal;
import com.zyg.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        // 在执行controller（handler）方法之前进行执行
        /**
         * 1。需要判断请求的路径是否为handler
         * 2。判断token是否为空，如果为空，未登陆状态
         * 3。如果token不为空，进行登陆认证
         * 4。如果登陆成功 放行即可
         */
        if(!(handler instanceof HandlerMethod)){
            // handler 可能是requestResourcehandler 资源访问
            // spring-boot默认是classpath目录下的static目录
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");
        SysUser sysUser = loginService.checkToken(token);
        if(StringUtils.isBlank(token)||sysUser==null){
            Result result = Result.fail(1111,"未登陆");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.put(sysUser);
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息 会有内存泄漏的风险
        System.out.println(UserThreadLocal.get()+"before");
        UserThreadLocal.remove();
        System.out.println(UserThreadLocal.get()+"after");
    }
}
