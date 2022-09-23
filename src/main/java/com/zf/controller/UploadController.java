package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author wenqin
 * @Date 2022/9/23 18:15
 */

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;


    @PostMapping("/upload")
    public ResponseVo upload(MultipartFile file) {
        return uploadService.uploadImg(file);
    }
}
