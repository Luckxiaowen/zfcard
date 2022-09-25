package com.zf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.ResponseVo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author Amireux
 * @description 针对表【sys_user(用户表)】的数据库操作Service
 * @createDate 2022-09-16 08:47:17
 */
public interface SysUserService extends IService<SysUser> {


    ResponseVo add(SysUser sysUser, String updateId);

    ResponseVo delete(long userid, String updateId);

    ResponseVo modify(SysUser sysUser, String updateId);

    ResponseVo selectAll();

    /**
     * 进入个人信息，查询个人简介以及头像
     *
     * @param token
     * @return
     */

    ResponseVo selectUserInfo(String token);

    /**
     * 修改个人职业照
     *
     * @param token
     * @param photo
     * @return
     */
    ResponseVo updateUserPhoton(String token, MultipartFile photo);

    /**
     * 个人简介修改
     *
     * @param token
     * @param info
     * @return
     */
    ResponseVo updateInfo(String token, String info);

    ResponseVo selectByConditions(String conditions);

    ResponseVo updateUserOpenId(String userId, String openId);



     ResponseVo updateUserWxCode(String token, HttpServletRequest request, MultipartFile file);
}
