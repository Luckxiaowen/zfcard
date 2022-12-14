package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zf.domain.entity.Client;
import com.zf.domain.entity.ExpoSnapshot;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.vo.ClientVo;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.ClientMapper;
import com.zf.mapper.ExpoSnapshotMapper;
import com.zf.mapper.ExposureTotalMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.ClientService;
import com.zf.utils.DateUtil;
import com.zf.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author Amireux
* @description 针对表【client(客户表)】的数据库操作Service实现
* @createDate 2022-09-16 08:47:16
*/
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client> implements ClientService {

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ExpoSnapshotMapper expoSnapshotMapper;

    @Autowired
    private ExposureTotalMapper exposureTotalMapper;

    @Override
    public ResponseVo addClient(Client client) {
        int insert = clientMapper.insert(client);
        if (insert>0){
            LambdaQueryWrapper<ExposureTotal>queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(ExposureTotal::getCreateBy,client.getCreatedBy());
            ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper);
            if (Objects.isNull(exposureTotal)){
                return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加用户失败：曝光统计不存在当前用户记录信息");
            }else {
                exposureTotal.setDayAddClient(exposureTotal.getDayAddClient()+1);
                exposureTotalMapper.updateById(exposureTotal);
            }
            return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加成功");
        }else{
            return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加失败");
        }
    }

    @Override
    public ResponseVo clientSummary(String userId) throws ParseException {

        if (Objects.isNull(sysUserMapper.selectById(Long.parseLong(userId)))){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"未查询用户!");
        }else{
            if (StringUtils.isEmpty(sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid())){
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"当前用户不属于任何公司!");
            }else{
                HashMap<String,Object>map=new HashMap<>();
                //TODO 客户总量!
                LambdaQueryWrapper<Client>queryWrapper=new LambdaQueryWrapper<>();
                queryWrapper.eq(Client::getCreatedBy,userId);
                Integer ClientTotal = clientMapper.selectCount(queryWrapper);
                map.put("ClientTotal",ClientTotal);
                //TODO 今日新增
                LambdaQueryWrapper<ExposureTotal>lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(ExposureTotal::getCreateBy,Long.parseLong(userId));
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(lambdaQueryWrapper);

                if (!Objects.isNull(exposureTotal)){
                    map.put("ClientDay",exposureTotal.getDayAddClient());
                    //TODO 近七日新增客户
                    List<String> sevenDate = DateUtil.getSevenDate();
                    List<Integer> sevenDayVisitorList = exposureTotalMapper.selectSevenDayByExposureTotal(exposureTotal.getId(), sevenDate);
                    if (sevenDayVisitorList.size()==0){
                        map.put("sevenTotal",0);
                    }else {
                        Integer sevenTotal=0;
                        ResponseVo sevenClientTrend = this.sevenClientTrend(userId);
                        TreeMap data= (TreeMap) sevenClientTrend.getData();
                        Set<Map.Entry<String, Long>> entrySet = data.entrySet();
                        if (entrySet.size()==0){
                            map.put("sevenTotal",0);
                        }else {
                            for (Map.Entry<String, Long> entry : entrySet) {
                                Long value = entry.getValue();
                                sevenTotal = Math.toIntExact(sevenTotal + value);
                            }
                        }
                        map.put("sevenTotal",sevenTotal);
                    }
                }else{
                    map.put("ClientDay",0);
                }
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),map);
            }
        }
    }

    @Override
    public ResponseVo sevenClientTrend(String userId) throws ParseException {
        Map<String,Object>hashMap=new TreeMap<>(new Comparator<String>() {
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
                System.out.println("exposureTotal = " + exposureTotal);
                LambdaQueryWrapper<ExpoSnapshot> wrapper=new LambdaQueryWrapper<>();
                for (String date : sevenDate) {
                    wrapper.like(ExpoSnapshot::getCreateTime,date);
                    wrapper.eq(ExpoSnapshot::getExpoTotalId,exposureTotal.getId());
                    ExpoSnapshot expoSnapshot = expoSnapshotMapper.selectOne(wrapper);
                    wrapper=new LambdaQueryWrapper<>();
                    String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();

                    if (Objects.isNull(expoSnapshot)||expoSnapshot.getDayAddClient()==null||"".equals(expoSnapshot.getDayAddClient())){
                        hashMap.put(date,0L);
                    }else{
                        hashMap.put(date,expoSnapshot.getDayAddClient());
                        if (date.equals(nowDate)){
                            System.out.println("nowDate = " + nowDate);
                            hashMap.put(nowDate,exposureTotal.getDayAddClient());
                            System.out.println("exposureTotal = " + exposureTotal.getDayAddClient());
                        }
                    }
                }

            }

            return ResponseVo.okResult(hashMap);
        }
    }



    @Override
    public ResponseVo searchAll(String userId) {
        LoginUser loginUser = UserUtils.getLoginUser();
        if (loginUser.getSysUser()==null){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"当前员工不存在!");
        }else{
            LambdaQueryWrapper<Client>queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(Client::getCreatedBy,Long.parseLong(userId))
                    .eq(Client::getDelFlag,0);
          List<ClientVo> clientVoLists= clientMapper.selectListByMe(userId);
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功",clientVoLists);
        }

    }

    @Override
    public ResponseVo clientVisitor(Integer staffId,Integer time) {
        ExposureTotal exposureTotal = exposureTotalMapper.selectOne(new LambdaQueryWrapper<ExposureTotal>().eq(ExposureTotal::getCreateBy, staffId));
        if (Objects.isNull(exposureTotal)){
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        Integer totalTime = exposureTotal.getAverageStayMin() + time;
        exposureTotal.setAverageStayMin(totalTime);
        int stayNum = exposureTotal.getStayNum();
        int newStayNum=stayNum+1;
        exposureTotal.setStayNum(newStayNum);
        Long visitorTotal = exposureTotal.getVisitorTotal();
        Long newVisitorTotal=visitorTotal+1;
        exposureTotal.setVisitorTotal(newVisitorTotal);
        exposureTotalMapper.updateById(exposureTotal);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"操作成功：访客数+1，时常"+time+"用户访问次数+1");
    }
}
