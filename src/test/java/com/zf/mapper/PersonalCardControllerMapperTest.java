package com.zf.mapper;


import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.vo.PersonalCardVo;
import com.zf.service.ExposureTotalService;
import com.zf.service.SysMenuService;
import com.zf.utils.ExposureUtils;
import com.zf.utils.RedisCache;
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

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

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

  @Resource
  private SysMenuService menuService;

  @Test
  public void selectPersonalCardById() {
    PersonalCardVo personalCard = personalCardMapper.selectPersonalCardById(1L);
    System.out.println(personalCard);
  }

  @Test
  public void test22(){
    System.out.println(menuService.getSysMenuByUserId(1L));
  }

  @Autowired
  private ExposureUtils exposureUtils;
  @Autowired
  private ExposureTotalService exposureTotalService;
  @Test
  public void test(){
//    Integer value = 1001;
//    String key = "exposure:" + 2 + ":" + "download:";
//    redisCache.setCacheObject(key,value);
//    Integer cacheObject = redisCache.getCacheObject(key);
//    System.out.println(cacheObject);
    exposureUtils.incrementExposureOne(ExposureUtils.DOWNLOAD,1);
    exposureUtils.incrementExposureOne(ExposureUtils.FORWARD,1);

    ExposureTotal total = new ExposureTotal();
    Integer downloadCount = exposureUtils.getExposureCount(ExposureUtils.DOWNLOAD,1);
    Integer addContactCount = exposureUtils.getExposureCount(ExposureUtils.ADD_CONTACT,1);
    total.setDayDownloadNum(downloadCount);
    total.setDayAddContact(addContactCount);
    exposureTotalService.save(total);

  }

}
