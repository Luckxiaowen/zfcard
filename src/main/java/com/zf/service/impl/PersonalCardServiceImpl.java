package com.zf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.CompanyImg;
import com.zf.domain.entity.PersonalCard;
import com.zf.mapper.CompanyImgMapper;
import com.zf.mapper.PersonalCardMapper;
import com.zf.service.CompanyImgService;
import com.zf.service.PersonalCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 17:34
 */
@Service
public class PersonalCardServiceImpl extends ServiceImpl<PersonalCardMapper, PersonalCard>
  implements PersonalCardService {

  @Autowired
  private PersonalCardMapper personalCardMapper;

  @Override
  public PersonalCard personalCardById(Integer id) {
    return personalCardMapper.selectPersonalCardById(id);
  }
}
