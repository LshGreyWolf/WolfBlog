package com.lsh.controller;

import com.alibaba.fastjson.JSON;
import com.lsh.domain.ResponseResult;
import com.lsh.service.UpLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController

public class UploadController {

    @Autowired
    private UpLoadService upLoadService;
    @PostMapping("/upload")
    public ResponseResult uploadImg(@RequestParam("img") MultipartFile multipartFile){
        try {
            return upLoadService.uploadImg(multipartFile);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败");
        }


    }
}
