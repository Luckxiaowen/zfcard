package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.CompanyImg;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;

/**
* @author Amireux
* @description 针对表【company_img(公司图片表)】的数据库操作Service
* @createDate 2022-09-16 08:47:16
*/
public interface CompanyImgService extends IService<CompanyImg> {

    //    获取顶部图片
    ResponseVo getcompanyPictures(@Param("token") String token);
}
