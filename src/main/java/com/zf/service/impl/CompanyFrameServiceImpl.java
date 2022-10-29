package com.zf.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.dto.AppKey;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.entity.User;
import com.zf.domain.vo.DingDingResult;
import com.zf.domain.vo.Info;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyFrameMapper;
import com.zf.service.CompanyFrameService;
import com.zf.service.CompanyService;
import com.zf.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author wenqin
 * @Date 2022/9/21 13:42
 */
@Service
@Slf4j
public class CompanyFrameServiceImpl extends ServiceImpl<CompanyFrameMapper, CompanyFrame> implements CompanyFrameService {

    @Resource
    private CompanyService companyService;

    @Override
    public ResponseVo<?> getCompanyFramework(String token) {
        LoginUser loginUser = UserUtils.getLoginUser();
        Long companyId = loginUser.getSysUser().getCompanyid();
        if (companyId == null)throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);

        LambdaQueryWrapper<CompanyFrame> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyFrame::getCompanyId,companyId);
        List<CompanyFrame> frameList = list(queryWrapper);
        if (Objects.isNull(frameList))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        List<CompanyFrame> list = frameList.stream()
                .filter(item -> item.getParentId() == -1)
                .map(m -> {
                    m.setChildren(getChildren(m, frameList));
                    return m;
                })
                .collect(Collectors.toList());
        return ResponseVo.okResult(list);
    }

    private List<CompanyFrame> getChildren(CompanyFrame root, List<CompanyFrame> frameList) {
        return frameList.stream()
                .filter(item -> Objects.equals(root.getId(), item.getParentId()))
                .map(m -> {
                    m.setChildren(getChildren(m, frameList));
                    return m;
                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponseVo<?> addCompanyRole(CompanyFrame companyFrame) {
        LoginUser loginUser = UserUtils.getLoginUser();
        Long companyId = loginUser.getSysUser().getCompanyid();
        if (companyService.getById(companyId) == null)
            throw new SystemException(AppHttpCodeEnum.COMPANY_NOF_FIND);


        companyFrame.setCompanyId(companyId);

        companyFrame.setCreateBy(Math.toIntExact(loginUser.getSysUser().getId()));
        companyFrame.setUpdateBy(Math.toIntExact(loginUser.getSysUser().getId()));

        boolean res = save(companyFrame);
        return res ? ResponseVo.okResult() : ResponseVo.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseVo<?> updateCompanyRole(CompanyFrame companyFrame) {
        LoginUser loginUser = UserUtils.getLoginUser();
        CompanyFrame frame = getById(companyFrame.getId());
        if (Objects.isNull(frame))
            throw new SystemException(AppHttpCodeEnum.DEPARTMENT_NOT_EXIST);

        LambdaQueryWrapper<CompanyFrame> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(CompanyFrame::getCompanyId,loginUser.getSysUser().getCompanyid())
                .eq(CompanyFrame::getRoleName,companyFrame.getRoleName())
                .eq(CompanyFrame::getParentId,frame.getParentId());

        if (!Objects.isNull(getOne(queryWrapper)))
            throw new SystemException(AppHttpCodeEnum.DEPARTMENT_EXIST);

        frame.setUpdateBy(Math.toIntExact(loginUser.getSysUser().getId()));
        frame.setCompanyId(loginUser.getSysUser().getCompanyid());
        frame.setRoleName(companyFrame.getRoleName());
        updateById(frame);
        return ResponseVo.okResult();
    }

    @Override
    @Transactional
    public ResponseVo<?> delCompanyFrameworkById(Long id) {
        if (Objects.isNull(id))
            throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
        LoginUser loginUser = UserUtils.getLoginUser();
        CompanyFrame temp = getById(id);
        if (Objects.isNull(temp))
            throw new SystemException(AppHttpCodeEnum.DEPARTMENT_NOT_EXIST);
        if (!Objects.equals(temp.getCompanyId(),loginUser.getSysUser().getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        LambdaQueryWrapper<CompanyFrame> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CompanyFrame::getParentId,id);
        remove(queryWrapper);
        removeById(id);
        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo dingDingImport(AppKey appKey, String rootName) {
        LoginUser loginUser = UserUtils.getLoginUser();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MediaType type=MediaType.parseMediaType("application/json;charset=UTF-8");
        headers.setContentType(type);

        HashMap<String, Object> map = new HashMap<>();
        map.put("appKey",appKey.getAppKey());
        map.put("appSecret",appKey.getAppSecret());
        HttpEntity<HashMap<String,Object>> body = new HttpEntity<>(map, headers);
        String getTokenUrl = "https://api.dingtalk.com/v1.0/oauth2/accessToken";
        Object postForObject = restTemplate.postForObject(getTokenUrl, body, Object.class);
        String accessToken = (String) ((LinkedHashMap) postForObject).get("accessToken");
        if (Objects.isNull(accessToken))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);

        String url = "https://oapi.dingtalk.com/topapi/v2/department/listsub?access_token=" + accessToken;
        map = new HashMap<>();

        map.put("dept_id", 1);
        HttpEntity<HashMap<String,Object>> request = new HttpEntity<>(map, headers);

        DingDingResult result = restTemplate.postForObject(url, request, DingDingResult.class);
        if (result == null || result.getErrcode() != 0)
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        List<Info> infoList = result.getResult();

        CompanyFrame companyFrame = new CompanyFrame();
        companyFrame.setParentId(-1L);
        companyFrame.setCompanyId(loginUser.getSysUser().getCompanyid());
        companyFrame.setRoleName(rootName);
        companyFrame.setCreateBy(Math.toIntExact(loginUser.getSysUser().getId()));
        companyFrame.setUpdateBy(Math.toIntExact(loginUser.getSysUser().getId()));
        save(companyFrame);

        for (Info info : infoList) {
            map.put("dept_id", info.getDept_id());
            DingDingResult res = restTemplate.postForObject(url, request, DingDingResult.class);

            CompanyFrame tempFrame = new CompanyFrame();
            tempFrame.setId(info.getDept_id());
            tempFrame.setParentId(companyFrame.getId());
            tempFrame.setCompanyId(loginUser.getSysUser().getCompanyid());
            tempFrame.setRoleName(info.getName());
            tempFrame.setCreateBy(Math.toIntExact(loginUser.getSysUser().getId()));
            tempFrame.setUpdateBy(Math.toIntExact(loginUser.getSysUser().getId()));
            save(tempFrame);

            if (res.getErrcode() == 0){
                info.setChildren(res.getResult());
                for (Info child : info.getChildren()) {
                    tempFrame.setId(child.getDept_id());
                    tempFrame.setParentId(child.getParent_id());
                    tempFrame.setRoleName(child.getName());
                    save(tempFrame);
                }
            }
        }

        Company company = companyService.getById(loginUser.getSysUser().getCompanyid());
        company.setAppKey(appKey.getAppKey());
        company.setAppSecret(appKey.getAppSecret());
        companyService.updateById(company);


        return ResponseVo.okResult();
    }


}
