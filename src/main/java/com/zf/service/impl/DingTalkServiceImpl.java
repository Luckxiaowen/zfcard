package com.zf.service.impl;

import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.zf.domain.dto.DingTalkDto;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.DingTalkService;
import com.zf.utils.dingtalkutil.DingTalkUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DingTalkServiceImpl implements DingTalkService {

    @Override
    public ResponseVo<?> getDep() {
        List<String>stringList=new ArrayList<>();
        List<Long>idList=new ArrayList<>();
        HashMap<String,Object>depMap=new HashMap<>();
        DingTalkUtils dingTalkUtils=new DingTalkUtils();
        OapiV2DepartmentListsubResponse department = dingTalkUtils.getDepartment();
        List<OapiV2DepartmentListsubResponse.DeptBaseResponse> result = department.getResult();
        if (result.size()==0){
            return new ResponseVo<>(AppHttpCodeEnum.FAIL.getCode(), "同步失败: 当前组织架构为空");
        }else {
            for (OapiV2DepartmentListsubResponse.DeptBaseResponse deptBaseResponse : result) {
                stringList.add(deptBaseResponse.getName());
                idList.add(deptBaseResponse.getDeptId());
            }
            depMap.put("depName",stringList);
            depMap.put("depId",idList);
        }
        return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",depMap);
    }

    @Override
    public ResponseVo<?> getAssessToken(DingTalkDto dingTalkDto) throws Exception {
        ResponseVo<?> assessToken = new DingTalkUtils().getAssessToken(dingTalkDto);
        return null;
    }
}
