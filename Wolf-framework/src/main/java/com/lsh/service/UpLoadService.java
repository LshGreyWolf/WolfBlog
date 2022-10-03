package com.lsh.service;

import com.lsh.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

public interface UpLoadService {
    ResponseResult uploadImg(MultipartFile img);
}
