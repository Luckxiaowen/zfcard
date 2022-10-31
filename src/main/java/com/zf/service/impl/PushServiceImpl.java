package com.zf.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.service.PushService;
import com.zf.utils.HttpUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class PushServiceImpl implements PushService {

    private String appSecret="2fbfd1e16477508775aa540288a79d3b";
    private String appId="wx4aea51614141f532";

    List<JSONObject> errorList = new ArrayList();

    @Override
    public ResponseVo<?> getAllOpenId() {
        //拿到关注公共号的所有openId
        String assessToken = getAssessToken();
        String url ="https://api.weixin.qq.com/cgi-bin/user/get?access_token="+assessToken;
        String s = HttpUtil.sendGet(url);
        JSONObject json = (JSONObject) JSONObject.toJSON(JSON.parse(s));
        System.out.println("json = " + json);
        String data = json.getString("data");
        JSONObject dataJson = (JSONObject) JSONObject.toJSON(JSON.parse(data));
        String openIdData = dataJson.getString("openid");
        openIdData=  openIdData.replace("[","");
        openIdData=openIdData.replace("]","");
        openIdData=openIdData.replace("\"","");
        System.out.println("openIdData = " + openIdData);
        String[] openIds = openIdData.split(",");
        return new ResponseVo<>(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",openIds);
    }

    @Override
    public String getAssessToken() {
        //这里直接写死就可以，不用改，用法可以去看api
        String grant_type = "client_credential";
        //封装请求数据
        String params = "grant_type=" + grant_type + "&secret=" + appSecret + "&appid=" + appId;
        //发送GET请求
        String sendGet = HttpUtil.sendGet("https://api.weixin.qq.com/cgi-bin/token", params);
        // 解析相应内容（转换成json对象）
        com.alibaba.fastjson.JSONObject jsonObject1 = com.alibaba.fastjson.JSONObject.parseObject(sendGet);
        String accessToken = (String) jsonObject1.get("access_token");
        return accessToken;
    }




    @Override
    public String sendMsg() {
        String assessToken = getAssessToken();
        String [] openIds = (String[]) this.getAllOpenId().getData();
        for (String openId : openIds) {
            JSONObject pushMsg = new JSONObject(new LinkedHashMap<>());
            pushMsg.put("openId",openId);
            String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + assessToken;
            String sendPost = HttpUtil.sendPost(url, pushMsg.toJSONString());
            JSONObject WeChatMsgResult = JSONObject.parseObject(sendPost);
            if (!"0".equals(WeChatMsgResult.getString("errcode"))) {
                JSONObject error = new JSONObject();
                error.put("openid", openId);
                error.put("errorMessage", WeChatMsgResult.getString("errmsg"));
                errorList.add(error);
            }
        }
        JSONObject result = new JSONObject();
        result.put("result", "success");
        result.put("errorData", errorList);
        return result.toJSONString();
    }
}
