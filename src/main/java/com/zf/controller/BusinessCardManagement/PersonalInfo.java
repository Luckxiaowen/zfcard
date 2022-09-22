package com.zf.controller.BusinessCardManagement;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.SysUserMapper;
import com.zf.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/9/19 15:35
 */

@RestController
@Api(value = "个人信息", tags = "名片管理")
@RequestMapping("/personal-info")
public class PersonalInfo {
  @Autowired
  private SysUserMapper sysUserMapper;

  @ApiOperation("进入个人信息")
  @GetMapping("/into-PersonalInfo")
  public ResponseVo intoPersonalInfo(@RequestHeader("token") String token) throws Exception {

    Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());

    SysUser sysUser = sysUserMapper.selectById(id);
    String avatar = sysUser.getAvatar();
    String info = sysUser.getInfo();

    HashMap<String, String> map = new HashMap<>();
    map.put("photo",avatar);
    map.put("info",info);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),map);
  }

  @ApiOperation("职业照上传")
  @PutMapping("/updatePhoto")
  public ResponseVo updatePhoto (@RequestHeader("token") String token,@RequestPart("photo") MultipartFile photo) throws Exception {

    String photoPath = null;

    if(!photo.isEmpty()){
      //获取上传的文件的文件名
      String fileName = photo.getOriginalFilename();
      //获取上传的文件的后缀名
      String suffixName = fileName.substring(fileName.lastIndexOf("."));
      //将UUID作为文件名
      String uuid = UUID.randomUUID().toString().replaceAll("-", "");
      //将uuid和后缀名拼接后的结果作为最终的文件名
      fileName = uuid + suffixName;
      //通过ResourceUtils获取服务器中photo目录的路径
      String path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
      String pathName = path +  File.separator + "resources" + File.separator +"userImg";
      File file = new File(pathName);
      //判断photoPath所对应路径是否存在
      if (!file.exists()) {
        //若不存在，则创建目录
        file.mkdir();
      }

      photoPath = "userImg/" + fileName;

      String finalPath = pathName + File.separator + fileName;
      //上传文件
      try {
        photo.transferTo(new File(finalPath));
      } catch (IOException exception) {
        exception.printStackTrace();
      }
    }

    Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());

    LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.eq(SysUser::getId,id);
    updateWrapper.set(SysUser::getAvatar,photoPath);
    sysUserMapper.update(null,updateWrapper);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg());
  }

  @ApiOperation("个人简介修改")
  @PostMapping("/revise-info")
  public ResponseVo reviseInfo(@RequestHeader("token") String token,@RequestParam("info") String info) throws Exception {

    Integer id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());

    LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.eq(SysUser::getId,id);
    updateWrapper.set(SysUser::getInfo,info);
    sysUserMapper.update(null,updateWrapper);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg());
  }
}
