package com.lsh.service.impl;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.LoginUser;
import com.lsh.domain.entity.User;
import com.lsh.domain.vo.BlogUserLoginVo;
import com.lsh.domain.vo.UserInfoVo;
import com.lsh.service.LoginService;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.JwtUtil;
import com.lsh.utils.RedisCache;
import com.lsh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
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
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或者密码错误");
        }
        //获取userId生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //token就是jwt
        String jwt = JwtUtil.createJWT(userId);
        //将用户信息存入redis  将loginUser存入redis中，因为其可里面可包含权限信息
        redisCache.setCacheObject("login:" + userId, loginUser);
        //把token封装  查看响应格式，只响应一个token，不用封装成vo了
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Long userId = SecurityUtils.getUserId();
        //删除缓存中的token
        redisCache.deleteObject("login:" + userId);
        return ResponseResult.okResult();
    }
}
