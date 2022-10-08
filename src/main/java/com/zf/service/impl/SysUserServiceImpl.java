package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.dto.AccountDto;
import com.zf.domain.dto.StaffDto;
import com.zf.domain.entity.CompanyFrame;
import com.zf.domain.entity.SysRole;
import com.zf.domain.entity.SysUser;
import com.zf.domain.entity.SysUserRole;
import com.zf.domain.vo.LoginUser;
import com.zf.domain.vo.ResponseVo;
import com.zf.domain.vo.SysUserVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.*;
import com.zf.service.CompanyFrameService;
import com.zf.service.SysUserService;
import com.zf.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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


    @Resource
    private SysUserRoleServiceImpl userRoleService;
    @Resource
    private SysRoleServiceImpl roleService;

    @Override
    public ResponseVo add(SysUser sysUser, String updateId) {
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
                                                    sysUser.setCompanyid(sysUserMapper.selectById(updateId).getCompanyid());
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
    public ResponseVo delete(long userid, String updateId) {
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
                if (sysUserMapper.updateById(sysUser) > 0) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
                } else {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "删除失败");
                }

            }
        }
    }

    @Override
    public ResponseVo modify(SysUser sysUser, String updateId) {
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
    public ResponseVo selectAll(String userId) {
        if ("".equals(userId) || userId == null) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "查询失败：用户id为空");
        } else {
            SysUser sysUser = sysUserMapper.selectById(userId);
            List<SysUserVo> userVoList = sysUserMapper.selectByCompanyId(sysUser.getCompanyid());
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), AppHttpCodeEnum.SUCCESS.getMsg(), userVoList);
        }
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
        map.put("photo", avatar);
        map.put("info", info);

        return ResponseVo.okResult(map);
    }

    @Override
    public ResponseVo updateUserPhotonAndInfo(String token, MultipartFile photo, String info, HttpServletRequest request) {
        Integer id = null;
        try {
            id = Integer.valueOf(JwtUtil.parseJWT(token).getSubject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Objects.isNull(photo)) {
            SysUser sysUser = new SysUser();
            sysUser.setId(Long.valueOf(id));
            sysUser.setInfo(info);
            sysUser.setAvatar(sysUser.getAvatar());
            sysUserMapper.updateById(sysUser);
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "用户个人简介修改成功");
        } else {
            HashMap map = UpLoadUtil.updateUserWxCode(request, photo);
            String url = (String) map.get("url");
            Integer msgCode = (Integer) map.get(("msg"));
            SysUser sysUser = new SysUser();
            sysUser.setId(Long.valueOf(id));
            if (msgCode == 201) {
                sysUser.setAvatar(sysUser.getAvatar());
            } else {
                sysUser.setAvatar(url);
            }
            sysUser.setInfo(info);
            sysUserMapper.updateById(sysUser);
            return ResponseVo.okResult(map);
        }
    }


    @Override
    public ResponseVo updateUserOpenId(String userId, String openId) {

        long uId = Long.parseLong(userId);
        SysUser selectUser = sysUserMapper.selectById(uId);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(selectUser.getOpenedId())) {
            LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUser::getOpenedId, openId);
            SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
            if (Objects.isNull(sysUser)) {
                selectUser.setOpenedId(openId);
                int i = sysUserMapper.updateById(selectUser);
                if (i > 0) {
                    map.put("userId", selectUser.getId());
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "绑定成功!", map);
                } else {

                    return ResponseVo.okResult("绑定失败");
                }
            } else {
                map.put("userId", selectUser.getId());
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "该微信号已绑定员工账号，请直接登录", map.put("userId", selectUser.getId()));
            }
        } else {
            map.put("userId", selectUser.getId());
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "当前员已绑定微信号", map.put("userId", selectUser.getId()));
        }

    }


    @Override
    public ResponseVo updateUserWxCode(String token, HttpServletRequest request, MultipartFile file) {
        HashMap map = new HashMap();
        String fileName = file.getOriginalFilename();
        String suffixName = fileName.substring(0, fileName.lastIndexOf("."));
        if (!file.isEmpty()) {
            UUID id = UUID.randomUUID();//生成文件名
            try {
                //参数就是图片保存在服务器的本地地址
                file.transferTo(new File("/www/myproject/image/" + id + ".png"));
                map.put("url", request.getServerName() + ":" + request.getServerPort() + "/images/" + id + ".png");
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "上传成功", map);
            } catch (IOException e) {
                e.printStackTrace();
            }
            map.put("url", "上传失败");
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "上传失败", map);
        }
        map.put("url", "上传失败");
        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "上传失败", map);
    }

    //TODO 修改 gou sir 代码 优化个人简介接口
    @Override
    public ResponseVo selectUserInfoByWu(String id) {
        if (id == null || "".equals(id)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：输入为空");
        } else {
            //TODO 游客进入
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：不存在此员工");
            } else {
                HashMap<String, String> infoMap = new HashMap<>();
                infoMap.put("info", sysUserMapper.selectById(userId).getInfo());
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功", infoMap);
            }
        }
    }

    //TODO 修改 gou sir 代码 修改个人职业照接口
    @Override
    public ResponseVo selectUserProPhotoByWu(String id) {
        if (id == null || "".equals(id)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：输入为空");
        } else {
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：不存在此员工");
            } else {
                HashMap<String, Object> photoMap = new HashMap<>();
                photoMap.put("photo", sysUserMapper.selectById(userId).getAvatar());
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "查询成功", photoMap);
            }
        }
    }

    /**
     * 新增员工
     *
     * @param staff
     * @return
     */
    @Resource
    private CompanyFrameService companyFrameService;


    @Override
    public ResponseVo addStaff(StaffDto staff) {
        LoginUser loginUser = UserUtils.getLoginUser();
        Integer depId = staff.getDepId();
        CompanyFrame frame = companyFrameService.getById(depId);
        if (Objects.isNull(frame) || !Objects.equals(loginUser.getSysUser().getCompanyid(), frame.getCompanyId()))
            throw new SystemException(AppHttpCodeEnum.DEPARTMENT_NOT_EXIST);
        String password = staff.getPhonenumber().substring(staff.getPhonenumber().length() - 6);
        String encodePassword = passwordEncoder.encode(password);
        SysUser user = BeanCopyUtils.copyBean(staff, SysUser.class);
        user.setCreateBy(loginUser.getSysUser().getId());
        user.setPassword(encodePassword);
        user.setSex(2);
        user.setUserType(1);
        user.setCompanyid(loginUser.getSysUser().getCompanyid());
        user.setNickName(staff.getUsername());
        save(user);
        return ResponseVo.okResult();

    }

    @Override
    public ResponseVo updateStaff(StaffDto staff) {
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser updateUser = getById(staff.getId());
        if (Objects.isNull(updateUser) || !Objects.equals(updateUser.getCompanyid(), loginUser.getSysUser().getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        CompanyFrame frame = companyFrameService.getById(staff.getDepId());
        if (Objects.isNull(frame) || !Objects.equals(loginUser.getSysUser().getCompanyid(), frame.getCompanyId()))
            throw new SystemException(AppHttpCodeEnum.DEPARTMENT_NOT_EXIST);
        updateUser.setUsername(staff.getUsername());
        updateUser.setPhonenumber(staff.getPhonenumber());
        updateUser.setEmail(staff.getEmail());
        updateUser.setTelWeixin(staff.getTelWeixin());
        updateUser.setDepId(staff.getDepId());
        updateUser.setStation(staff.getStation());
        updateUser.setWeixinCode(staff.getWeixinCode());

        boolean res = updateById(updateUser);
        return res ? ResponseVo.okResult() : ResponseVo.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseVo delStaffById(Integer id) {
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser delStaff = getById(id);
        if (Objects.isNull(delStaff) || !Objects.equals(loginUser.getSysUser().getCompanyid(), delStaff.getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        boolean res = removeById(id);
        return res ? ResponseVo.okResult() : ResponseVo.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseVo SelectPage(String userId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getId, userId)
                .eq(SysUser::getDelFlag, 0);
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getCompanyid, sysUser.getCompanyid());
        long count = sysUserMapper.selectList(queryWrapper).stream().count();
        pageNum = (pageNum - 1) * pageSize;
        List<SysUserVo> userVoList = sysUserMapper.selectMyPage(sysUser.getCompanyid(), pageNum, pageSize);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setTotal((int) count);
        pageUtils.setData(userVoList);
        return ResponseVo.okResult(pageUtils);
    }

    @Override
    public ResponseVo getStaffById(Integer id) {
        if (id == null)
            throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser staff = getById(id);
        if (Objects.isNull(staff) || !Objects.equals(loginUser.getSysUser().getCompanyid(), staff.getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        StaffDto staffDto = BeanCopyUtils.copyBean(staff, StaffDto.class);
        staffDto.setId(Math.toIntExact(staff.getId()));
        return ResponseVo.okResult(staffDto);
    }

    @Override
    public ResponseVo getAllAccount() {
        Long companyid = UserUtils.getLoginUser().getSysUser().getCompanyid();
        List<SysUser> list = sysUserMapper.selectAllAccount(companyid);
        return ResponseVo.okResult(list);
    }

    @Override
    public ResponseVo delAccountById(Integer id) {
        if (id == null)
            throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser user = getById(id);
        if (Objects.isNull(user) || !Objects.equals(user.getCompanyid(), loginUser.getSysUser().getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        removeById(id);
        userRoleService.remove(new LambdaUpdateWrapper<SysUserRole>().eq(SysUserRole::getUserId, id));
        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo resetAccountPassword(Integer id) {
        if (id == null)
            throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser user = getById(id);
        if (Objects.isNull(user) || !Objects.equals(user.getCompanyid(), loginUser.getSysUser().getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        String phonenumber = user.getPhonenumber();
        String password = phonenumber.substring(phonenumber.length() - 6);
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
        updateById(user);
        return ResponseVo.okResult();
    }


    @Override
    @Transactional
    public ResponseVo updateAccount(AccountDto accountDto) {
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser user = getById(accountDto.getId());
        if (Objects.isNull(user) || !Objects.equals(user.getCompanyid(), loginUser.getSysUser().getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        user.setPhonenumber(accountDto.getTelNumber());
        user.setUsername(accountDto.getUsername());
        updateById(user);

        SysRole role = roleService.getById(accountDto.getRoleId());
        if (Objects.isNull(role) || !Objects.equals(role.getCompanyId(), loginUser.getSysUser().getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.ROLE_NOT_EXIST);
        SysUserRole userRole = userRoleService.getOne(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, accountDto.getId()));
        userRole.setRoleId(Long.valueOf(accountDto.getRoleId()));
        userRoleService.update(userRole, new LambdaUpdateWrapper<SysUserRole>().eq(SysUserRole::getUserId, user.getId()));
        return ResponseVo.okResult();
    }

    @Override
    @Transactional
    public ResponseVo addAccount(AccountDto accountDto) {
        LoginUser loginUser = UserUtils.getLoginUser();
        String phonenumber = accountDto.getTelNumber();
        String password = phonenumber.substring(phonenumber.length() - 6);
        String encodePassword = passwordEncoder.encode(password);
        SysUser user = new SysUser();
        user.setUserType(0);
        user.setCompanyid(loginUser.getSysUser().getCompanyid());
        user.setCreateBy(loginUser.getSysUser().getId());
        user.setPhonenumber(accountDto.getTelNumber());
        user.setUsername(accountDto.getUsername());
        user.setNickName(accountDto.getUsername());
        user.setPassword(encodePassword);
        save(user);
        SysUserRole userRole = new SysUserRole(user.getId(), Long.valueOf(accountDto.getRoleId()));
        userRoleService.save(userRole);
        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo getAccountById(Integer id) {
        if (id == null)
            throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
        LoginUser loginUser = UserUtils.getLoginUser();
        SysUser user = getById(id);
        if (Objects.isNull(user) || !Objects.equals(user.getCompanyid(), loginUser.getSysUser().getCompanyid()))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        Integer roleId = Math.toIntExact(userRoleService.getOne(new LambdaUpdateWrapper<SysUserRole>().eq(SysUserRole::getUserId, id)).getRoleId());
        AccountDto account = new AccountDto();
        account.setId(Math.toIntExact(user.getId()));
        account.setUsername(user.getUsername());
        account.setTelNumber(user.getPhonenumber());
        account.setRoleId(roleId);
        return ResponseVo.okResult(account);
    }

    @Override
    public ResponseVo addUserList(String token, List<StaffDto> userList) {
        if (userList.size() == 0) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "列表为空 ");
        } else {
            for (StaffDto staffDto : userList) {
                this.addStaff(staffDto);
            }
            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "批量添加成功");
        }
    }

    public Integer getInteger(String userId) {
        int id;
        if (Validator.isNumeric(userId)) {
            id = Integer.parseInt(userId);
        } else {
            //TODO 员工token获取
            try {
                String subject = JwtUtil.parseJWT(userId).getSubject();
                id = Integer.parseInt(subject);
            } catch (Exception e) {
                throw new SystemException(AppHttpCodeEnum.PARAMETER_ERROR);
            }
        }
        return id;
    }
}
