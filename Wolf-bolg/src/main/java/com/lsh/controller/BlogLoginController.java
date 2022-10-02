package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.User;
import com.lsh.enums.AppHttpCodeEnum;
import com.lsh.exception.SystemException;
import com.lsh.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
public class BlogLoginController {
    @Autowired
    private BlogLoginService BlogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){

        if (!StringUtils.hasText(user.getUserName())){
            //提示，必须要传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return BlogLoginService.login(user);
    }
}
