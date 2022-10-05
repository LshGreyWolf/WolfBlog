package com.lsh.service;

import com.lsh.domain.ResponseResult;
import com.lsh.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
