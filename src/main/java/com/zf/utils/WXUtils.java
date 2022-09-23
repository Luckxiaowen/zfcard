package com.zf.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;
import reactor.util.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WXUtils {

    /**
     * WXLogin:
     * <p>
     * APP_ID: wxafcffece6679f01a
     * SECRET: 83b297e0e7412cf259b6e0346ed5d1f6
     * JS_CODE: 023G9A00018tyO1pFT300RY3XL3G9A03
     * GRANT_TYPE: authorization_code
     */

    private static final String APP_ID = "wxafcffece6679f01a";
    private static final String SECRET = "83b297e0e7412cf259b6e0346ed5d1f6";
    private static final String JS_CODE = "023G9A00018tyO1pFT300RY3XL3G9A03";
    private static final String GRANT_TYPE = "authorization_code";


    public static Map<String, Object> getOpenId(String code) throws IOException {

        Map<String, Object> resultMap = new HashMap<>();
        String loginUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=" + APP_ID + "&secret=" + SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(loginUrl);
        client = HttpClients.createDefault();
        response = client.execute(httpGet);
        HttpEntity entity = response.getEntity();

        String result = EntityUtils.toString(entity);
        JSONObject object = JSONObject.parseObject(result);
        String wxOpenId = object.getString("openid");
        String sessionKey = object.getString("session_key");
        resultMap.put("sessionKey",sessionKey);
        resultMap.put("openId",wxOpenId);
        if (response != null) {
            response.close();
        }
        if (client != null) {
            client.close();
        }
        return resultMap;
    }
}
