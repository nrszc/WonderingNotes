package com.wondering.dao;

import com.wondering.pojo.SysNoticePrivate;

public interface SysNoticePrivateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysNoticePrivate record);

    int insertSelective(SysNoticePrivate record);

    SysNoticePrivate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysNoticePrivate record);

    int updateByPrimaryKey(SysNoticePrivate record);
}