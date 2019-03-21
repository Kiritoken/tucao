package com.eli.emailserver.controller;

import com.eli.emailserver.entity.ResultBean;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;


/**
 * 抽象controller基类  异常处理
 * @author eli
 */
public abstract class BaseController {

    @ResponseBody
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResultBean<String> incorrectCredentialException(){
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setCode(ResultBean.PASSWORDERROR);
        resultBean.setMsg("密码错误!");
        return resultBean;
    }


    @ResponseBody
    @ExceptionHandler({UnknownAccountException.class})
    public ResultBean<String> unknownAccountException() {
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setCode(ResultBean.UNAUTH);
        resultBean.setMsg("该用户未注册!");
        return resultBean;
    }
    @ResponseBody
    @ExceptionHandler({UnauthenticatedException.class, AuthenticationException.class})
    public ResultBean<String> authenticationException() {
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setCode(ResultBean.UNAUTH);
        resultBean.setMsg("用户未登录!");
        return resultBean;
    }

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class })
    public ResultBean<String> authorizationException(){
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setCode(ResultBean.UNAUTHORIZE);
        resultBean.setMsg("用户权限不足!");
        return resultBean;
    }
}
