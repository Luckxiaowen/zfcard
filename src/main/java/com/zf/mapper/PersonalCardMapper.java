package com.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.PersonalCard;
import com.zf.domain.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 17:31
 */
@Repository
public interface PersonalCardMapper extends BaseMapper<PersonalCard> {

  PersonalCard selectPersonalCardById(@Param("id") Integer id);
}
