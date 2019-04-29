package com.wondering.dao;

import com.wondering.pojo.ArticleFavor;

public interface ArticleFavorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleFavor record);

    int insertSelective(ArticleFavor record);

    ArticleFavor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleFavor record);

    int updateByPrimaryKey(ArticleFavor record);

    Integer InsertFavor(ArticleFavor articleFavor);

    Integer CancleFavor(ArticleFavor articleFavor);
}