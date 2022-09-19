package com.zf.service.impl;



import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.domain.vo.SearchUserVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.mapper.SysUserMapper;
import com.zf.service.SysUserService;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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
        queryWrapper.eq(SysUser::getDelFlag,0);
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), sysUserMapper.selectList(queryWrapper));
    }

    @Override
    public ResponseVo selectByConditions(String conditions) throws JsonProcessingException {
        if (conditions.length()==0){
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(),sysUserMapper.selectList(null));
        }else{
            ObjectMapper objectMapper=new ObjectMapper();
            SearchUserVo searchUserVo = objectMapper.readValue(conditions, SearchUserVo.class);
           List<SysUser>sysUserList= sysUserMapper.selectByConditions(searchUserVo);
            System.out.println("sysUserList = " + sysUserList);
        }
        return null;
    }

}
