package com.wondering.dao;

import com.wondering.pojo.Article;
import com.wondering.vo.ArticleVO;
import com.wondering.vo.StatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKey(Article record);

    Integer InsertArticle(Article article);

    List<ArticleVO> getUserArticle(Integer user_id);

    List<ArticleVO> getUserArticle1(Integer user_id);

    List<ArticleVO> getUserArticle2(@Param("user_id") Integer user_id,@Param("user_id1") Integer user_id1);

    List<ArticleVO> getAllPage();

    List<ArticleVO> getAllPage1(Integer user_id);

    List<Article> getCarousel();

    List<ArticleVO> getNewPage(Integer user_id);

    List<ArticleVO> getHotPage();

    List<ArticleVO> getHotPage1(Integer user_id);

    ArticleVO ShowArticle(String articleid);

    ArticleVO ShowArticle1(@Param("user_id")Integer user_id, @Param("articleid")String articleid);

    List<ArticleVO> searchTitleByWord(String search_word);

    List<ArticleVO> searchTitleByWord1(@Param("user_id") Integer user_id,@Param("search_word") String search_word);

    List<ArticleVO> searchPlaceByWord(String search_word);

    List<ArticleVO> searchPlaceByWord1(@Param("user_id")Integer user_id,@Param("search_word") String search_word);

    Integer DeleteArticle(String articleid);

    void UpFavor(Integer article_id);

    void DownFavor(Integer article_id);

    Integer GetSumArticle();

    List<StatisticsVO> GetStaArticle(String year);
}