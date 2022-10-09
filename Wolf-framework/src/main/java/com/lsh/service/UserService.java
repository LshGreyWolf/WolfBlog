package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.dto.UserDto;
import com.lsh.domain.entity.User;
import com.lsh.domain.vo.SaveUserVo;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-10-02 19:28:58
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult userPage(Integer pageNum, Integer pageSize, User user);


    ResponseResult addUser(UserDto userDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUser(Long id);


    ResponseResult saveUser(SaveUserVo saveUserVo);
}

