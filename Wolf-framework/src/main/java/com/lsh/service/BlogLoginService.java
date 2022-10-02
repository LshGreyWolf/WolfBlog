package com.lsh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.User;

public interface BlogLoginService  {
    ResponseResult login(User user);
}
