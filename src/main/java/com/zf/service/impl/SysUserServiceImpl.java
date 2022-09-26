package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.*;
import com.zf.service.SysUserService;
import com.zf.utils.JwtUtil;
import com.zf.utils.UpLoadUtil;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

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
  public ResponseVo updateUserPhotonAndInfo(String token, MultipartFile photo, String info,HttpServletRequest request) {


    Integer id = null;
    try {
      id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
    } catch (Exception e) {
      e.printStackTrace();
    }

    HashMap map = UpLoadUtil.updateUserWxCode(token, request, photo);

    String url = (String) map.get("url");

    SysUser sysUser = new SysUser();

    sysUser.setId(Long.valueOf(id));
    sysUser.setAvatar(url);
    sysUser.setInfo(info);

    sysUserMapper.updateById(sysUser);

    return ResponseVo.okResult(map);
  }


    @Override
    public ResponseVo updateUserOpenId(String userId, String openId) {


        long uId = Long.parseLong(userId);
        SysUser selectUser = sysUserMapper.selectById(uId);
        Map<String,Object>map=new HashMap<>();
        if (StringUtils.isEmpty(selectUser.getOpenedId())){
            LambdaQueryWrapper<SysUser>queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getOpenedId,openId);
            SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
            if (Objects.isNull(sysUser)){
                selectUser.setOpenedId(openId);
                int i = sysUserMapper.updateById(selectUser);
                if (i>0){
                    map.put("userId",selectUser.getId());
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "绑定成功!",map);
                }else{

                    return ResponseVo.okResult("绑定失败");
                }
            }else{
                map.put("userId",selectUser.getId());
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"该微信号已绑定员工账号，请直接登录",  map.put("userId",selectUser.getId()));
            }
        }else{
            map.put("userId",selectUser.getId());
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(),"当前员已绑定微信号",  map.put("userId",selectUser.getId()));
        }

    }


    @Override
    public ResponseVo updateUserWxCode(String token,HttpServletRequest request, MultipartFile file) {
        HashMap map=new HashMap();
        if(!file.isEmpty()){
            UUID id=UUID.randomUUID();//生成文件名
            try {
                //参数就是图片保存在服务器的本地地址
                file.transferTo(new File("/www/myproject/image/"+id+".png"));
                map.put("url",request.getServerName()+":"+request.getServerPort()+"/images/"+id+".png");
              return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "上传成功",map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("url","上传失败");
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "上传失败",map);
        }
        map.put("url","上传失败");
        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "上传失败",map);
    }
}
