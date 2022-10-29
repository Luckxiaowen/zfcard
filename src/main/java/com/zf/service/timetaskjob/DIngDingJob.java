package com.zf.service.timetaskjob;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.vo.DingDingResult;
import com.zf.domain.vo.Info;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.service.CompanyFrameService;
import com.zf.service.CompanyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author wenqin
 * @Date 2022/10/27 19:00
 */
@Service
public class DIngDingJob {
    @Resource
    private CompanyService companyService;
    @Resource
    private CompanyFrameService companyFrameService;

    @Scheduled(cron = "0 0 1 ? * 5")
    public void DingDingImportDb(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type=MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);
        String getTokenUrl = "https://api.dingtalk.com/v1.0/oauth2/accessToken";

        List<Company> list = companyService.list();
        List<Company> companyList = list.stream()
                .filter(company -> company.getAppKey() != null && company.getAppSecret() != null)
                .collect(Collectors.toList());
        HashMap<String, Object> map = new HashMap<>();
        for (Company company : companyList) {
            map.put("appKey",company.getAppKey());
            map.put("appSecret",company.getAppSecret());
            HttpEntity<HashMap<String,Object>> body = new HttpEntity<>(map, headers);
            Object postForObject = restTemplate.postForObject(getTokenUrl, body, Object.class);
            String accessToken = (String) ((LinkedHashMap) postForObject).get("accessToken");
            if (accessToken == null)
                continue;
            String url = "https://oapi.dingtalk.com/topapi/v2/department/listsub?access_token=" + accessToken;
            map = new HashMap<>();
            map.put("dept_id", 1);
            HttpEntity<HashMap<String,Object>> request = new HttpEntity<>(map, headers);
            DingDingResult result = restTemplate.postForObject(url, request, DingDingResult.class);
            if (result == null || result.getErrcode() != 0)
                continue;
            List<Info> infoList = result.getResult();
            for (Info info : infoList) {
                CompanyFrame companyFrame = new CompanyFrame();
                companyFrame.setRoleName(info.getName());
                companyFrame.setRoleName(info.getName());
                companyFrame.setCompanyId(Long.valueOf(company.getId()));
                boolean byId = companyFrameService.updateById(companyFrame);
                if (!byId){
                    companyFrameService.save(companyFrame);
                }
                map.put("dept_id", info.getDept_id());
                DingDingResult res = restTemplate.postForObject(url, request, DingDingResult.class);
                if (res.getErrcode() == 0){
                    info.setChildren(res.getResult());
                    for (Info child : info.getChildren()) {
                        CompanyFrame tempFrame = new CompanyFrame();
                        tempFrame.setId(child.getDept_id());
                        tempFrame.setParentId(child.getParent_id());
                        tempFrame.setRoleName(child.getName());
                        boolean byId1 = companyFrameService.updateById(tempFrame);
                        if (!byId1){
                            companyFrameService.save(tempFrame);
                        }
                    }
                }
            }
        }
    }
}
