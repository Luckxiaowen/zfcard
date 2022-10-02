package com.zf.controller;

import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.UploadService;
import com.zf.utils.UpLoadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wenqin
 * @Date 2022/9/23 18:15
 */

@RestController
@Api(tags = "上传图片接口")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @ApiOperation(value = "上传图片至七牛云服务器接口")
    @PostMapping("/upload")
    public ResponseVo upload(MultipartFile file) {
        return uploadService.uploadImg(file);
    }

    @ApiOperation(value = "上传图片至本地124服务器接口")
    @PostMapping("/upload-me")
    public ResponseVo uploadMe(HttpServletRequest request,MultipartFile file) {
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",UpLoadUtil.updateUserWxCode(request,file));
    }
}
