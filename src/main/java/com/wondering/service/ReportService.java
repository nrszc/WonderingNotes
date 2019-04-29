package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.CommentCommentReport;
import com.wondering.pojo.CommentReport;
import com.wondering.pojo.Report;

public interface ReportService {
    ServerResponse ArticleReport(Report report);

    ServerResponse CommentReport(CommentReport report);

    ServerResponse CommentCommentReport(CommentCommentReport report);

    ServerResponse ArticleComment(Integer user_id);

    ServerResponse CommentComment(Integer user_id);

    ServerResponse ArticleFavor(Integer user_id);

    ServerResponse CommentFavor(Integer user_id);

    ServerResponse GetReportNotes(int pn);

    ServerResponse GetReportComments(int pn);

    ServerResponse UpdateReport(int[] idArray);

    ServerResponse SearchReport(int pn, Integer status, String date1, String date2);

    ServerResponse UpdateCReport(int[] idArray);

    ServerResponse SearchCReport(int pn, Integer status, String date1, String date2);

    ServerResponse GetReportCC(int pn);

    ServerResponse UpdateCCReport(int[] idArray);

    ServerResponse SearchCCReport(int pn, Integer status, String date1, String date2);
}
