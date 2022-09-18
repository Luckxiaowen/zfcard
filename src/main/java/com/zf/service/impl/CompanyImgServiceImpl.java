package com.zf.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyImg;
import com.zf.mapper.CompanyImgMapper;
import com.zf.service.CompanyImgService;
import org.springframework.stereotype.Service;

/**
* @author Amireux
* @description 针对表【company_img(公司图片表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class CompanyImgServiceImpl extends ServiceImpl<CompanyImgMapper, CompanyImg>
implements CompanyImgService {

}
