package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.ExpoSnapshot;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ExposureVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.ExpoSnapshotMapper;
import com.zf.mapper.ExposureTotalMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.ExposureTotalService;
import com.zf.utils.DateUtil;
import com.zf.utils.JwtUtil;
import com.zf.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Amireux
 * @description 针对表【exposure_total(曝光统计)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:17
 */
@Service
public class ExposureTotalServiceImpl extends ServiceImpl<ExposureTotalMapper, ExposureTotal> implements ExposureTotalService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private ExposureTotalMapper exposureTotalMapper;

    @Resource
    private ExpoSnapshotMapper expoSnapshotMapper;

    @Override
    public ResponseVo getVisitorNum(String id) {
        HashMap<String, Object> visitorMap = new HashMap<>();
        if ("".equals(id) || id == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取访客概况失败:用户Id为空");
        } else {
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取访客概况失败:未查询到用户");
            } else {
                LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ExposureTotal::getCreateBy, userId);
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
                if (!Objects.isNull(exposureTotal)) {
                    //TODO 访客总量
                    visitorMap.put("visitorTotal", exposureTotal.getVisitorTotal());
                    //TODO 今日访客量
                    visitorMap.put("dayVisitorNum", exposureTotal.getDayTotal());
                    //TODO 近七日访客量
                    List<String> sevenDate = DateUtil.getSevenDate();
                    List<Integer> sevenDayVisitorList = exposureTotalMapper.selectSevenDayByExposureTotal(exposureTotal.getId(), sevenDate);
                    if (sevenDayVisitorList.size() == 0) {
                        visitorMap.put("sevenVisitorTotal", 0);
                    } else {
                        Integer sevenVisitorTotal = 0;
                        for (Integer integer : sevenDayVisitorList) {
                            sevenVisitorTotal = sevenVisitorTotal + integer;
                        }
                        visitorMap.put("sevenVisitorTotal", sevenVisitorTotal);
                    }

                } else {
                    visitorMap.put("visitorTotal", 0);
                    visitorMap.put("dayVisitorNum", 0);

                }
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "获取访客成功", visitorMap);
            }

        }

    }

    @Override
    public ResponseVo getSevenVisitorTrend(String userId) {
        Map<String,Object>treeMap=new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return t1.compareTo(s);
            }
        });
        if (Objects.isNull(sysUserMapper.selectById(Long.parseLong(userId)))) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "未查询用户!");
        } else {
            if (StringUtils.isEmpty(sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid())) {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前用户不属于任何公司!");
            } else {
                //TODO 近七日新增折线图
                /*拿到七天的日期*/
                List<String> sevenDate = DateUtil.getSevenDate();
                LambdaQueryWrapper<ExposureTotal>lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(ExposureTotal::getCreateBy,Long.parseLong(userId));
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(lambdaQueryWrapper);
                LambdaQueryWrapper<ExpoSnapshot> wrapper=new LambdaQueryWrapper<>();
                for (String date : sevenDate) {
                    wrapper.like(ExpoSnapshot::getCreateTime,date);
                    wrapper.eq(ExpoSnapshot::getExpoTotalId,exposureTotal.getId());
                    ExpoSnapshot expoSnapshot = expoSnapshotMapper.selectOne(wrapper);
                    wrapper=new LambdaQueryWrapper<>();
                    if (Objects.isNull(expoSnapshot)||expoSnapshot.getDayTotal()==null||"".equals(expoSnapshot.getDayTotal())){
                        treeMap.put(date,0);
                    }else{
                        treeMap.put(date,expoSnapshot.getDayTotal());
                    }
                    String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
                    if (date.equals(nowDate)){
                        treeMap.put(nowDate,exposureTotal.getDayTotal());
                    }
                }
            }

            return ResponseVo.okResult(treeMap);
        }
    }

    @Override
    public ResponseVo getExposureHistory(String id) {
        HashMap<String,Object>historyMap=new HashMap<>();
        ExposureVo totalData = new ExposureVo();
        ExposureVo averageData =new ExposureVo();
        List<ExposureVo>exposureVoList=new ArrayList<>();
        if ("".equals(id) || id == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取访客概况失败:用户Id为空");
        }else {
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取访客概况失败:未查询到用户");
            }else {
                LambdaQueryWrapper<ExposureTotal>queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(ExposureTotal::getCreateBy,userId);
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
                if (!Objects.isNull(exposureTotal)){
                    //TODO 访客总量
                    totalData.setVisitor(Math.toIntExact(exposureTotal.getVisitorTotal()));
                    LambdaQueryWrapper<ExpoSnapshot>wrapper=new LambdaQueryWrapper<>();
                    wrapper.eq(ExpoSnapshot::getExpoTotalId,exposureTotal.getId());
                    Integer count = expoSnapshotMapper.selectCount(wrapper);
                    if (count==0){
                        //TODO 日均访问量
                        averageData.setVisitor(0);
                        //TODO 日均访问时常
                        averageData.setStay(0);
                        //TODO 日均保存名片数
                        averageData.setDownload(0);
                    }else {
                        averageData.setVisitor(Math.toIntExact(exposureTotal.getVisitorTotal() / count));
                        Integer averageStayMin = exposureTotal.getAverageStayMin();
                        if (averageStayMin==null){
                            totalData.setStay(0);
                        }else {
                            totalData.setStay(averageStayMin);
                            averageData.setStay(averageStayMin/count);

                            //TODO 名片下载总量以及日均名片下载 通讯录 留言
                            Integer saveCard=0;
                            Integer saveContact=0;
                            Integer addNotesTotal=0;
                            List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(wrapper);
                            if (expoSnapshotList.size()==0){
                                totalData.setDownload(saveCard);
                                averageData.setDownload(saveCard);
                                averageData.setContact(saveContact);
                                averageData.setComment(addNotesTotal);
                            }else {
                                for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                                    saveCard= saveCard+Math.toIntExact(expoSnapshot.getDayDownloadNum());
                                    saveContact= Math.toIntExact(saveContact + expoSnapshot.getDayAddClient());
                                    addNotesTotal= Math.toIntExact(addNotesTotal + expoSnapshot.getDayNotesNum());
                                }
                                totalData.setDownload(saveCard);
                                totalData.setContact(saveContact);
                                totalData.setComment(addNotesTotal);
                                averageData.setDownload(saveCard/expoSnapshotList.size());
                                averageData.setContact(saveContact/expoSnapshotList.size());
                                averageData.setComment(addNotesTotal/expoSnapshotList.size());

                            }
                        }
                    }
                    historyMap.put("totalData",totalData);
                    historyMap.put("averageData",averageData);
                    ExposureVo exposureVo = new ExposureVo();
                    exposureVo.setDate(exposureTotal.getUpdateTime());
                    exposureVo.setComment(Math.toIntExact(exposureTotal.getDayNotes()));
                    exposureVo.setStay(exposureTotal.getAverageStayMin()/exposureTotal.getStayNum());
                    exposureVo.setDownload(Math.toIntExact(exposureTotal.getDayDownloadNum()));
                    exposureVo.setContact(Math.toIntExact(exposureTotal.getDayAddContact()));
                    exposureVo.setVisitor(Math.toIntExact(exposureTotal.getDayTotal()));
                    exposureVoList.add(exposureVo);
                    List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectList(wrapper);
                    for (ExpoSnapshot expoSnapshot : expoSnapshotList) {
                        exposureVo=new ExposureVo();
                        exposureVo.setDate(expoSnapshot.getCreateTime());
                        exposureVo.setComment(Math.toIntExact(expoSnapshot.getDayNotesNum()));
                        if (expoSnapshot.getStayNum()==0){
                            exposureVo.setStay(0);
                        }else {
                            exposureVo.setStay(expoSnapshot.getAverageStayMin()/expoSnapshot.getStayNum());
                        }
                        exposureVo.setDownload(Math.toIntExact(expoSnapshot.getDayDownloadNum()));
                        exposureVo.setContact(Math.toIntExact(expoSnapshot.getDayAddContact()));
                        exposureVo.setVisitor(Math.toIntExact(expoSnapshot.getDayTotal()));
                        exposureVoList.add(exposureVo);
                    }
                    historyMap.put("dataList",exposureVoList);
                }
            }
        }
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功:",historyMap);
    }

    @Override
    public ResponseVo updateVisitor(String userId) {
        if ("".equals(userId)||userId==null){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新访客量失败：用户Id为空");
        }else {
            if (Objects.isNull(sysUserMapper.selectById(userId))){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新访客量失败：不存在当前用户");
            }else{
                LambdaQueryWrapper<ExposureTotal>queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(ExposureTotal::getCreateBy,userId);
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
                if (!Objects.isNull(exposureTotal)){
                    Long dayTotal = exposureTotal.getDayTotal();
                    exposureTotal.setDayTotal(dayTotal+1);
                    exposureTotalMapper.updateById(exposureTotal);
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "访客量更新成功");
                }else{
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新访客量失败：未知错误");
                }
            }
        }

    }

    public Integer getInteger(String userId) {
        int id;
        if (Validator.isNumeric(userId)) {
            id = Integer.parseInt(userId);
        } else {
            //TODO 员工token获取
            try {
                String subject = JwtUtil.parseJWT(userId).getSubject();
                id = Integer.parseInt(subject);
            } catch (Exception e) {
                throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
            }
        }
        return id;
    }
}
