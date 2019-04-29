package com.wondering.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.dao.ReportMapper;
import com.wondering.pojo.CommentCommentReport;
import com.wondering.pojo.CommentReport;
import com.wondering.pojo.Report;
import com.wondering.service.ReportService;
import com.wondering.vo.ArticleVO;
import com.wondering.vo.Comment;
import com.wondering.vo.ReportVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("reportService")
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportMapper reportMapper;

    public ServerResponse ArticleReport(Report report) {
        if(reportMapper.ArticleReport(report)>0)
            return ServerResponse.createBySuccessMessage("举报成功");
        return ServerResponse.createByErrorMessage("举报失败");
    }

    public ServerResponse CommentReport(CommentReport report) {
        if(reportMapper.CommentReport(report)>0)
            return ServerResponse.createBySuccessMessage("举报成功");
        return ServerResponse.createByErrorMessage("举报失败");
    }

    public ServerResponse CommentCommentReport(CommentCommentReport report) {
        if(reportMapper.CommentCommentReport(report)>0)
            return ServerResponse.createBySuccessMessage("举报成功");
        return ServerResponse.createByErrorMessage("举报失败");
    }

    public ServerResponse ArticleComment(Integer user_id) {
        List<Comment> list = reportMapper.ArticleComment(user_id);
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse CommentComment(Integer user_id) {
        List<Comment> list = reportMapper.CommentComment(user_id);
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse ArticleFavor(Integer user_id) {
        List<Comment> list = reportMapper.ArticleFavor(user_id);
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse CommentFavor(Integer user_id) {
        List<Comment> list = reportMapper.CommentFavor(user_id);
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse GetReportNotes(int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<ReportVO> list = reportMapper.GetReportNotes();
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse GetReportComments(int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<ReportVO> list = reportMapper.GetReportComments();
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse UpdateReport(int[] idArray) {
        Integer result = reportMapper.UpdateReport(idArray);
        if(result>0)
            return ServerResponse.createBySuccessMessage("审核成功");
        return ServerResponse.createByErrorMessage("审核成功");
    }

    public ServerResponse SearchReport(int pn, Integer status, String date1, String date2) {
        PageHelper.startPage(pn, Const.articlecount);
        List<ReportVO> list = reportMapper.SearchReport(status, date1, date2);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse UpdateCReport(int[] idArray) {
        Integer result = reportMapper.UpdateCReport(idArray);
        if(result>0)
            return ServerResponse.createBySuccessMessage("审核成功");
        return ServerResponse.createByErrorMessage("审核成功");
    }

    public ServerResponse SearchCReport(int pn, Integer status, String date1, String date2) {
        PageHelper.startPage(pn, Const.articlecount);
        List<ReportVO> list = reportMapper.SearchCReport(status, date1, date2);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse GetReportCC(int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<ReportVO> list = reportMapper.GetReportCC();
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse UpdateCCReport(int[] idArray) {
        Integer result = reportMapper.UpdateCCReport(idArray);
        if(result>0)
            return ServerResponse.createBySuccessMessage("审核成功");
        return ServerResponse.createByErrorMessage("审核成功");
    }

    public ServerResponse SearchCCReport(int pn, Integer status, String date1, String date2) {
        PageHelper.startPage(pn, Const.articlecount);
        List<ReportVO> list = reportMapper.SearchCCReport(status, date1, date2);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }
}
