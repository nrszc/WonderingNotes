package com.wondering.dao;

import com.wondering.pojo.Bgimg;

import java.util.List;

public interface BgimgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Bgimg record);

    int insertSelective(Bgimg record);

    Bgimg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Bgimg record);

    int updateByPrimaryKey(Bgimg record);

    Integer InsertImg(Bgimg bgimg);

    List<Bgimg> GetBgimg();

    Integer DeleteBgimg(Integer bgimg_id);

    Bgimg GetLoginBgimg();
}