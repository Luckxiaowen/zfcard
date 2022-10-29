package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.ExpoSnapshot;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ExposureVo;
import com.zf.domain.vo.PersonVisitorVo;
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
                        ResponseVo sevenVisitorTrend = this.getSevenVisitorTrend(String.valueOf(userId));
                        TreeMap data = (TreeMap) sevenVisitorTrend.getData();
                        Set<Map.Entry<String, Long>> entrySet = data.entrySet();
                        if (entrySet.size() == 0) {
                            visitorMap.put("sevenVisitorTotal", 0);
                        } else {
                            for (Map.Entry<String, Long> entry : entrySet) {
                                Long value = entry.getValue();
                                sevenVisitorTotal = Math.toIntExact(sevenVisitorTotal + value);
                            }
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
        Map<String, Object> treeMap = new TreeMap<>(new Comparator<String>() {
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
                LambdaQueryWrapper<ExposureTotal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(ExposureTotal::getCreateBy, Long.parseLong(userId));
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(lambdaQueryWrapper);
                LambdaQueryWrapper<ExpoSnapshot> wrapper = new LambdaQueryWrapper<>();
                for (String date : sevenDate) {
                    wrapper.like(ExpoSnapshot::getCreateTime, date);
                    wrapper.eq(ExpoSnapshot::getExpoTotalId, exposureTotal.getId());
                    ExpoSnapshot expoSnapshot = expoSnapshotMapper.selectOne(wrapper);
                    wrapper = new LambdaQueryWrapper<>();
                    if (Objects.isNull(expoSnapshot) || expoSnapshot.getDayTotal() == null || "".equals(expoSnapshot.getDayTotal())) {
                        treeMap.put(date, 0L);
                    } else {
                        treeMap.put(date, expoSnapshot.getDayTotal());
                    }
                    String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
                    if (date.equals(nowDate)) {
                        treeMap.put(nowDate, exposureTotal.getDayTotal());
                    }
                }
            }

            return ResponseVo.okResult(treeMap);
        }
    }


    @Override
    public ResponseVo getExposureHistory(String id,String startTime,String endTime) {
        HashMap<String, Object> historyMap = new HashMap<>();
        ExposureVo totalData = new ExposureVo();
        ExposureVo averageData = new ExposureVo();
        List<ExposureVo> exposureVoList = new ArrayList<>();
        List<ExposureVo> exposureVoListByReturn = new ArrayList<>();
        if ("".equals(id) || id == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取访客概况失败:用户Id为空");
        } else {
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取访客概况失败:未查询到用户");
            } else {
                if ("".equals(startTime)&& "".equals(endTime)){
                    totalData= expoSnapshotMapper.selectHistoryWithOut(String.valueOf(userId));
                    averageData.setVisitor(totalData.getVisitor()/totalData.getCount());
                    int dayAverage= totalData.getStay()/totalData.getCount()/60;
                    averageData.setStayMin(totalData.getStayMin()/dayAverage);
                    averageData.setDownload(totalData.getDownload()/totalData.getCount());
                    averageData.setContact(totalData.getContact()/totalData.getCount());
                    averageData.setComment(totalData.getComment()/totalData.getCount());
                    totalData.setStayMin(totalData.getStayMin()/60);
                    historyMap.put("totalData",totalData);
                    historyMap.put("averageDate",averageData);
                    LambdaQueryWrapper<ExposureTotal>queryWrapper=new LambdaQueryWrapper<>();
                    queryWrapper.eq(ExposureTotal::getCreateBy,userId);
                    ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
                    if (Objects.isNull(exposureTotal)){
                        exposureVoList=null;
                    }else {
                        ExposureVo exposureVo=new ExposureVo();
                        exposureVo.setDate(exposureTotal.getUpdateTime());
                        exposureVo.setVisitor(Math.toIntExact(exposureTotal.getDayTotal()));
                        exposureVo.setStay(exposureTotal.getAverageStayMin()/exposureTotal.getStayNum()/60);
                        exposureVo.setDownload(Math.toIntExact(exposureTotal.getDayDownloadNum()));
                        exposureVo.setContact(Math.toIntExact(exposureTotal.getDayAddContact()));
                        exposureVo.setComment(Math.toIntExact(exposureTotal.getDayNotes()));
                        exposureVoList.add(exposureVo);
                        exposureVoList=expoSnapshotMapper.selectListById(Math.toIntExact(exposureTotal.getId()));
                        for (ExposureVo vo : exposureVoList) {
                            if (vo.getStay()==null||vo.getStay()==0){
                                vo.setStayMin((double) 0);
                            }
                            vo.setStayMin(vo.getStayMin()/vo.getStay()/60);
                            exposureVoListByReturn.add(vo);
                        }

                        historyMap.put("exposureVoList",exposureVoListByReturn);
                    }
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功:", historyMap);
                }else {
                    if ("".equals(startTime)){
                        startTime=null;
                    }
                    if ("".equals(endTime)){
                        endTime=null;
                    }
                    totalData= expoSnapshotMapper.selectHistory(String.valueOf(userId),startTime,endTime);
                    averageData.setVisitor(totalData.getVisitor()/totalData.getCount());
                    int dayAverage= totalData.getStay()/totalData.getCount();
                    averageData.setStayMin(totalData.getStayMin()/dayAverage/60);
                    averageData.setDownload(totalData.getDownload()/totalData.getCount());
                    averageData.setContact(totalData.getContact()/totalData.getCount());
                    averageData.setComment(totalData.getComment()/totalData.getCount());
                    totalData.setStayMin(totalData.getStayMin()/60);
                    historyMap.put("totalData",totalData);
                    historyMap.put("averageDate",averageData);
                    LambdaQueryWrapper<ExposureTotal>queryWrapper=new LambdaQueryWrapper<>();
                    queryWrapper.eq(ExposureTotal::getCreateBy,userId);
                    ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
                    if (Objects.isNull(exposureTotal)){
                        exposureVoList=null;
                    }else {
                        ExposureVo exposureVo=new ExposureVo();
                        exposureVo.setDate(exposureTotal.getUpdateTime());
                        exposureVo.setVisitor(Math.toIntExact(exposureTotal.getDayTotal()));
                        exposureVo.setStay(exposureTotal.getAverageStayMin()/exposureTotal.getStayNum()/60);
                        exposureVo.setDownload(Math.toIntExact(exposureTotal.getDayDownloadNum()));
                        exposureVo.setContact(Math.toIntExact(exposureTotal.getDayAddContact()));
                        exposureVo.setComment(Math.toIntExact(exposureTotal.getDayNotes()));
                        exposureVoList.add(exposureVo);
                        exposureVoList=expoSnapshotMapper.selectListByTime(String.valueOf(exposureTotal.getId()),startTime,endTime);
                        for (ExposureVo vo : exposureVoList) {
                            if (vo.getStay()==null||vo.getStay()==0){
                                vo.setStayMin((double) 0);
                            }
                            vo.setStayMin(vo.getStayMin()/vo.getStay()/60);
                            exposureVoListByReturn.add(vo);
                        }

                        historyMap.put("exposureVoList",exposureVoListByReturn);
                    }
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功:", historyMap);
                }
            }
        }
    }

    @Override
    public ResponseVo updateVisitor(String userId) {
        if ("".equals(userId) || userId == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新访客量失败：用户Id为空");
        } else {
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新访客量失败：不存在当前用户");
            } else {
                LambdaQueryWrapper<ExposureTotal> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ExposureTotal::getCreateBy, userId);
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
                if (!Objects.isNull(exposureTotal)) {
                    Long dayTotal = exposureTotal.getDayTotal();
                    exposureTotal.setDayTotal(dayTotal + 1);
                    exposureTotal.setVisitorTotal(exposureTotal.getVisitorTotal()+1);
                    exposureTotalMapper.updateById(exposureTotal);
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "访客量更新成功");
                } else {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "更新访客量失败：未知错误");
                }
            }
        }
    }

    @Override
    public ResponseVo getDayData(String userId) {
        HashMap<String,Object>returnMap=new HashMap<>();
        if ("".equals(userId)||userId==null){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取个人中心数据失败：用户Id的为空");
        }else {
            if (Objects.isNull(sysUserMapper.selectById(userId))){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取个人中心数据失败：不存在此用户");
            }else {
                LambdaQueryWrapper<ExposureTotal>queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(ExposureTotal::getCreateBy,userId);
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
                if (Objects.isNull(exposureTotal)){
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取个人中心数据失败：不存在此用户的数据");
                }else {
                    PersonVisitorVo personVisitorVo = new PersonVisitorVo();
                    personVisitorVo.setDayVisitor(Math.toIntExact(exposureTotal.getDayTotal()));
                    personVisitorVo.setDayNotes(Math.toIntExact(exposureTotal.getDayNotes()));
                    personVisitorVo.setDayContact(Math.toIntExact(exposureTotal.getDayAddContact()));
                    personVisitorVo.setDayDownloadCard(Math.toIntExact(exposureTotal.getDayDownloadNum()));
                    returnMap.put("centerData",personVisitorVo);
                }
            }
        }
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功",returnMap);
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
