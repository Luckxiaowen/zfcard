package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.*;
import com.zf.domain.vo.PersonalCardVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.*;
import com.zf.service.CompanyImgService;
import com.zf.service.PersonalCardService;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/17 17:34
 */
@Service
public class PersonalCardServiceImpl extends ServiceImpl<PersonalCardMapper, PersonalCardVo>
  implements PersonalCardService {

  @Autowired
  private PersonalCardMapper personalCardMapper;

  @Autowired
  private ExposureTotalMapper exposureTotalMapper;

  @Autowired
  private SysRoleMapper sysRoleMapper;

  @Autowired
  private CompanyMapper companyMapper;


  @Override
  public PersonalCardVo personalCardById(Integer id) {
    return personalCardMapper.selectPersonalCardById(id);
  }

  @Override
  public ResponseVo selectPersonalCard(String token) {

    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    PersonalCardVo personalCardVo = personalCardMapper.selectPersonalCardById(id);
    int roleId = personalCardVo.getRoleId();
    int companyId = personalCardVo.getCompanyId();

    LambdaQueryWrapper<ExposureTotal> query = new LambdaQueryWrapper<>();
    query.eq(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(query);

    if (total==null){

      Date date = new Date();
      assert id != null;
      ExposureTotal exposure = new ExposureTotal(null, id.longValue(), date, date, 0L, 0L, 0L,
        0L, 0L, 0L, 0L, 0L, 0L,0L);

      exposureTotalMapper.insert(exposure);
    }


    LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SysRole::getId,roleId);
    SysRole sysRole = sysRoleMapper.selectOne(queryWrapper);
    String roleName = sysRole.getName();

    LambdaQueryWrapper<Company> companyQueryWrapper = new LambdaQueryWrapper<>();
    companyQueryWrapper.eq(Company::getId,companyId);
    Company company = companyMapper.selectOne(companyQueryWrapper);
    String companyName = company.getCompany();
    String address = company.getAddress();

    HashMap<String, Object> map = new HashMap<>();
    String email = personalCardVo.getEmail();
    String username = personalCardVo.getUsername();
    BigInteger phoneNumber = personalCardVo.getPhoneNumber();

    map.put("roleName",roleName);
    map.put("companyName",companyName);
    map.put("email",email);
    map.put("username",username);
    map.put("address",address);
    map.put("phoneNumber",phoneNumber);

    return ResponseVo.okResult(map);
  }

  @Override
  public ResponseVo savePersonalCard(String token,Long phoneNum) {

    if (phoneNum==null){
      return ResponseVo.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }

    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);
    Long exposureTotalId = total.getId();

    Long dayDownloadNum = total.getDayDownloadNum();
    Long dayDownload = dayDownloadNum+1;

    LambdaUpdateWrapper<ExposureTotal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ExposureTotal::getDayDownloadNum,dayDownload);
    updateWrapper.eq(ExposureTotal::getId,exposureTotalId);
    exposureTotalMapper.update(null,updateWrapper);

    return ResponseVo.okResult();
  }

  @Override
  public ResponseVo forwardPersonalCard(String token,Long phoneNum) {
    if (phoneNum==null){
      return ResponseVo.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }

    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

    Long dayForwardNum = total.getDayForwardNum();
    Long forwardNum = dayForwardNum+1;

    LambdaUpdateWrapper<ExposureTotal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ExposureTotal::getDayForwardNum,forwardNum);
    exposureTotalMapper.update(total,updateWrapper);

    return ResponseVo.okResult();
  }

  @Override
  public ResponseVo savePhoneNum(String token,Long phoneNum) {

    if (phoneNum==null){
      return ResponseVo.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
    }

    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(ExposureTotal::getCreateBy,id);
    ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

    Long dayAddContact = total.getDayAddContact();
    long addContact = dayAddContact + 1;

    LambdaUpdateWrapper<ExposureTotal> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.set(ExposureTotal::getDayAddContact,addContact);
    exposureTotalMapper.update(total,updateWrapper);

    return ResponseVo.okResult();
  }

}
