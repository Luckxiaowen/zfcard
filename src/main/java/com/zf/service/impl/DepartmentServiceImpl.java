package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.*;
import com.zf.domain.vo.DepVo;
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
    public ResponseVo<?> getdepartmentRank(int depId) {
        DepVo depVo = new DepVo();
        Map<String, Object> returnMap = new TreeMap<>();
        List<DepVo> depVoList = new ArrayList<>();
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        Long companyid = sysUser.getCompanyid();
        List<SysUser>sysUserList=new ArrayList<>();
        List<CompanyFrame> companyFrameList=new ArrayList<>();
        List<CompanyFrame> childCompanyFrameList=new ArrayList<>();
        List<CompanyFrame> parentCompanyFrameList=new ArrayList<>();
        List<SysUser>compareList=new ArrayList<>();
        if (companyid == null || "".equals(companyid)) {
            return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "查询失败：该员工未加入任何公司");
        } else {
            Company company = new Company();
            company = companyMapper.selectById(companyid);
            if (Objects.isNull(company)) {
                return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "查询失败：当前公司不存在请检查");
            } else {
                LambdaQueryWrapper<Company> companyWrapper = new LambdaQueryWrapper<>();
                companyWrapper.eq(Company::getId, companyid).eq(Company::getDelFlag, 0);
                company = companyMapper.selectOne(companyWrapper);
                if (Objects.isNull(company)) {
                    return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "查询失败：当前公司已被删除!");
                }else {
                    //TODO 拿拿到公司总人数
                    LambdaQueryWrapper<SysUser>sysUserWrapper=new LambdaQueryWrapper<>();
                    sysUserWrapper.eq(SysUser::getCompanyid,companyid);
                    //TODO 已拿到公司总人数
                    sysUserList = sysUserMapper.selectList(sysUserWrapper);
                    //TODO 通过传递的depId和用户公司Id拿到公司部门Id集合
                    LambdaQueryWrapper<CompanyFrame>companyFrameWrapper=new LambdaQueryWrapper<>();
                    //TODO 已拿到公司DepId集合
                    companyFrameWrapper.eq(CompanyFrame::getCompanyId,companyid).eq(CompanyFrame::getDelFlag,0);
                    companyFrameList = companyFrameMapper.selectList(companyFrameWrapper);
                    if (depId==1){
                        //TODO 拿到父级Id员工并且分组
                        companyFrameList= companyFrameList.stream().filter(parentId->parentId.getParentId()==-1).collect(Collectors.toList());
                        for (CompanyFrame companyFrame : companyFrameList) {
                            //todo 对比拿到parentId下的员工
                            for (SysUser user : sysUserList) {
                                if (Objects.equals(user.getDepId(), companyFrame.getId())){
                                    compareList.add(user);
                                }
                            }
                            /**部门访客数*/
                            int depVisitNum=0;
                            /**部门客户总数*/
                            int depClientNum=0;
                            /**部门总人数*/
                            int depPersonNum=0;
                            /**部门活跃总数*/
                            int depActiveNum=0;
                            for (SysUser user : compareList) {
                                 depVisitNum=0;
                                 depClientNum=0;
                                 depActiveNum=0;
                                //TODO 遍历部门成员查询曝光统计表和曝光快照表
                                LambdaQueryWrapper<ExposureTotal>exposureTotalWrapper=new LambdaQueryWrapper<>();
                                exposureTotalWrapper.eq(ExposureTotal::getCreateBy,user.getId());
                                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(exposureTotalWrapper);
                                LambdaQueryWrapper<ExpoSnapshot>expoSnapshotWrapper=new LambdaQueryWrapper<>();
                                expoSnapshotWrapper.eq(ExpoSnapshot::getExpoTotalId,exposureTotal.getId());
                                List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(expoSnapshotWrapper);
                                //TODO 计算
                                for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                    depActiveNum= (int) (depActiveNum+expoSnapshot.getDayNotesNum()+expoSnapshot.getDayDownloadNum()+expoSnapshot.getDayAddContact()+expoSnapshot.getDayAddClient());
                                }
                                depActiveNum= (int) (depActiveNum+exposureTotal.getDayTotal()+exposureTotal.getDayForwardNum()+exposureTotal.getDayNotes()+exposureTotal.getDayAddContact()+exposureTotal.getDayAddClient()+exposureTotal.getDayForwardNum());
                                depVisitNum= (int) (depVisitNum+exposureTotal.getVisitorTotal());
                                depClientNum= (int) (depClientNum+exposureTotal.getClientTotalNum());
                            }
                            depPersonNum=compareList.size();
                            depVo.setDepId(companyFrame.getId());
                            depVo.setDepName(companyFrame.getRoleName());
                            depVo.setDepClientNum(depClientNum);
                            depVo.setDepActiveNum(depActiveNum);
                            depVo.setDepVisitNum(depVisitNum);
                            depVo.setDepPersonNum(depPersonNum);
                            if (compareList.size()==0){
                                depVo.setDepActiveAverage(0);
                                depVo.setDepVisitAverage(0);
                                depVo.setDepClientAverage(0);
                            }else {
                                depVo.setDepActiveAverage(depActiveNum/compareList.size());
                                depVo.setDepVisitAverage(depVisitNum/compareList.size());
                                depVo.setDepClientAverage(depClientNum/compareList.size());
                            }
                            depVoList.add(depVo);
                            depVo=new DepVo();
                        }
                        returnMap.put("depVoList",depVoList);
                        return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",returnMap);
                    }else {
                        if (depId==2){
                            //TODO 先拿到子级Id集合
                            parentCompanyFrameList= companyFrameList.stream().filter(parentId->parentId.getParentId()==-1).collect(Collectors.toList());
                            for (CompanyFrame parentFrame : parentCompanyFrameList) {
                                for (CompanyFrame child : companyFrameList) {
                                    if (Objects.equals(child.getParentId(),parentFrame.getId())){
                                        childCompanyFrameList.add(child);
                                    }
                                }
                            }
                            for (CompanyFrame childFrame : childCompanyFrameList) {
                                for (SysUser user : sysUserList) {
                                    if (Objects.equals(childFrame.getId(),user.getDepId()));
                                    compareList.add(user);
                                }
                                /**部门访客数*/
                                int depVisitNum=0;
                                /**部门客户总数*/
                                int depClientNum=0;
                                /**部门总人数*/
                                int depPersonNum=0;
                                /**部门活跃总数*/
                                int depActiveNum=0;
                                for (SysUser user : compareList) {
                                    //TODO 遍历部门成员查询曝光统计表和曝光快照表
                                    LambdaQueryWrapper<ExposureTotal>exposureTotalWrapper=new LambdaQueryWrapper<>();
                                    exposureTotalWrapper.eq(ExposureTotal::getCreateBy,user.getId());
                                    ExposureTotal exposureTotal = exposureTotalMapper.selectOne(exposureTotalWrapper);
                                    LambdaQueryWrapper<ExpoSnapshot>expoSnapshotWrapper=new LambdaQueryWrapper<>();
                                    expoSnapshotWrapper.eq(ExpoSnapshot::getExpoTotalId,exposureTotal.getId());
                                    List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(expoSnapshotWrapper);
                                    //TODO 计算
                                    for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                        depActiveNum= (int) (depActiveNum+expoSnapshot.getDayNotesNum()+expoSnapshot.getDayDownloadNum()+expoSnapshot.getDayAddContact()+expoSnapshot.getDayAddClient());
                                    }
                                    depActiveNum= (int) (depActiveNum+exposureTotal.getDayTotal()+exposureTotal.getDayForwardNum()+exposureTotal.getDayNotes()+exposureTotal.getDayAddContact()+exposureTotal.getDayAddClient()+exposureTotal.getDayForwardNum());
                                    depVisitNum= (int) (depVisitNum+exposureTotal.getVisitorTotal());
                                    depClientNum= (int) (depClientNum+exposureTotal.getClientTotalNum());
                                }
                                depPersonNum=compareList.size();
                                depVo.setDepId(childFrame.getId());
                                depVo.setDepName(childFrame.getRoleName());
                                depVo.setDepClientNum(depClientNum);
                                depVo.setDepActiveNum(depActiveNum);
                                depVo.setDepVisitNum(depVisitNum);
                                depVo.setDepPersonNum(depPersonNum);
                                if (compareList.size()==0){
                                    depVo.setDepActiveAverage(0);
                                    depVo.setDepVisitAverage(0);
                                    depVo.setDepClientAverage(0);
                                }else {
                                    depVo.setDepActiveAverage(depActiveNum/compareList.size());
                                    depVo.setDepVisitAverage(depVisitNum/compareList.size());
                                    depVo.setDepClientAverage(depClientNum/compareList.size());
                                }
                                depVoList.add(depVo);
                                depVo=new DepVo();
                            }
                            returnMap.put("depVoList",depVoList);
                            return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",returnMap);
                        }else {
                            if (depId==3){

                            }else {
                                return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "查询失败: 权限不足");
                            }
                        }
                    }
                }
            }
        }
        //TODO 返回
        return null;
    }


    @Override
    public ResponseVo<?> getCardExposure() {

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
                                }
                                Integer internalNum = 0;
                                for (ExpoSnapshot snapshot : snapshotList) {
                                    internalNum = internalNum + Math.toIntExact(snapshot.getDayTotal() + snapshot.getDayNotesNum() + snapshot.getDayDownloadNum() + snapshot.getDayAddContact() + snapshot.getDayAddClient());
                                }
                                if (sdf.format(exposureTotal.getUpdateTime()).startsWith(mouth)) {
                                    internalNum = internalNum + Math.toIntExact(exposureTotal.getDayTotal() + exposureTotal.getDayDownloadNum() + exposureTotal.getDayNotes() + exposureTotal.getDayAddContact() + exposureTotal.getDayAddClient() + exposureTotal.getDayForwardNum());
                                }
                                returnMap.put(mouth, internalNum);
                            }
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功", returnMap);
                        }
                    }
                }
            }
        }
        return null;
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
                                int mouthClient = 0;
                                List<ExpoSnapshot> snapshotList = new ArrayList<>();
                                for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                    String format = sdf.format(expoSnapshot.getCreateTime());
                                    if (format.startsWith(mouth)) {
                                        snapshotList.add(expoSnapshot);
                                    }
                                }
                                for (ExpoSnapshot snapshot : snapshotList) {
                                    mouthClient = Math.toIntExact(mouthClient + snapshot.getDayAddClient());
                                }
                                if (sdf.format(exposureTotal.getUpdateTime()).startsWith(mouth)) {
                                    mouthClient = Math.toIntExact(mouthClient + exposureTotal.getDayAddClient());
                                }
                                returnMap.put(mouth, mouthClient);
                            }
                            return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功", returnMap);
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public ResponseVo<?> getMouthExcellent() {
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser sysUser = loginUser.getSysUser();
        List<ExcellentStaffVo> excellentStaffVoList = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
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
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                            String format = sdf.format(new Date());
                            for (Client client : clientList) {
                                if (sdf.format(client.getCreatedTime()).startsWith(format)) {
                                    clients.add(client);
                                }
                            }
                            excellentStaffVo.setMouthClient(clients.size());
                            clients = new ArrayList<>();
                            //TODO 互动数
                            LambdaQueryWrapper<ExposureTotal> exposureTotalQueryWrapper = new LambdaQueryWrapper<>();
                            exposureTotalQueryWrapper.eq(ExposureTotal::getCreateBy, user.getId());
                            ExposureTotal exposureTotal = exposureTotalMapper.selectOne(exposureTotalQueryWrapper);
                            if (Objects.isNull(exposureTotal)) {
                                excellentStaffVo.setMouthActive(0);
                                excellentStaffVo.setMouthVisitor(0);
                            } else {
                                List<ExpoSnapshot> snapshotList = new ArrayList<>();
                                LambdaQueryWrapper<ExpoSnapshot> expoSnapshotQueryWrapper = new LambdaQueryWrapper<>();
                                expoSnapshotQueryWrapper.eq(ExpoSnapshot::getExpoTotalId, exposureTotal.getId());
                                List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(expoSnapshotQueryWrapper);
                                //todo 拿到分组后的list
                                for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                    if (sdf.format(expoSnapshot.getCreateTime()).startsWith(format)) {
                                        snapshotList.add(expoSnapshot);
                                    }
                                }
                                //TODO 分组后计算
                                int active = 0;
                                int visitor = 0;
                                for (ExpoSnapshot snapshot : snapshotList) {
                                    active = active + Math.toIntExact(snapshot.getDayTotal() + snapshot.getDayNotesNum() + snapshot.getDayDownloadNum() + snapshot.getDayAddContact() + snapshot.getDayAddClient());
                                    visitor = visitor + Math.toIntExact(snapshot.getDayTotal());
                                }
                                if (sdf.format(exposureTotal.getUpdateTime()).equals(format)) {
                                    active = Math.toIntExact(active + exposureTotal.getDayTotal() + exposureTotal.getDayDownloadNum() + exposureTotal.getDayAddClient() + exposureTotal.getDayAddContact() + exposureTotal.getDayNotes() + exposureTotal.getDayForwardNum());
                                    visitor = Math.toIntExact(visitor + exposureTotal.getDayTotal());
                                }
                                excellentStaffVo.setMouthActive(active);
                                excellentStaffVo.setMouthVisitor(visitor);
                            }
                        }
                        excellentStaffVoList.add(excellentStaffVo);
                    }
                    //TODO 排序 按照mouthActive排序 需要从写一个方法
                    Collections.sort(excellentStaffVoList);
                    returnMap.put("excellentList", excellentStaffVoList);
                }
                return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功：按照mouthActive排序", returnMap);
            }
        }
    }


    //TODO 对比分组
    public List<SysUser> compareToCDepIdAndSDepId(List<SysUser> userList, List<CompanyFrame> companyFrameList) {
        List<SysUser> sysUserList = new ArrayList<>();
        for (CompanyFrame companyFrame : companyFrameList) {
            for (SysUser sysUser : userList) {
                if (Objects.equals(companyFrame.getId(), sysUser.getDepId())) {
                    sysUserList.add(sysUser);
                }
            }
        }
        return sysUserList;
    }
}