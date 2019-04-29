package com.wondering.dao;

import com.wondering.pojo.ArticleCollect;
import com.wondering.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleCollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleCollect record);

    int insertSelective(ArticleCollect record);

    ArticleCollect selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleCollect record);

    int updateByPrimaryKey(ArticleCollect record);

    Integer getArticleCollect(@Param("articleid")String articleid, @Param("user_id")String user_id);

    Integer ToCollect(@Param("articleid")String articleid, @Param("user_id")String user_id);

    Integer CancelCollect(@Param("articleid")String articleid, @Param("user_id")String user_id);

    List<ArticleVO> getUserCollectArticle(Integer user_id);
}