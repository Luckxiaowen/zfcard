package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zf.domain.entity.Client;
import com.zf.domain.entity.ExpoSnapshot;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
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
            return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加成功");
        }else{
            return ResponseVo.okResult(AppHttpCodeEnum.SUCCESS.getCode(),"添加失败");
        }
    }

    @Override
    public ResponseVo clientSummary(String userId) {

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
                    //TODO 近七日新增
                    List<String> sevenDate = DateUtil.getSevenDate();
                    List<Integer> sevenDayByDate=expoSnapshotMapper.selectSevenDayByDate(exposureTotal.getId(),sevenDate);
                    if (sevenDayByDate.size()==0){
                        map.put("sevenTotal",0);
                    }else{
                        Integer sevenTotal=0;
                        for (Integer integer : sevenDayByDate) {
                            sevenTotal=sevenTotal+integer;
                        }
                        map.put("sevenTotal",sevenTotal);
                        System.out.println("sevenTotal = " + sevenTotal);
                    }
                }else{
                    map.put("ClientDay",0);
                }
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg(),map);
            }
        }
    }

    @Override
    public ResponseVo sevenClientTrend(String userId) {
        List<HashMap>list=new ArrayList<>();
        HashMap<String,Object>hashMap=new HashMap<>();
        if (Objects.isNull(sysUserMapper.selectById(Long.parseLong(userId)))) {
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "未查询用户!");
        } else {
            if (StringUtils.isEmpty(sysUserMapper.selectById(Long.parseLong(userId)).getCompanyid())) {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前用户不属于任何公司!");
            } else {
                //TODO 近七日新增折线图
                List<String> sevenDate = DateUtil.getSevenDate();
                LambdaQueryWrapper<ExposureTotal>lambdaQueryWrapper=new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(ExposureTotal::getCreateBy,Long.parseLong(userId));
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(lambdaQueryWrapper);
                LambdaQueryWrapper<ExpoSnapshot> wrapper=new LambdaQueryWrapper<>();

                for (String date : sevenDate) {
                    wrapper.like(ExpoSnapshot::getCreateTime,date);
                    wrapper.eq(ExpoSnapshot::getExpoTotalId,exposureTotal.getId());
                    ExpoSnapshot expoSnapshot = expoSnapshotMapper.selectOne(wrapper);
                    if (Objects.isNull(expoSnapshot)||expoSnapshot.getDayAddClient()==null||"".equals(expoSnapshot.getDayAddClient())){
                        hashMap.put(date,0);
                    }else{
                        hashMap.put(date,expoSnapshot.getDayAddClient());
                    }
                    wrapper=new LambdaQueryWrapper<>();
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
            clientMapper.selectList(queryWrapper);
            return ResponseVo.okResult(clientMapper.selectList(queryWrapper));
        }

    }
}
