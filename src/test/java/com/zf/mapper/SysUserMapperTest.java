package com.zf.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zf.domain.vo.SysUserVo;
import com.zf.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/21 17:48
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class SysUserMapperTest {

  @Autowired
  private SysUserMapper sysUserMapper;
  @Test
  public void testSelectUserByQuery() {

    Page<SysUserVo> page = new Page<>(1,8);
    sysUserMapper.selectUserByQuery(page,"1",null,null,null);
  }
}
