package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.*;
import com.zf.domain.vo.CompanyClientVo;
import com.zf.domain.vo.PersonalCardVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.*;
import com.zf.service.CompanyImgService;
import com.zf.service.PersonalCardService;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
public class PersonalCardServiceImpl extends ServiceImpl<PersonalCardMapper, PersonalCardVo> implements PersonalCardService {

    @Autowired
    private PersonalCardMapper personalCardMapper;

    @Autowired
    private ExposureTotalMapper exposureTotalMapper;

    @Autowired
    private CompanyClientVoMapper companyClientVoMapper;

    @Autowired
    private CompanyClientMapper companyClientMapper;

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Override
    public PersonalCardVo personalCardById(Integer id) {
        return personalCardMapper.selectPersonalCardById(Long.valueOf(id));
    }

    @Override
    public ResponseVo selectPersonalCard(String id) throws Exception {

        if (id == null || id.equals("")) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：输入为空");
        } else {
            //TODO 游客进入
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：不存在此员工");
            } else {
                LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(SysUser::getId, userId);
                SysUser sysUser = sysUserMapper.selectOne(lambdaQueryWrapper);

                PersonalCardVo personalCardVo = personalCardMapper.selectPersonalCardById(Long.valueOf(userId));
                if (Objects.isNull(personalCardVo)) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "用户信息为空");
                }
                int roleId = personalCardVo.getRoleId();
                int companyId = personalCardVo.getCompanyId();

                LambdaQueryWrapper<ExposureTotal> query = new LambdaQueryWrapper<>();
                query.eq(ExposureTotal::getCreateBy, userId);
                ExposureTotal total = exposureTotalMapper.selectOne(query);
                if (total == null) {
                    Date date = new Date();
                    assert userId != null;
                    ExposureTotal exposure = new ExposureTotal(null, Long.valueOf(userId), date, date, 0L, 0L, 0L,
                            0L, 0L, 0L, 0L, 0L, 0L, 0L,"",0);
                    exposureTotalMapper.insert(exposure);
                }
                String roleName="";
                LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(SysUser::getId, userId);
                SysUser sysRole = sysUserMapper.selectOne(queryWrapper);

                if (Objects.isNull(sysRole)){
                    roleName="职位为空";
                }else {
                    roleName = sysRole.getStation();
                }
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
                map.put("weixinCode", sysUser.getWeixinCode());
                map.put("telWeixin", sysUser.getTelWeixin());

                return ResponseVo.okResult(map);
            }
        }
    }


    @Override
    public ResponseVo savePersonalCard(String userId, String phoneNum, String name) {
        Integer id = null;
        if (userId == null || "".equals(userId)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存失败：输入为空");
        } else {
            //TODO 游客进入
            id = getInteger(userId);
            Long companyUserId = Long.valueOf(id);
            if (Objects.isNull(sysUserMapper.selectById(id))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存失败：不存在此员工");
            } else {
                if (!Validator.isMobile(phoneNum)) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存失败：电话号码错误");
                } else {
                    if ("".equals(name) || name == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存失败：姓名为空");
                    } else {
                        LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(ExposureTotal::getCreateBy, id);
                        ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);
                        total.setDayDownloadNum(total.getDayDownloadNum() + 1);
                        exposureTotalMapper.updateById(total);
                        //TODO 添加用户
                        this.isExistClient(id, Long.valueOf(phoneNum), name);

                        CompanyClient client = getClient(phoneNum, name,companyUserId);

                        String saveCaseNum = client.getSaveCaseNum();
                        BigDecimal saveCaseNumDecimal = new BigDecimal(saveCaseNum);
                        String addSaveCase = String.valueOf(saveCaseNumDecimal.add(BigDecimal.valueOf(1)));

                        LambdaUpdateWrapper<CompanyClient> updateWrapper = new LambdaUpdateWrapper<>();

                        updateWrapper.set(CompanyClient::getSaveCaseNum,addSaveCase).eq(CompanyClient::getClientTel,phoneNum);

                        companyClientMapper.update(null,updateWrapper);

                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "保存成功");
                    }
                }
            }
        }

    }

    public CompanyClient getClient(String phoneNum,String name,Long companyUserId) {

        LambdaQueryWrapper<CompanyClient> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CompanyClient::getClientTel,phoneNum).eq(CompanyClient::getDelFlag,0);

        CompanyClient client = companyClientMapper.selectOne(wrapper);

        if (client == null || client.getDelFlag() == 1){
            Date date = new Date();
            CompanyClient companyC = new CompanyClient(null, name, phoneNum, "0", "0", "0", "0", "0", 0, companyUserId, date, date);
            companyClientMapper.insert(companyC);
        }

        LambdaQueryWrapper<CompanyClient> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyClient::getClientTel,phoneNum).eq(CompanyClient::getDelFlag,0);


        return companyClientMapper.selectOne(queryWrapper);

    }

    public Integer getInteger(String userId) {
        Integer id;
        if (Validator.isNumeric(userId)) {
            id = Integer.valueOf(userId);

        } else {
            //TODO 员工token获取
            try {
                String subject = JwtUtil.parseJWT(userId).getSubject();
                id = Integer.valueOf(subject);
            } catch (Exception e) {
                throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
            }
        }
        return id;
    }

    @Override
    public ResponseVo forwardPersonalCard(String userId, String phoneNum, String name) {
        Integer id = null;
        if (userId == null || "".equals(userId)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "转发失败：输入为空");
        } else {
            id = getInteger(userId);
            Long companyUserId = Long.valueOf(id);
            if (Objects.isNull(sysUserMapper.selectById(id))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "转发失败：不存在此员工");
            } else {
                if (!Validator.isMobile(phoneNum)) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "转发失败：电话号码错误");
                } else {
                    if ("".equals(name) || name == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "转发失败：姓名为空");
                    } else {

                        LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(ExposureTotal::getCreateBy, id);
                        ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

                        Long dayForwardNum = total.getDayForwardNum();
                        Long forwardNum = dayForwardNum + 1;
                        total.setDayForwardNum(forwardNum);
                        exposureTotalMapper.updateById(total);
                        //TODO 添加用户
                        this.isExistClient(id, Long.valueOf(phoneNum), name);



                        CompanyClient client = getClient(phoneNum, name,companyUserId);

                        String clientForwardNum = client.getForwardNum();
                        BigDecimal clientForwardNumDecimal = new BigDecimal(clientForwardNum);
                        String clientForward = String.valueOf(clientForwardNumDecimal.add(BigDecimal.valueOf(1)));

                        LambdaUpdateWrapper<CompanyClient> updateWrapper = new LambdaUpdateWrapper<>();

                        updateWrapper.set(CompanyClient::getForwardNum,clientForward).eq(CompanyClient::getClientTel,phoneNum);

                        companyClientMapper.update(null,updateWrapper);

                        return ResponseVo.okResult();
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo savePhoneNum(String userId, String phoneNum, String name) {
        Integer id = null;
        if (userId == null || "".equals(userId)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存电话失败：输入为空或者不存在此员工");
        } else {
            id = getInteger(userId);
            Long companyUserId = Long.valueOf(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存电话失败：不存在此员工");
            } else {
                if (!Validator.isMobile(phoneNum)) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存电话失败：电话号码错误");
                } else {
                    if ("".equals(name) || name == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "保存电话失败：姓名为空");
                    } else {
                        LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
                        queryWrapper.eq(ExposureTotal::getCreateBy, id);
                        ExposureTotal total = exposureTotalMapper.selectOne(queryWrapper);

                        Long dayAddContact = total.getDayAddContact();
                        long addContact = dayAddContact + 1;

                        total.setDayAddContact(addContact);
                        exposureTotalMapper.updateById(total);
                        //TODO 添加用户
                        this.isExistClient(id, Long.valueOf(phoneNum), name);

                        CompanyClient client = getClient(phoneNum, name,companyUserId);

                        String saveMailListNum = client.getSaveMailListNum();
                        BigDecimal saveMailListNumDecimal = new BigDecimal(saveMailListNum);
                        String saveMailList = String.valueOf(saveMailListNumDecimal.add(BigDecimal.valueOf(1)));

                        LambdaUpdateWrapper<CompanyClient> updateWrapper = new LambdaUpdateWrapper<>();

                        updateWrapper.set(CompanyClient::getSaveMailListNum,saveMailList).eq(CompanyClient::getClientTel,phoneNum);

                        companyClientMapper.update(null,updateWrapper);

                        return ResponseVo.okResult();
                    }
                }
            }
        }
    }

    public void isExistClient(Integer userId, Long vTel, String name) {

        LambdaQueryWrapper<Client> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(Client::getTel, vTel);
        Wrapper.eq(Client::getCreatedBy, userId);
        Client client = clientMapper.selectOne(Wrapper);
        int insert = 0;
        if (!Objects.isNull(client)) {
            System.out.println("已存在手机号为 :" + vTel + "的客户");
        } else {
            client = new Client();
            client.setName(name);
            client.setTel(String.valueOf(vTel));
            client.setCreatedBy(Long.valueOf(userId));
            client.setUpdatedBy(Long.valueOf(userId));
            client.setUpdatedTime(new Date());
            client.setCreatedTime(new Date());
            insert = clientMapper.insert(client);
            if (insert > 0) {
                System.out.println("添加成功");
            } else {
                System.out.println("添加失败");
            }
        }
    }
}

