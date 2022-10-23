package com.zf.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zf.domain.dto.AccountDto;
import com.zf.domain.dto.StaffDto;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.PswVo;
import com.zf.domain.vo.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_user(用户表)】的数据库操作Service
* @createDate 2022-09-16 08:47:17
*/
public interface SysUserService extends IService<SysUser> {


    ResponseVo add(SysUser sysUser,String updateId);

    ResponseVo delete(long userid,String updateId);

    ResponseVo modify(SysUser sysUser,String updateId);

    ResponseVo selectAll(String userId);

  /**
   * 进入个人信息，查询个人简介以及头像
   * @param token
   * @return
   */

    ResponseVo selectUserInfo(String token);

  /**
   * 修改个人职业照以及个人简介
   * @param token
   * @param photo
   * @param info
   * @return
   */
    ResponseVo updateUserPhotonAndInfo(String token, MultipartFile photo,String info,HttpServletRequest request);



  ResponseVo updateUserOpenId(String subject, String openedId);



     ResponseVo updateUserWxCode(String token, HttpServletRequest request, MultipartFile file);

    ResponseVo selectUserInfoByWu(String userId);

    ResponseVo selectUserProPhotoByWu(String userId);

    ResponseVo addStaff(StaffDto staff);

    ResponseVo updateStaff(StaffDto staff);

    ResponseVo delStaffById(Integer id);

    ResponseVo SelectPage(String subject, Integer pageNum, Integer pageSize);


    ResponseVo getStaffById(Integer id);

    ResponseVo getAllAccount();

    ResponseVo delAccountById(Integer id);

    ResponseVo resetAccountPassword(Integer id);

    ResponseVo updateAccount(AccountDto accountDto);

    ResponseVo addAccount(AccountDto accountDto);

    ResponseVo getAccountById(Integer id);

    ResponseVo addUserList(String token, List<StaffDto> userList);

    ResponseVo changePassword(String token, PswVo psw) throws Exception;

}
