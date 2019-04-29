package com.wondering.dao;

import com.wondering.pojo.ArticleComment;
import com.wondering.pojo.CommentComment;
import com.wondering.vo.CommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);

    ArticleComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    Integer ToComment(ArticleComment comment);

    List<CommentVO> getCommment(Integer articleid);

    List<CommentVO> getCommment1(@Param("articleid") Integer articleid,@Param("user_id") Integer user_id);

    List<CommentVO> getNewCommment(Integer articleid);

    List<CommentVO> getNewCommment1(@Param("articleid") Integer articleid,@Param("user_id") Integer user_id);

    Integer UpdateComment(Integer id);

    List<CommentVO> getReply(Integer commentid);

    Integer UpFavorComment(Integer commentid);

    Integer ToFavorComment(@Param("user_id")Integer user_id, @Param("commentid")Integer commentid);

    Integer DownFavorComment(Integer commentid);

    Integer CancelFavorComment(@Param("user_id")Integer user_id,@Param("commentid") Integer commentid);

    Integer ToReplyit(CommentComment commentComment);

    Integer UpdateArticleComment(Integer comment_id);
}