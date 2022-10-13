package com.zf.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.Client;
import com.zf.domain.vo.ResponseVo;
import org.springframework.stereotype.Repository;

/**
* @author Amireux
* @description 针对表【client(客户表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:16
* @Entity com.zf.domain.Client
*/
@Repository
public interface ClientMapper extends BaseMapper<Client> {


}
