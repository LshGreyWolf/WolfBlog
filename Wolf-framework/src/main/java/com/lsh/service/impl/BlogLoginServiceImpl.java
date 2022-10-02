package com.lsh.service.impl;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.LoginUser;
import com.lsh.domain.entity.User;
import com.lsh.domain.vo.BlogUserLoginVo;
import com.lsh.domain.vo.UserInfoVo;
import com.lsh.service.BlogLoginService;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.JwtUtil;
import com.lsh.utils.RedisCache;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //authenticationManager 会调用 UserDetailsService 已经自定义了一个UserDetailsServiceImpl实现UserDetailsService
        //所以会调用UserDetailsServiceImpl这个类
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        //判断是否认证通过
        if (Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或者密码错误");
        }
        //获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //token就是jwt
        String jwt = JwtUtil.createJWT(userId);
        //将用户信息存入redis  将loginUser存入redis中，因为其可里面可包含权限信息
        redisCache.setCacheObject("bloglogin:"+userId,loginUser);
        //把token和userInfo封装 返回
        //查看响应格式，封装vo，有两个
        //将user转换成userInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        //获取key<-获取userid<-获取token
        //登录的时候，JwtAuthenticationTokenFilter将token存入到了SecurityContextHolder中
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        redisCache.deleteObject("bloglogin:"+userId);


        return ResponseResult.okResult();
    }
}
