package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.ModuleConfig;
import com.zf.domain.entity.ModuleConfigVo;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.CompanyMapper;
import com.zf.mapper.ModuleConfigMapper;
import com.zf.service.ModuleConfigService;
import com.zf.service.UploadService;
import com.zf.utils.JwtUtil;
import com.zf.utils.UpLoadUtil;
import com.zf.utils.emailutil.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/24 14:22
 */
@Service
public class ModuleConfigServiceImpl extends ServiceImpl<ModuleConfigMapper,ModuleConfig> implements ModuleConfigService {

  @Autowired
  private ModuleConfigMapper moduleConfigMapper;
  @Autowired
  private CompanyMapper companyMapper;

  @Override
  public ResponseVo setModuleSwitch(String token,int switchFlag,String category) throws Exception {

    if (token == null){
      return ResponseVo.errorResult(AppHttpCodeEnum.LOGIN_INVALID);
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Integer companyId = Math.toIntExact(loginUser.getSysUser().getCompanyid());

    Company company = new Company();

    if (switchFlag == 1 || switchFlag == 0){

    }else {
      return ResponseVo.okResult(200,"switchFlag只能是（1）开，或者（0）关");
    }


    if ("个性化简介".equals(category)){


      company.setIntroductionSwitch(switchFlag);

    }else if ("个性化内容".equals(category)) {

      company.setContentSwitch(switchFlag);

    }else {
      return ResponseVo.okResult(200,"category只能是（个性化简介），或者（个性化内容）");

    }

    company.setId(companyId);
    companyMapper.updateById(company);

    return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS);
  }

  @Override
  public ResponseVo insertIntroduceModule(String token,String moduleName, HttpServletRequest request, MultipartFile file,String category) {
    if (token == null){
      return ResponseVo.errorResult(AppHttpCodeEnum.LOGIN_INVALID);
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Long companyId =loginUser.getSysUser().getCompanyid();
    String url = "";
    HashMap map=new HashMap();
    if (Objects.isNull(file)){
        LambdaQueryWrapper<ModuleConfig>wrapper=new LambdaQueryWrapper<>();
      wrapper.eq(ModuleConfig::getCompanyId,companyId);
      wrapper.eq(ModuleConfig::getCategory,category);
      ModuleConfig moduleConfig = moduleConfigMapper.selectOne(wrapper);
      url=moduleConfig.getModuleBanner();
    }else {
      map = UpLoadUtil.updateUserWxCode(request, file);
      if (!map.get("msg").equals(200)){
        return ResponseVo.errorResult(AppHttpCodeEnum.FAIL,"上传图片失败");
      }else {
        url = String.valueOf(map.get("url"));
      }
    }

    if ("个性化简介".equals(category)){

      LambdaQueryWrapper<ModuleConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(ModuleConfig::getCategory,category).eq(ModuleConfig::getCompanyId,companyId);
      ModuleConfig persona = moduleConfigMapper.selectOne(lambdaQueryWrapper);

      if (persona == null){
        moduleConfigMapper.insert(new ModuleConfig(moduleName,url,companyId,category,null));
        return ResponseVo.okResult(url);
      }else {
        String id = persona.getModuleId();

        ModuleConfig moduleConfig = new ModuleConfig();
        moduleConfig.setCategory(category);
        moduleConfig.setModuleName(moduleName);
        moduleConfig.setModuleBanner(url);
        moduleConfig.setCompanyId(companyId);
        moduleConfig.setModuleId(id);

        moduleConfigMapper.updateById(moduleConfig);

        return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS);
      }


    }else if ("个性化内容".equals(category)) {

      LambdaQueryWrapper<ModuleConfig> lambdaQueryWrapper = new LambdaQueryWrapper<>();
      lambdaQueryWrapper.eq(ModuleConfig::getCategory,category).eq(ModuleConfig::getCompanyId,companyId);
      ModuleConfig persona = moduleConfigMapper.selectOne(lambdaQueryWrapper);

      if (persona == null){
        moduleConfigMapper.insert(new ModuleConfig(moduleName,url,companyId,category,null));
        return ResponseVo.okResult(url);
      }else {
        String id = persona.getModuleId();

        ModuleConfig moduleConfig = new ModuleConfig();
        moduleConfig.setCategory(category);
        moduleConfig.setModuleName(moduleName);
        moduleConfig.setModuleBanner(url);
        moduleConfig.setCompanyId(companyId);
        moduleConfig.setModuleId(id);

        moduleConfigMapper.updateById(moduleConfig);

        return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS);

      }
    }

    return ResponseVo.okResult(200,"category只能是（个性化简介），或者（个性化内容）");

  }

  @Override
  public ResponseVo PersonaEcho(String token, String category) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Long companyId =loginUser.getSysUser().getCompanyid();


    if ("个性化简介".equals(category)){
      LambdaQueryWrapper<ModuleConfig> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(ModuleConfig::getCompanyId,companyId);
      queryWrapper.eq(ModuleConfig::getCategory,category);
      ModuleConfig personaEcho = moduleConfigMapper.selectOne(queryWrapper);
      LambdaQueryWrapper<Company>companyLambdaQueryWrapper=new LambdaQueryWrapper<>();
      companyLambdaQueryWrapper.eq(Company::getId,personaEcho.getCompanyId());
      Company company = companyMapper.selectOne(companyLambdaQueryWrapper);
      ModuleConfigVo moduleConfigVo=new ModuleConfigVo(personaEcho.getModuleName(),personaEcho.getModuleBanner(),personaEcho.getCompanyId(),personaEcho.getCategory(),personaEcho.getModuleId(),company.getContentSwitch());
      return ResponseVo.okResult(moduleConfigVo);
    }
    if("个性化内容".equals(category)){

      LambdaQueryWrapper<ModuleConfig> queryWrapper = new LambdaQueryWrapper<>();

      queryWrapper.eq(ModuleConfig::getCompanyId,companyId);
      queryWrapper.eq(ModuleConfig::getCategory,category);
      ModuleConfig contentEcho = moduleConfigMapper.selectOne(queryWrapper);

      LambdaQueryWrapper<Company>companyLambdaQueryWrapper=new LambdaQueryWrapper<>();
      companyLambdaQueryWrapper.eq(Company::getId,contentEcho.getCompanyId());
      Company company = companyMapper.selectOne(companyLambdaQueryWrapper);
      ModuleConfigVo moduleConfigVo=new ModuleConfigVo(contentEcho.getModuleName(),contentEcho.getModuleBanner(),contentEcho.getCompanyId(),contentEcho.getCategory(),contentEcho.getModuleId(),company.getContentSwitch());

      return ResponseVo.okResult(moduleConfigVo);
    }

    return null;
  }
}
