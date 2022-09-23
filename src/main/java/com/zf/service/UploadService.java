package com.zf.service;

import com.zf.domain.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author wenqin
 * @Date 2022/9/23 18:16
 */

public interface UploadService {
    ResponseVo uploadImg(MultipartFile file);
}
