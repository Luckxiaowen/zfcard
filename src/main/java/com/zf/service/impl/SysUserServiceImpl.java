package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.Company;
import com.zf.domain.entity.ExposureTotal;
import com.zf.domain.entity.SysRole;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.PersonalCardVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.GlobalExceptionHandler;
import com.zf.exception.SystemException;
import com.zf.mapper.*;
import com.zf.service.PersonalCardService;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Amireux
 * @description 针对表【sys_user(用户表)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:17
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseVo add(SysUser sysUser,String updateId) {
        if ("".equals(sysUser.getUsername()) || sysUser.getUsername() == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "员工姓名不能为空");
        } else {
            if (sysUser.getUsername().length() < 1) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "姓名长度不能小于1");
            } else {
                if ("".equals(sysUser.getNickName()) || sysUser.getNickName() == null) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "请输入昵称");
                } else {
                    if ("".equals(sysUser.getPassword()) || sysUser.getPassword() == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "密码不能为空");
                    } else {
                        if (sysUser.getPassword().length() < 6) {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "密码不能小于6位数");
                        } else {
                            if ("".equals(sysUser.getEmail()) || sysUser.getEmail() == null) {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "邮箱不能为空");
                            } else {
                                if (sysUserMapper.selectList(null).stream().filter(sysUser1 -> sysUser1.getEmail().equals(sysUser.getEmail())).count() > 0) {
                                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前用户邮箱已存在请检查");
                                } else {
                                    if (Validator.isEmail(sysUser.getEmail())) {
                                        if ("".equals(sysUser.getPhonenumber()) || sysUser.getPhonenumber() == null) {
                                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "电话号码不能为空");
                                        } else {
                                            if (sysUserMapper.selectList(null).stream().anyMatch(sysUser1 -> sysUser1.getPhonenumber().equals(sysUser.getPhonenumber()))) {
                                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前用户电话已存在请检查");
                                            } else {
                                                //TODO 数据库操作
                                                if (Validator.isMobile(sysUser.getPhonenumber())) {
                                                    sysUser.setCompanyid( sysUserMapper.selectById(updateId).getCompanyid());
                                                    sysUser.setCreateTime(new Date());
                                                    sysUser.setUpdateTime(new Date());
                                                    sysUser.setCreateBy(Long.parseLong(updateId));
                                                    sysUser.setUpdateBy(Long.parseLong(updateId));
                                                    sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
                                                    if (sysUserMapper.insert(sysUser) > 0) {

                                                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "添加成功");
                                                    } else {
                                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "添加失败，请重试");
                                                    }
                                                } else {
                                                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "电话号码错误请检查");
                                                }
                                            }
                                        }
                                    } else {
                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "邮箱格式错误请检查");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo delete(long userid,String updateId) {
        SysUser sysUser = sysUserMapper.selectById(userid);
        if (Objects.isNull(sysUser)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "您的访问有误");
        } else {
            if (sysUser.getDelFlag() == 1) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前员工已删除，请刷新页面");
            } else {
                sysUser.setDelFlag(1);
                sysUser.setUpdateBy(Long.parseLong(updateId));
                Wrapper<SysUser> wrapper = new UpdateWrapper<>();
                if (sysUserMapper.updateById(sysUser)>0) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
                } else {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "删除失败");
                }

            }
        }
    }

    @Override
    public ResponseVo modify(SysUser sysUser,String updateId) {
        if ("".equals(sysUser.getUsername()) || sysUser.getUsername() == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "员工姓名不能为空");
        } else {
            if (sysUser.getUsername().length() < 1) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "姓名长度不能小于1");
            } else {
                if ("".equals(sysUser.getNickName()) || sysUser.getNickName() == null) {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "请输入昵称");
                } else {
                    if ("".equals(sysUser.getPassword()) || sysUser.getPassword() == null) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "密码不能为空");
                    } else {
                        if (sysUser.getPassword().length() < 6) {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "密码不能小于6位数");
                        } else {
                            if ("".equals(sysUser.getEmail()) || sysUser.getEmail() == null) {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "邮箱不能为空");
                            } else {
                                if (sysUserMapper.selectList(null).stream().filter(sysUser1 -> sysUser1.getEmail().equals(sysUser.getEmail())).count() > 0) {
                                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前用户邮箱已存在请检查");
                                } else {
                                    if (Validator.isEmail(sysUser.getEmail())) {
                                        if ("".equals(sysUser.getPhonenumber()) || sysUser.getPhonenumber() == null) {
                                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "电话号码不能为空");
                                        } else {
                                            if (sysUserMapper.selectList(null).stream().anyMatch(sysUser1 -> sysUser1.getPhonenumber().equals(sysUser.getPhonenumber()))) {
                                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "当前用户电话已存在请检查");
                                            } else {
                                                //TODO 数据库操作
                                                if (Validator.isMobile(sysUser.getPhonenumber())) {
                                                    sysUser.setUpdateTime(new Date());
                                                    sysUser.setUpdateBy(Long.parseLong(updateId));
                                                    sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
                                                    if (sysUserMapper.updateById(sysUser) > 0) {
                                                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "修改成功");
                                                    } else {
                                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改成功，请重试");
                                                    }
                                                } else {
                                                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "电话号码错误请检查");
                                                }
                                            }
                                        }
                                    } else {
                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "邮箱格式错误请检查");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo selectAll() {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getDelFlag,"0");
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), sysUserMapper.selectList(queryWrapper));
    }

  @Override
  public ResponseVo selectUserInfo(String token) {

    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    SysUser sysUser = sysUserMapper.selectById(id);
    String avatar = sysUser.getAvatar();
    String info = sysUser.getInfo();

    HashMap<String, String> map = new HashMap<>();
    map.put("photo",avatar);
    map.put("info",info);

    return ResponseVo.okResult(map);
  }

  @Override
  public ResponseVo updateUserPhoton(String token, MultipartFile photo) {

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
      String path = null;
      try {
        path = new File(ResourceUtils.getURL("classpath:").getPath()).getAbsolutePath();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
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

    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.eq(SysUser::getId,id);
    updateWrapper.set(SysUser::getAvatar,photoPath);
    sysUserMapper.update(null,updateWrapper);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg());
  }

  @Override
  public ResponseVo updateInfo(String token, String info) {

    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
    updateWrapper.eq(SysUser::getId,id);
    updateWrapper.set(SysUser::getInfo,info);
    sysUserMapper.update(null,updateWrapper);

    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),AppHttpCodeEnum.SUCCESS.getMsg());
  }

    @Override
    public ResponseVo selectByConditions(String conditions) {

        return null;
    }

    @Override
    public ResponseVo updateUserOpenId(String userId, String openId) {


        long uId = Long.parseLong(userId);
        SysUser selectUser = sysUserMapper.selectById(uId);
        if (StringUtils.isEmpty(selectUser.getOpenedId())){
            LambdaQueryWrapper<SysUser>queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getOpenedId,openId);
            SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
            if (Objects.isNull(sysUser)){
                selectUser.setOpenedId(openId);
                int i = sysUserMapper.updateById(selectUser);
                if (i>0){
                    return ResponseVo.okResult("绑定成功");
                }else{
                    return ResponseVo.okResult("绑定失败");
                }
            }else{
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"该微信号已绑定员工账号，请直接登录");
            }
        }else{
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"当前员已绑定微信号");
        }

    }
}
