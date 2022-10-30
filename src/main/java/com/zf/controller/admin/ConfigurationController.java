package com.zf.controller.admin;

import com.zf.domain.vo.ResponseVo;
import com.zf.service.ModuleConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/24 10:44
 */

@RestController
@Api(value = "PC个性化简介,个性化内容", tags = "PC个性化简介，个性化内容")
@RequestMapping("/config")
public class ConfigurationController {

  @Autowired
  private ModuleConfigService moduleConfigService;

//  @ApiOperation(value = "个性化简介模块展示开关(1开，0关)")
//  @PostMapping("/persona_module_show")
//  public ResponseVo personaModuleShow(@RequestHeader("token") String token,@RequestParam("switchFlag") int switchFlag,@RequestParam("category") String category) throws Exception {
//    return moduleConfigService.setModuleSwitch(token,switchFlag,category);
//  }

  @ApiOperation(value = "个性化简介录入")
  @PostMapping("/persona_module_message")
  public ResponseVo PersonaConfig(@RequestHeader("token") String token,@RequestParam("moduleName") String moduleName,
                                  HttpServletRequest request, @RequestPart(value = "file",required = false) MultipartFile file,@RequestParam("category" ) String category){
    return moduleConfigService.insertIntroduceModule(token,moduleName,request,file,category);
  }


  @ApiOperation(value ="简介与内容展示开关(1开，0关)通过传递的字符串判断")
  @PostMapping("/content_module_show")
  public ResponseVo contentModuleShow(@RequestHeader("token") String token,@RequestParam("switchFlag") int switchFlag,@RequestParam("category") String category) throws Exception {
    return moduleConfigService.isSwitch(token,switchFlag,category);
//    return moduleConfigService.setModuleSwitch(token,switchFlag,category);
  }

  @ApiOperation(value = "个性化内容录入")
  @PostMapping("/content_module_message")
  public ResponseVo contentConfig(@RequestHeader("token") String token,@RequestParam("moduleName") String moduleName, HttpServletRequest request, @RequestPart(value = "file",required = false) MultipartFile file,
                                  @RequestParam("category") String category){
    return moduleConfigService.insertIntroduceModule(token,moduleName,request,file,category);
  }

  @ApiOperation(value = "个性化简介录入数据回显")
  @GetMapping("/persona_module_echo")
  public ResponseVo personaEcho(@RequestHeader("token") String token,@RequestParam("category") String category){
    return moduleConfigService.PersonaEcho(token,category);
  }


}
