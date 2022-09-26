package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.PersonalCardVo;
import com.zf.domain.vo.ResponseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.zip.ZipEntry;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 17:33
 */
@Service
public interface PersonalCardService extends IService<PersonalCardVo> {


  PersonalCardVo personalCardById(@Param("id") Integer id);

  /**
   * 个人名片显示，同时创建个人统计数据
   * @param token
   * @return
   */

  ResponseVo selectPersonalCard(String token) throws Exception;

  /**
   * 保存名片
   * @param token
   * @param phoneNum
   * @return
   */
  ResponseVo savePersonalCard(String token,Long phoneNum);


  /**
   * 转发名片
   * @param token
   * @param phoneNum
   * @return
   */
  ResponseVo forwardPersonalCard(String token,Long phoneNum);

  /**
   * 保存电话
   * @param token
   * @param phoneNum
   * @return
   */
  ResponseVo savePhoneNum(String token,Long phoneNum);

}
