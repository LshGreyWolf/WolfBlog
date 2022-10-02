package com.lsh.handler.security;

import com.alibaba.fastjson.JSON;
import com.lsh.domain.ResponseResult;
import com.lsh.enums.AppHttpCodeEnum;
import com.lsh.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 授权失败处理器
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        e.printStackTrace();
        //配置失败后，返回的状态码和信息，认证失败后，会出现以下异常
        //InsufficientAuthenticationException
        //BadCredentialsException
        ResponseResult result = null;
        if (e instanceof BadCredentialsException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(), e.getMessage());
        } else if (e instanceof InsufficientAuthenticationException) {
            result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        } else {
            result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(), "认证或授权出现失败");
        }
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
