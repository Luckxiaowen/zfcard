package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.zf.utils.ExposureUtils;
import com.zf.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

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

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public PersonalCardVo personalCardById(Integer id) {
        return personalCardMapper.selectPersonalCardById(Long.valueOf(id));
    }

    @Override
    public ResponseVo selectPersonalCard(String token) throws Exception {

        Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());

        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getId,id);
        SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);
        String weixinCode = sysUser.getWeixinCode();
        String telWeixin = sysUser.getTelWeixin();

        if (id==null){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"用户id为空");
        }else{
            PersonalCardVo personalCardVo = personalCardMapper.selectPersonalCardById(Long.valueOf(id));
            if (Objects.isNull(personalCardVo)){
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"用户信息为空");
            }
            int roleId = personalCardVo.getRoleId();
            int companyId = personalCardVo.getCompanyId();


            LambdaQueryWrapper<ExposureTotal> query = new LambdaQueryWrapper<>();
            query.eq(ExposureTotal::getCreateBy, id);
            ExposureTotal total = exposureTotalMapper.selectOne(query);

            if (total == null) {

                Date date = new Date();
                assert id != null;
                ExposureTotal exposure = new ExposureTotal(null, Long.valueOf(id), date, date, 0, 0, 0,
                        0, 0, 0, 0, 0, 0, 0);

                exposureTotalMapper.insert(exposure);
            }

            LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysRole::getId, roleId);
            SysRole sysRole = sysRoleMapper.selectOne(queryWrapper);

            String roleName = sysRole.getName();

            LambdaQueryWrapper<Company> companyQueryWrapper = new LambdaQueryWrapper<>();
            companyQueryWrapper.eq(Company::getId, companyId);
            Company company = companyMapper.selectOne(companyQueryWrapper);

            String companyName = company.getCompany();
            String address = company.getAddress();

            HashMap<String, Object> map = new HashMap<>();
            String email = personalCardVo.getEmail();
            String username = personalCardVo.getUsername();
            BigInteger phoneNumber = personalCardVo.getPhoneNumber();

            map.put("roleName", roleName);
            map.put("companyName", companyName);
            map.put("email", email);
            map.put("username", username);
            map.put("address", address);
            map.put("phoneNumber", phoneNumber);
            map.put("weixinCode",weixinCode);
            map.put("telWeixin",telWeixin);

            return ResponseVo.okResult(map);
        }

    }

    @Override
    public ResponseVo savePersonalCard(String token, Long phoneNum) {

        if (phoneNum == null) {
            return ResponseVo.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExposureTotal::getCreateBy, id);
        ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);
        Long exposureTotalId = total.getId();

        Integer dayDownloadNum = total.getDayDownloadNum() + 1;

        total.setDayDownloadNum(dayDownloadNum);
        exposureTotalMapper.updateById(total);

        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo forwardPersonalCard(String token, Long phoneNum) {
        if (phoneNum == null) {
            return ResponseVo.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }

        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExposureTotal::getCreateBy, id);
        ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

        Integer dayForwardNum = total.getDayForwardNum();
        Integer forwardNum = dayForwardNum + 1;

        total.setDayForwardNum(forwardNum);

        exposureTotalMapper.updateById(total);

        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo savePhoneNum(String token, Long phoneNum) {

        if (phoneNum == null) {
            return ResponseVo.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        }

        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }

        LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ExposureTotal::getCreateBy, id);
        ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

        Integer dayAddContact = total.getDayAddContact();
        Integer addContact = dayAddContact + 1;
        total.setDayAddContact(addContact);

        exposureTotalMapper.updateById(total);
        return ResponseVo.okResult();
    }
}