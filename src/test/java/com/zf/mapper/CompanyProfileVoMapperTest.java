package com.zf.mapper;

import com.zf.domain.vo.CompanyProfileVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class CompanyProfileVoMapperTest {

    @Autowired
    private CompanyProfileVoMapper companyProfileVoMapper;
    @Test
    public void companyProfile() {
        List<CompanyProfileVo> companyProfileVos = companyProfileVoMapper.companyProfile(1);
        companyProfileVos.forEach(System.out::println);
    }
}