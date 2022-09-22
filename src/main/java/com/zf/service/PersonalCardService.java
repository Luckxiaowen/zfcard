package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.Notes;
import com.zf.domain.vo.PersonalCardVo;
import org.apache.ibatis.annotations.Param;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 17:33
 */
public interface PersonalCardService extends IService<PersonalCardVo> {

  PersonalCardVo personalCardById(@Param("id") Integer id);

}
