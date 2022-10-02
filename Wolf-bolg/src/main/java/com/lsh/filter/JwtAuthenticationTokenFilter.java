package com.lsh.filter;

import com.alibaba.fastjson.JSON;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.LoginUser;
import com.lsh.enums.AppHttpCodeEnum;
import com.lsh.utils.JwtUtil;
import com.lsh.utils.RedisCache;
import com.lsh.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 写完代码之后要配置到Spring Security 配置中
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)) {
            //说明该接口不需要登录，直接放行
            filterChain.doFilter(request, response);
            //下面的代码不需要执行，return 不要忘了
            return;
        }
        //解析获取userId
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时，token非法
            //响应告诉前端需要重新登录
            //springmvc会对controller解析，将bean解析成json，但是filter不会，所以使用到WebUtils
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            //到这里下面的代码不需要执行，所以return，阻止一下
            return;

        }
        String userId = claims.getSubject();
        //从redis获取用户信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);
        //如果获取不到
        if (Objects.isNull(loginUser)){
            //说明登录过期,提示重新登录
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            //到这里下面的代码不需要执行，所以return，阻止一下
            return;
        }

        //有信息，表示已经登录过  存入SecurityContextHolder
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}
