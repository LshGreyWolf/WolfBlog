package com.lsh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.User;
import com.lsh.domain.vo.UserInfoVo;
import com.lsh.mapper.UserMapper;
import com.lsh.service.UserService;
import com.lsh.utils.BeanCopyUtils;
import com.lsh.utils.SecurityUtils;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-02 19:29:00
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult userInfo() {
        //查询当前员用户id
        Long userId = SecurityUtils.getUserId();
        //根据id查询用户信息
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getId,userId);
        User user = userService.getOne(qw);
        //封装成userInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);


        return ResponseResult.okResult(userInfoVo);
    }
}

