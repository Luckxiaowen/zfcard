package com.zf.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.dto.CompanyDto;
import com.zf.domain.entity.*;
import com.zf.domain.vo.NewCompanyVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.CompanyMapper;
import com.zf.service.CompanyService;
import com.zf.utils.BeanCopyUtils;
import com.zf.utils.UserUtils;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Amireux
 * @description 针对表【company(公司表)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:16
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private SysUserServiceImpl userService;
    @Resource
    private SysUserRoleServiceImpl sysUserRoleService;
    @Resource
    private SysRoleMenuServiceImpl roleMenuService;
    @Resource
    private SysRoleServiceImpl roleService;

    /**
     *
     * @param companyDto
     * @return
     */
    @Override
    @Transactional
    public ResponseVo insert(CompanyDto companyDto) {
        SysUser loginUser = UserUtils.getLoginUser().getSysUser();
        Company company = BeanCopyUtils.copyBean(companyDto, Company.class);
        company.setCreateBy(loginUser.getId());
        company.setStatus(0);
        save(company);
        SysUser user = new SysUser();
        user.setPassword(passwordEncoder.
                encode(companyDto.getAdminTel().substring(companyDto.getAdminTel().length() - 6)));
        user.setUsername(companyDto.getAdminName());
        user.setNickName(companyDto.getAdminName());
        user.setUserType(0);
        user.setPhonenumber(companyDto.getAdminTel());
        userService.save(user);
        SysRole role = new SysRole();
        role.setCompanyId(Long.valueOf(company.getId()));
        role.setName(companyDto.getAdminName());
        role.setCreateBy(loginUser.getId());
        roleService.save(role);
        sysUserRoleService.save(new SysUserRole(user.getId(),role.getId()));
        for (Integer menuId : companyDto.getCompanyAuthority()) {
            roleMenuService.save(new SysRoleMenu(role.getId(),Long.valueOf(menuId)));
        }
        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo delete(Long companyid,String updateId) {
        Company company = companyMapper.selectById(companyid);
        if (Objects.isNull(company)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "您的访问有误");
        } else {
            if (company.getDelFlag() == 1) {
                throw new SystemException(AppHttpCodeEnum.COMPANY_NOF_FIND);
            } else {
                boolean delFlag = removeById(companyid);
                if (delFlag==true) {
                    return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除成功");
                } else {
                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "删除失败");
                }
            }
        }
    }

    @Override
    @Transactional
    public ResponseVo modify(CompanyDto companyDto) {
        SysUser loginUser = UserUtils.getLoginUser().getSysUser();
        Company company = companyMapper.selectById(loginUser.getCompanyid());
        SysUser user = userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, company.getAdminTel()));
        if (Objects.isNull(company) || Objects.isNull(user))
            throw new SystemException(AppHttpCodeEnum.COMPANY_NOF_FIND);
        company.setCompanyLogo(companyDto.getCompanyLogo());
        company.setCompanyName(companyDto.getCompanyName());
        company.setCompanyAbbreviation(companyDto.getCompanyAbbreviation());
        company.setExpirationTime(companyDto.getExpirationTime());
        company.setAdminName(companyDto.getAdminName());
        company.setAdminTel(companyDto.getAdminTel());
        updateById(company);
        user.setPhonenumber(companyDto.getAdminTel());
        userService.updateById(user);
        Long roleId = sysUserRoleService.getById(user.getId()).getRoleId();
        roleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId,roleId));
        for (Integer menuId : companyDto.getCompanyAuthority()) {
            roleMenuService.save(new SysRoleMenu(roleId,Long.valueOf(menuId)));
        }
        return ResponseVo.okResult();
    }

    @Override
    public ResponseVo searchCompany(String conditions,String status) {
        Object strObject=new Object();
        Object telObject=new Object();
        Object numObject=new Object();
        List<Company>companyList= new ArrayList<>();
        if (Validator.isNumeric(conditions)){
            if (Validator.isMobile(conditions)){
                telObject=conditions;
                int num=1;
                if ("".equals(status)||status==null){
                    status=null;
                }
                companyList= companyMapper.selectByConditions(telObject,status,num);
            }else {
                int num=2;
                numObject=conditions;
                if ("".equals(status)||status==null){
                    status=null;
                }
                companyList= companyMapper.selectByConditions(numObject,status,num);
            }
        }else {
            int num=3;
            strObject=conditions;
            if ("".equals(status)||status==null){
                status=null;
            }
            companyList= companyMapper.selectByConditions(strObject,status,num);
        }
        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "公司列表查询成功",companyList);
    }

    @Override
    public ResponseVo selectOneCompany(Integer companyId) {
        Company company = companyMapper.selectById(companyId);
        SysUser companyAdmin =
                userService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhonenumber, company.getAdminTel()));
        if (Objects.isNull(companyAdmin))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        SysUserRole userRole = sysUserRoleService.getById(companyAdmin.getId());
        if (Objects.isNull(userRole))
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        List<SysRoleMenu> list =
                roleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, userRole.getRoleId()));
        List<Integer> roleIdList = list.stream().map(s -> Integer.parseInt(String.valueOf(s.getMenuId()))).collect(Collectors.toList());
        CompanyDto companyDto = BeanCopyUtils.copyBean(company, CompanyDto.class);
        companyDto.setCompanyAuthority(roleIdList);
        return ResponseVo.okResult(companyDto);
    }

    @Override
    public ResponseVo switchCompanyStatus(String companyId) {
        SysUser sysUser = UserUtils.getLoginUser().getSysUser();
        Company company=new Company();
        company = companyMapper.selectById(companyId);
        if (Objects.isNull(company)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "失败：当前公司不存在");
        }else {
           if (company.getDelFlag()==1||"".equals(company.getDelFlag())){
               return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "失败：当前公司已被删除");
           }else {
               Integer status = company.getStatus();
               if (status==0){
                   company.setStatus(1);
               }else {
                   company.setStatus(0);
               }
               int i = companyMapper.updateById(company);
               if (i>0){
                   return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "操作成功",company);
               }else {
                   return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "操作成功：未知错误",company);
               }

           }

        }
    }
}
