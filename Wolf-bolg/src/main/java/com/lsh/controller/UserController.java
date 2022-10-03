package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.User;
import com.lsh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }


    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){

        return userService.updateUserInfo(user);

    }

}