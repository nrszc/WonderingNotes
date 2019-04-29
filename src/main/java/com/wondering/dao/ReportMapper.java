package com.wondering.dao;

import com.wondering.pojo.CommentCommentReport;
import com.wondering.pojo.CommentReport;
import com.wondering.pojo.Report;
import com.wondering.vo.Comment;
import com.wondering.vo.ReportVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Report record);

    int insertSelective(Report record);

    Report selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Report record);

    int updateByPrimaryKey(Report record);

    Integer ArticleReport(Report report);

    Integer CommentReport(CommentReport report);

    Integer CommentCommentReport(CommentCommentReport report);

    List<Comment> ArticleComment(Integer user_id);

    List<Comment> CommentComment(Integer user_id);

    List<Comment> ArticleFavor(Integer user_id);

    List<Comment> CommentFavor(Integer user_id);

    List<ReportVO> GetReportNotes();

    List<ReportVO> GetReportComments();

    Integer UpdateReport(int[] idArray);

    List<ReportVO> SearchReport(@Param("status") Integer status, @Param("date1")String date1, @Param("date2")String date2);

    Integer UpdateCReport(int[] idArray);

    List<ReportVO> SearchCReport(@Param("status") Integer status, @Param("date1")String date1, @Param("date2")String date2);

    List<ReportVO> GetReportCC();

    Integer UpdateCCReport(int[] idArray);

    List<ReportVO> SearchCCReport(@Param("status") Integer status, @Param("date1")String date1, @Param("date2")String date2);
}