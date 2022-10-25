package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.ModuleConfig;
import com.zf.domain.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/24 14:21
 */
public interface ModuleConfigService extends IService<ModuleConfig> {

  ResponseVo setModuleSwitch(String token,int switchFlag,String category) throws Exception;

  ResponseVo insertIntroduceModule(String token,String moduleName, HttpServletRequest request, MultipartFile file,String category);

}
