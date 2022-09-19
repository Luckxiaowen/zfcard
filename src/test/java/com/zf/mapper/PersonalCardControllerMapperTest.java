package com.zf.mapper;


import com.zf.domain.vo.PersonalCardVo;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 17:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PersonalCardControllerMapperTest {

  @Autowired
  PersonalCardMapper personalCardMapper;

  @Test
  public void selectPersonalCardById() {

    PersonalCardVo personalCardVo = personalCardMapper.selectPersonalCardById(1);
    System.out.println(personalCardVo);
  }
}
