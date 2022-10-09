package com.lsh.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.UserDto;
import com.lsh.domain.entity.User;
import com.lsh.domain.vo.SaveUserVo;
import com.lsh.domain.vo.UserListVo;
import com.lsh.service.UserService;
import com.lsh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult userPage(Integer pageNum, Integer pageSize, User user) {
        return userService.userPage(pageNum, pageSize, user);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);

    }

    /**
     * 根据id回显用户信息
     *
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    /**
     * 保存用户信息
     */
    @PutMapping
    public ResponseResult saveUser(@RequestBody SaveUserVo saveUserVo) {
        return userService.saveUser(saveUserVo);

    }
}
