package com.wondering.dao;

import com.wondering.pojo.SysNoticePublic;
import com.wondering.vo.Notice;

import java.util.List;

public interface SysNoticePublicMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysNoticePublic record);

    int insertSelective(SysNoticePublic record);

    SysNoticePublic selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysNoticePublic record);

    int updateByPrimaryKey(SysNoticePublic record);

    Integer SendNotice(SysNoticePublic sn);

    List<Notice> GetNoticePublic();

    List<Notice> GetMsg(Integer user_id);
}