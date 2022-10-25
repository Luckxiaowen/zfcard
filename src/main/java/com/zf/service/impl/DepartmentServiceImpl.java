package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.*;
import com.zf.domain.vo.ExcellentStaffVo;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.*;
import com.zf.service.DepartmentService;
import com.zf.utils.DateUtil;
import com.zf.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author wenqin
 * @Date 2022/10/19 22:00
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ExposureTotalMapper exposureTotalMapper;

    @Resource
    private ExpoSnapshotMapper expoSnapshotMapper;

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private CompanyFrameMapper companyFrameMapper;

    @Override
    public ResponseVo getdepartmentRank() {

        return null;
    }

    @Override
    public ResponseVo getCardExposure() throws ParseException {
        LoginUser loginUser = UserUtils.getLoginUser();
        Map<String, Object> returnMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return t1.compareTo(s);
            }
        });
        SysUser sysUser = loginUser.getSysUser();
        if (sysUser.getCompanyid() == null || "".equals(sysUser.getCompanyid())) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前员工不属于任何公司");
        } else {
            Company company = new Company();
            company = companyMapper.selectById(sysUser.getCompanyid());
            if (Objects.isNull(company)) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前员工所属公司不存在");
            } else {
                if (company.getDelFlag() == 1 || "1".equals(company.getDelFlag())) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前公司已被注销");
                } else {
                    LambdaQueryWrapper<SysUser> sysUserQueryWrapper = new LambdaQueryWrapper<>();
                    sysUserQueryWrapper.eq(SysUser::getCompanyid, company.getId());
                    List<SysUser> sysUserList = sysUserMapper.selectList(sysUserQueryWrapper);
                    LambdaQueryWrapper<ExposureTotal> exposureTotalQueryWrapper = new LambdaQueryWrapper<>();
                    for (SysUser user : sysUserList) {
                        exposureTotalQueryWrapper = new LambdaQueryWrapper<>();
                        exposureTotalQueryWrapper.eq(ExposureTotal::getCreateBy, user.getId());
                        ExposureTotal exposureTotal = exposureTotalMapper.selectOne(exposureTotalQueryWrapper);
                        if (Objects.isNull(exposureTotal)) {
                            System.out.println("exposureTotal = " + exposureTotal);
                        } else {
                            LambdaQueryWrapper<ExpoSnapshot> expoSnapshotQueryWrapper = new LambdaQueryWrapper<>();
                            expoSnapshotQueryWrapper.eq(ExpoSnapshot::getExpoTotalId, exposureTotal.getId());
                            List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(expoSnapshotQueryWrapper);
                            List<String> sixMouth = DateUtil.getSixMouth();

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                            for (String mouth : sixMouth) {
                                List<ExpoSnapshot> snapshotList = new ArrayList<>();
                                for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                    String format = sdf.format(expoSnapshot.getCreateTime());
                                    if (format.startsWith(mouth)) {
                                        snapshotList.add(expoSnapshot);
                                    }
                                    Integer internalNum = 0;
                                    for (ExpoSnapshot snapshot : snapshotList) {
                                        internalNum = internalNum + Math.toIntExact(snapshot.getDayTotal() + snapshot.getDayNotesNum() + snapshot.getDayDownloadNum() + snapshot.getDayAddContact() + snapshot.getDayAddClient() +
                                                exposureTotal.getDayTotal() + exposureTotal.getDayDownloadNum() + exposureTotal.getDayNotes() + exposureTotal.getDayAddContact() + exposureTotal.getDayAddClient() + exposureTotal.getDayForwardNum());
                                    }
                                    returnMap.put(mouth, internalNum);
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功", returnMap);
    }

    @Override
    public ResponseVo<?> getMouthClientNum() {
        LoginUser loginUser = UserUtils.getLoginUser();
        TreeMap<String, Object> returnMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return t1.compareTo(s);
            }
        });
        SysUser sysUser = loginUser.getSysUser();
        if (sysUser.getCompanyid() == null) {
            return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前员工不属于任何公司");
        } else {
            Company company = new Company();
            company = companyMapper.selectById(sysUser.getCompanyid());
            if (Objects.isNull(company)) {
                return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前员工所属公司不存在");
            } else {
                if (company.getDelFlag() == 1) {
                    return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前公司已被注销");
                } else {
                    LambdaQueryWrapper<SysUser> sysUserQueryWrapper = new LambdaQueryWrapper<>();
                    sysUserQueryWrapper.eq(SysUser::getCompanyid, company.getId());
                    List<SysUser> sysUserList = sysUserMapper.selectList(sysUserQueryWrapper);
                    LambdaQueryWrapper<ExposureTotal> exposureTotalQueryWrapper = new LambdaQueryWrapper<>();
                    for (SysUser user : sysUserList) {
                        exposureTotalQueryWrapper = new LambdaQueryWrapper<>();
                        exposureTotalQueryWrapper.eq(ExposureTotal::getCreateBy, user.getId());
                        ExposureTotal exposureTotal = exposureTotalMapper.selectOne(exposureTotalQueryWrapper);
                        if (Objects.isNull(exposureTotal)) {
                            System.out.println("exposureTotal = " + exposureTotal);
                        } else {
                            LambdaQueryWrapper<ExpoSnapshot> expoSnapshotQueryWrapper = new LambdaQueryWrapper<>();
                            expoSnapshotQueryWrapper.eq(ExpoSnapshot::getExpoTotalId, exposureTotal.getId());
                            List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(expoSnapshotQueryWrapper);
                            List<String> sixMouth = DateUtil.getSixMouth();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                            for (String mouth : sixMouth) {
                                List<ExpoSnapshot> snapshotList = new ArrayList<>();
                                for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                    String format = sdf.format(expoSnapshot.getCreateTime());
                                    if (format.startsWith(mouth)) {
                                        snapshotList.add(expoSnapshot);
                                    }
                                    int mouthClient = 0;
                                    for (ExpoSnapshot snapshot : snapshotList) {
                                        mouthClient = Math.toIntExact(mouthClient + snapshot.getDayAddClient() + exposureTotal.getDayAddClient());
                                    }
                                    returnMap.put(mouth, mouthClient);
                                }
                            }
                        }
                    }
                }
            }
        }
        return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功", returnMap);
    }

    @Override
    public ResponseVo<?> getMouthExcellent() {
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        List<ExcellentStaffVo> excellentStaffVoList = new ArrayList<>();
        List<Client>clients=new ArrayList<>();
        HashMap<String, Object> returnMap = new HashMap<>();
        if (sysUser.getCompanyid() == null) {
            return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前员工不属于任何公司");
        } else {
            Company company = new Company();
            company = companyMapper.selectById(sysUser.getCompanyid());
            if (Objects.isNull(company)) {
                return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前员工所属公司不存在");
            } else {
                if (company.getDelFlag() == 1) {
                    return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "获取失败：当前公司已被注销");
                } else {
                    LambdaQueryWrapper<SysUser> sysUserQueryWrapper = new LambdaQueryWrapper<>();
                    sysUserQueryWrapper.eq(SysUser::getCompanyid, sysUser.getCompanyid());
                    List<SysUser> sysUserList = sysUserMapper.selectList(sysUserQueryWrapper);
                    LambdaQueryWrapper<Client> clientQueryWrapper = new LambdaQueryWrapper<>();
                    for (SysUser user : sysUserList) {
                        ExcellentStaffVo excellentStaffVo = new ExcellentStaffVo();
                        excellentStaffVo.setStaffName(user.getUsername());
                        LambdaQueryWrapper<CompanyFrame> companyFrameQueryWrapper = new LambdaQueryWrapper<>();
                        companyFrameQueryWrapper.eq(CompanyFrame::getCompanyId, company.getId())
                                .eq(CompanyFrame::getId, sysUser.getDepId())
                                .eq(CompanyFrame::getDelFlag, 0);
                        CompanyFrame companyFrame = companyFrameMapper.selectOne(companyFrameQueryWrapper);
                        String station = company.getCompanyName();
                        if (Objects.isNull(companyFrame)) {
                            excellentStaffVo.setStation(station);
                        } else {
                            Integer depId = user.getDepId();
                            CompanyFrame companyFrame1 = companyFrameMapper.selectById(depId);
                            if (companyFrame1.getParentId() == -1) {
                                excellentStaffVo.setStation(company.getCompanyName() + "-" + companyFrame1.getRoleName());
                            } else {
                                companyFrameQueryWrapper = new LambdaQueryWrapper<>();
                                companyFrameQueryWrapper.eq(CompanyFrame::getId, companyFrame1.getParentId())
                                        .eq(CompanyFrame::getDelFlag, 0);
                                CompanyFrame parentCompanyFrame = companyFrameMapper.selectOne(companyFrameQueryWrapper);
                                if (Objects.isNull(parentCompanyFrame)) {
                                    station = "公司" + company.getCompanyName() + "的组织架构出现问题，请检查";
                                    excellentStaffVo.setStation(station);
                                } else {
                                    station = company.getCompanyName() + "-" + parentCompanyFrame.getRoleName() + "-" + companyFrame1.getRoleName();
                                    excellentStaffVo.setStation(station);
                                }
                            }
                            //TODO 客户数量
                            clientQueryWrapper = new LambdaQueryWrapper<>();
                            clientQueryWrapper.eq(Client::getCreatedBy, user.getId());
                            List<Client> clientList = clientMapper.selectList(clientQueryWrapper);
                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
                            String format = sdf.format(new Date());
                            for (Client client : clientList) {
                                if (sdf.format(client.getCreatedTime()).startsWith(format)){
                                    clients.add(client);
                                }
                            }
                            excellentStaffVo.setMouthClient(clients.size());
                            clients=new ArrayList<>();
                            //TODO 互动数
                            LambdaQueryWrapper<ExposureTotal>exposureTotalQueryWrapper=new LambdaQueryWrapper<>();
                            exposureTotalQueryWrapper.eq(ExposureTotal::getCreateBy,user.getId());
                            ExposureTotal exposureTotal = exposureTotalMapper.selectOne(exposureTotalQueryWrapper);
                            if (Objects.isNull(exposureTotal)){
                                excellentStaffVo.setMouthActive(0);
                                excellentStaffVo.setMouthVisitor(0);
                            }else {
                                int active=0;
                                int visitor=0;
                                List<ExpoSnapshot>snapshotList=new ArrayList<>();
                                LambdaQueryWrapper<ExpoSnapshot>expoSnapshotQueryWrapper=new LambdaQueryWrapper<>();
                                expoSnapshotQueryWrapper.eq(ExpoSnapshot::getExpoTotalId,exposureTotal.getId());
                                List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(expoSnapshotQueryWrapper);
                                for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                    if (sdf.format(expoSnapshot.getCreateTime()).contains(format)){
                                        snapshotList.add(expoSnapshot);
                                    }
                                    for (ExpoSnapshot snapshot : snapshotList) {
                                        active= Math.toIntExact(active + snapshot.getDayTotal() + snapshot.getDayNotesNum() + snapshot.getDayDownloadNum() + snapshot.getDayAddContact() + snapshot.getDayAddClient() +
                                                exposureTotal.getDayAddClient() + exposureTotal.getDayAddContact() + exposureTotal.getDayAddClient() + exposureTotal.getDayNotes() + exposureTotal.getDayForwardNum());
                                        visitor= Math.toIntExact(visitor + snapshot.getDayTotal() + exposureTotal.getDayTotal());
                                    }
                                    excellentStaffVo.setMouthActive(active);
                                    excellentStaffVo.setMouthVisitor(visitor);
                                    snapshotList=new ArrayList<>();
                                    expoSnapshotQueryWrapper=new LambdaQueryWrapper<>();
                                }

                            }
                        }
                        excellentStaffVoList.add(excellentStaffVo);
                        returnMap.put("excellentList", excellentStaffVoList);
                    }
                }
                return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功", returnMap);
            }
        }
    }
}