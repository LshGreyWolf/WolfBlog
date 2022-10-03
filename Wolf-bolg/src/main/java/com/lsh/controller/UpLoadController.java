package com.lsh.controller;

import com.lsh.domain.ResponseResult;
import com.lsh.service.UpLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class UpLoadController {

    @Autowired
    private UpLoadService upLoadService;
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return upLoadService.uploadImg(img);
    }
}
