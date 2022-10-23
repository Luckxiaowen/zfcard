package com.zf.mapper;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zf.domain.entity.SysUser;
import com.zf.domain.vo.SearchUserVo;
import com.zf.domain.vo.SysUserVo;
import com.zf.domain.vo.UserQueryVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
* @author Amireux
* @description 针对表【sys_user(用户表)】的数据库操作Mapper
* @createDate 2022-09-16 08:47:17
* @Entity com.zf.domain.SysUser
*/
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {


    List<SysUser> selectByConditions(SearchUserVo searchUserVo);

    List<SysUserVo> selectByCompanyId(Long companyid);

    List<SysUserVo> selectMyPage(Long companyid, Integer pageNum, Integer pageSize);

    List<SysUser> selectAllAccount(Long companyid);

    Page<SysUserVo> selectUserByQuery(Page<SysUserVo> page,@Param("userId") String userId,@Param("userJob") String userJob,@Param("startTime") String startTime,@Param("endTime") String endTime);
}
