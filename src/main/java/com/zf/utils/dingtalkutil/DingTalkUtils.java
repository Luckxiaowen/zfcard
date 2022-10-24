package com.zf.utils.dingtalkutil;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.taobao.api.ApiException;
import com.zf.domain.dto.DingTalkDto;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import org.springframework.stereotype.Component;
import com.aliyun.dingtalkoauth2_1_0.Client;
import com.aliyun.tea.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class DingTalkUtils {

    private static String ACCESS_TOKEN="13be62f5643b3c95a97e84a9dc0cfb50";

    public OapiV2DepartmentListsubResponse getDepartment(){
        OapiV2DepartmentListsubResponse response=new OapiV2DepartmentListsubResponse();
        try {
            DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/department/listsub?access_token="+ACCESS_TOKEN);
            OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
            response = client.execute(req, "");
            System.out.println(response.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
     return response;
    }


    public static Client createClient() throws Exception {
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }

    public ResponseVo<?> getAssessToken(DingTalkDto dingTalkDto) throws Exception {

        GetAccessTokenResponse accessToken = new GetAccessTokenResponse();
        HashMap<String, Object> resMap = new HashMap<>();
        List<String> args = new ArrayList<>();
        Client client = DingTalkUtils.createClient();
        GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest();
        getAccessTokenRequest.setAppKey(dingTalkDto.getAppKey()).setAppSecret(dingTalkDto.getAppSecret());
        accessToken = client.getAccessToken(getAccessTokenRequest);
        resMap.put("assessToken",accessToken.getBody().accessToken);
        return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "123", resMap);
    }

}
