package com.zf.service.timetaskjob;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zf.domain.entity.ExpoSnapshot;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.entity.SysUser;
import com.zf.mapper.ExpoSnapshotMapper;
import com.zf.mapper.ExposureTotalMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InsertSnapshotJob {


    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ExpoSnapshotMapper expoSnapshotMapper;
    @Autowired
    private ExposureTotalMapper exposureTotalMapper;
    //todo 每天凌晨清空清空曝光统计表 将数据添加至快照表
    @Scheduled(cron = "0 0 0 * * ?")
    public void CheckAndInsertSnapshotJob() {
        //TODO。查询那些账户需要执行定时任务
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getStatus, 0).eq(SysUser::getDelFlag, 0);
        List<SysUser> sysUserList = sysUserMapper.selectList(queryWrapper);
        if (sysUserList.size() == 0) {
            System.out.println("执行定时任务失败");
        } else {
            for (SysUser sysUser : sysUserList) {

                LambdaQueryWrapper<ExposureTotal> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(ExposureTotal::getCreateBy, sysUser.getId());
                ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper1);
                if (exposureTotal == null) {
                    System.out.println("在total表里未查询到数据" + sysUser.getId());
                    ExposureTotal exposureTotal1 = new ExposureTotal();
                    exposureTotal1.setCreateBy(sysUser.getId());
                    exposureTotal1.setUpdateTime(new Date());
                    exposureTotal1.setCreateTime(new Date());
                    exposureTotalMapper.insert(exposureTotal1);
                } else {

                    ExpoSnapshot expoSnapshot = new ExpoSnapshot();
                    expoSnapshot.setExpoTotalId(exposureTotal.getId());
                    expoSnapshot.setDayTotal(exposureTotal.getDayTotal());
                    expoSnapshot.setDayNotesNum(exposureTotal.getDayNotes());
                    expoSnapshot.setDayDownloadNum(exposureTotal.getDayDownloadNum());

                    expoSnapshot.setDayAddClient(exposureTotal.getDayAddClient());
                    expoSnapshot.setDayAddContact(exposureTotal.getDayAddContact());
                    expoSnapshot.setWeekClientNum(exposureTotal.getWeekAddClient());
                    expoSnapshot.setWeekVisitorNum(exposureTotal.getVisitorTotal());
                    expoSnapshot.setAverageStayMin(exposureTotal.getAverageStayMin());
                    expoSnapshot.setStayNum(exposureTotal.getStayNum());

                    expoSnapshot.setCreateTime(new Date());
                    expoSnapshotMapper.insert(expoSnapshot);

                    List<ExposureTotal> exposureTotals = exposureTotalMapper.selectList(null);
                    for (ExposureTotal total : exposureTotals) {
                        total.setUpdateTime(new Date());
                        total.setDayDownloadNum(0L);
                        total.setDayTotal(0L);
                        total.setDayNotes(0L);
                        total.setDayAddContact(0L);
                        total.setDayAddClient(0L);
                        total.setDayForwardNum(0L);
                        total.setWeekAddClient(0L);
                        total.setSevenTotal(0L);
                        total.setAverageStayMin(0);
                        List<String> sevenDate = DateUtil.getSevenDate();
                        List<ExpoSnapshot> expoSnapshotList = expoSnapshotMapper.selectSevenDayByTotalId(total.getId(), sevenDate);

                        for (ExpoSnapshot snapshot : expoSnapshotList) {
                            total.setSevenTotal(snapshot.getWeekVisitorNum());
                            total.setWeekAddClient(snapshot.getWeekClientNum());
                        }
                        exposureTotalMapper.updateById(total);
                    }
                }
            }
        }
    }

}
