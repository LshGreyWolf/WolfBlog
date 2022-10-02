package com.lsh.handler.security;

import com.alibaba.fastjson.JSON;
import com.lsh.domain.ResponseResult;
import com.lsh.enums.AppHttpCodeEnum;
import com.lsh.utils.WebUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
