package com.zf.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zf.domain.entity.*;

import com.zf.domain.vo.CompanyClientVo;
import com.zf.domain.vo.NotesVo;
import com.zf.domain.vo.ResponseVo;
import com.zf.enums.AppHttpCodeEnum;
import com.zf.exception.SystemException;
import com.zf.mapper.*;
import com.zf.mapper.ClientMapper;
import com.zf.mapper.CompanyClientMapper;
import com.zf.mapper.NotesMapper;
import com.zf.mapper.SysUserMapper;
import com.zf.service.NotesService;
import com.zf.utils.BeanCopyUtils;
import com.zf.utils.JwtUtil;
import com.zf.utils.UserUtils;
import com.zf.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Amireux
 * @description 针对表【notes(用户留言表)】的数据库操作Service实现
 * @createDate 2022-09-16 08:47:17
 */
@Service
public class NotesServiceImpl extends ServiceImpl<NotesMapper, Notes> implements NotesService {
    @Autowired
    private NotesMapper notesMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Resource
    private CompanyClientMapper companyClientMapper;

    @Resource
    private ExposureTotalMapper exposureTotalMapper;



    @Override
    public ResponseVo getAllNoteById(String id) throws Exception {
        if (id==null||"".equals(id)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：输入为空");
        }else{
            Integer userId = getInteger(id);
            if (Objects.isNull(sysUserMapper.selectById(userId))){
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "获取失败：不存在此员工");
            }else{
                List<NotesVo> notesVos = this.HaveReplyNotes(String.valueOf(userId));
                System.out.println("notesVos = " + notesVos);
                return ResponseVo.okResult(notesVos);
            }
        }

    }

  public CompanyClient getClient(String phoneNum, String name, Long companyUserId) {

    LambdaQueryWrapper<CompanyClient> wrapper = new LambdaQueryWrapper<>();
    wrapper.eq(CompanyClient::getClientTel,phoneNum).eq(CompanyClient::getDelFlag,0);

    CompanyClient client = companyClientMapper.selectOne(wrapper);

    if (client == null || client.getDelFlag() == 1){
      Date date = new Date();
      CompanyClient companyC = new CompanyClient(null, name, phoneNum, "0", "0", "0", "0", "0", 0, companyUserId, date, date);
      companyClientMapper.insert(companyC);
    }

    LambdaQueryWrapper<CompanyClient> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(CompanyClient::getClientTel,phoneNum).eq(CompanyClient::getDelFlag,0);

    return companyClientMapper.selectOne(queryWrapper);

  }

    @Override
    public ResponseVo addNotes(String userId, Notes notes) {
        if (userId==null||"".equals(userId)){
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "留言失败: 用户Id为空");
        }else {
            SysUser sysUser = sysUserMapper.selectById(Long.parseLong(userId));
            if (!Objects.isNull(sysUser)) {
                if (!Objects.isNull(notes)) {
                    if (StringUtils.hasText(notes.getTel())) {
                        if (Validator.isMobile(notes.getTel())) {
                            if ("".equals(notes.getNotesContent().trim()) || notes.getNotesContent() == null) {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "留言失败: 留言内容为空");
                            } else {
                                if ("先生".equals(notes.getName()) || "女士".equals(notes.getName())) {
                                    return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "留言失败: 请选择称呼");
                                } else {
                                    notes.setCreateBy(Long.parseLong(userId));
                                    notes.setCreateTime(new Date());
                                    notes.setDelFlag(0);
                                    int insert = notesMapper.insert(notes);
                                    //todo 留言成功
                                    if (insert > 0) {
                                        LambdaQueryWrapper<ExposureTotal>wrapper=new LambdaQueryWrapper<>();
                                        wrapper.eq(ExposureTotal::getCreateBy,userId);
                                        ExposureTotal eTotal = exposureTotalMapper.selectOne(wrapper);
                                        eTotal.setDayNotes(eTotal.getDayNotes()+1);
                                        System.out.println("eTotal = " + eTotal);
                                        exposureTotalMapper.updateById(eTotal);
                                        LambdaQueryWrapper<Client> queryWrapper = new LambdaQueryWrapper<>();
                                        queryWrapper.eq(Client::getTel, notes.getTel());
                                        Client client1 = clientMapper.selectOne(queryWrapper);
                                        int insert1 = 0;
                                        if (!Objects.isNull(client1)) {
                                            System.out.println("已存在手机号为 :" + notes.getTel() + "的客户");
                                            CompanyClient companyClient = getClient(notes.getTel(), notes.getName(), Long.valueOf(userId));
                                            Integer saveLeaveNum = Integer.valueOf(companyClient.getLeaveNum());
                                            companyClient.setSaveCaseNum(String.valueOf(saveLeaveNum+1));
                                            companyClientMapper.updateById(companyClient);


                                        } else {
                                            Client client = new Client();
                                            client.setName(notes.getName());
                                            client.setTel(notes.getTel());
                                            client.setCreatedBy(Long.parseLong(userId));
                                            client.setUpdatedBy(Long.parseLong(userId));
                                            client.setUpdatedTime(new Date());
                                            client.setCreatedTime(new Date());
                                            insert1 = clientMapper.insert(client);
                                            CompanyClient companyClient = getClient(notes.getTel(), notes.getName(), Long.valueOf(userId));
                                            companyClient.setSaveCaseNum(companyClient.getSaveCaseNum()+1);
                                            int i1 = companyClientMapper.updateById(companyClient);
                                            LambdaQueryWrapper<ExposureTotal>queryWrapper1=new LambdaQueryWrapper<>();
                                            queryWrapper1.eq(ExposureTotal::getCreateBy,userId);
                                            ExposureTotal exposureTotal = exposureTotalMapper.selectOne(queryWrapper1);
                                            exposureTotal.setDayAddClient(exposureTotal.getDayAddClient()+1);
                                            exposureTotalMapper.updateById(exposureTotal);
                                        }
                                        return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "留言成功");
                                    } else {
                                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "留言失败");
                                    }
                                }
                            }
                        }

                    } else {
                        if ("".equals(notes.getNotesContent().trim())) {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "留言失败: 留言内容为空");
                        } else {
                            notes.setIsPublic(1);
                            if ("".equals(notes.getAvatar())) {
                                notes.setAvatar("img.wenstudy.top/2022/09/27/083413603570476880d091217fd91af0.jpg");
                            } else {
                                notes.setAvatar(notes.getAvatar());
                            }
                            notes.setName("微信用户");
                            notes.setCreateBy(Long.parseLong(userId));
                            notes.setCreateTime(new Date());
                            if (Validator.isMobile(notes.getTel())) {
                                notes.setTel(notes.getTel());
                            } else {
                                notes.setTel("0");
                            }
                            int insert = notesMapper.insert(notes);
                            if (insert > 0) {
                                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "留言成功");
                            } else {
                                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "留言失败");
                            }
                        }
                    }
                }
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "留言失败：留言实体为空");
            } else {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "留言失败:不存在此员工");
            }
        }
    }


    @Override
    public ResponseVo replyNotes(String userId, String noteid, String rcontent) {
        SysUser sysUser = sysUserMapper.selectById(Long.parseLong(userId));
        Notes notes = new Notes();
        if (Objects.isNull(sysUser)) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "回复失败:不存在此员工");
        } else {
            notes = notesMapper.selectById(Long.parseLong(noteid));
            System.out.println("notes = " + notes);
            if (Objects.isNull(notes)) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "回复失败:不存在当前留言");
            }
            if (!(notes.getCreateBy() == Long.parseLong(userId))) {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "回复失败:此条留言不在你的账号内");
            } else {
                LambdaQueryWrapper<Notes> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(Notes::getReplyId, Long.parseLong(noteid))
                        .eq(Notes::getCreateBy, Long.parseLong(userId));
                Notes notes1 = notesMapper.selectOne(queryWrapper);
                if (Objects.isNull(notes1)) {
                    if ("".equals(rcontent.trim())) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "回复失败:回复内容为空");
                    } else {
                        Notes notes2 = new Notes();
                        notes2.setReplyId(Long.parseLong(noteid));
                        notes2.setCreateTime(new Date());
                        notes2.setCreateBy(Long.parseLong(userId));
                        notes2.setNotesContent(rcontent);
                        notes2.setDelFlag(0);
                        notes2.setIsPublic(0);
                        notes2.setAvatar("员工回复：无头像");
                        int insert = notesMapper.insert(notes2);
                        if (insert > 0) {
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "回复成功");
                        } else {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "回复失败");
                        }
                    }
                } else {
                    if ("".equals(rcontent.trim())) {
                        return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改留言:修改内容为空");
                    } else {
                        notes1.setNotesContent(rcontent);
                        int i = notesMapper.updateById(notes1);
                        if (i > 0) {
                            return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "修改留言:修改成功");
                        } else {
                            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "修改留言:修改失败");
                        }
                    }
                }
            }
        }
    }

    @Override
    public ResponseVo deleteNoteById(String userId, Long noteid) {

        LambdaQueryWrapper<Notes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notes::getCreateBy, userId)
                .eq(Notes::getId, noteid);
        Notes notes = notesMapper.selectOne(queryWrapper);
        System.out.println("notes = " + notes);
        if (notes.getDelFlag() == 1 || "1".equals(notes.getDelFlag())) {
            return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "删除留言:当前留言已被删除，请刷新页面");
        } else {
            boolean b = removeById(noteid);
            if (b) {
                return new ResponseVo(AppHttpCodeEnum.SUCCESS.getCode(), "删除留言成功");
            } else {
                return new ResponseVo(AppHttpCodeEnum.FAIL.getCode(), "删除留言失败");
            }
        }
    }

    public List<NotesVo> HaveReplyNotes(String token) throws Exception {
        LambdaQueryWrapper<Notes> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Notes::getCreateBy, Long.valueOf(token));
        queryWrapper.eq(Notes::getDelFlag, 0);
        List<Notes> list = this.list(queryWrapper);
        List<NotesVo> noteVoList = BeanCopyUtils.copyBeanList(list, NotesVo.class);
        System.out.println(noteVoList);
        List<NotesVo> notesList = noteVoList.stream()
                .filter(item -> item.getReplyId() == 0)
                .map(m -> {
                    m.setChildrenNote(getChildrenNote(m, noteVoList));
                    return m;
                })
                .collect(Collectors.toList());
        return notesList;
    }

    public NotesVo getChildrenNote(NotesVo noteVo, List<NotesVo> allList) {
        for (NotesVo item : allList) {
            if (Objects.equals(item.getReplyId(), noteVo.getId())) {
              return item;
            }
        }
        return null;
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


