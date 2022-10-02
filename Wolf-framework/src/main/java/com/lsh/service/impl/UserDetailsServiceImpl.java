package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsh.domain.entity.LoginUser;
import com.lsh.domain.entity.User;
import com.lsh.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查询到  如果没有查到，抛出异常
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }
        System.out.println(user);
        //查到，返回用户信息
//        TODO 查询权限信息，封装
        //由于最后返回的类型应该是UserDetails类型的，所以直接返回user不行，解决办法：
        //定义一个类LoginUser实现UserDetails，将User定义为该类的成员变量，通过构造方法传值，然后重写里面的方法
        //通过getPassword，getPassword获取值，返回
        return new LoginUser(user);
    }
}
