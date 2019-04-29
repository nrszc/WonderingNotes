package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.*;
import com.wondering.service.ReportService;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * 游记举报
     * @return
     */
    @RequestMapping(value = "articlereport", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse articlereport(HttpSession session, Report report)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        report.setUser_id(user.getUser_id());
        return reportService.ArticleReport(report);
    }

    /**
     * 一级评论举报
     * @return
     */
    @RequestMapping(value = "commentreport", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse commentreport(HttpSession session, CommentReport report)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        report.setUser_id(user.getUser_id());
        return reportService.CommentReport(report);
    }

    /**
     * 二级评论举报
     * @return
     */
    @RequestMapping(value = "commentcommentreport", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse commentcommentreport(HttpSession session, CommentCommentReport report)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        report.setUser_id(user.getUser_id());
        return reportService.CommentCommentReport(report);
    }

    /**
     * 文章评论
     * @return
     */
    @RequestMapping(value = "articlecomment", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse articlecomment(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return reportService.ArticleComment(user.getUser_id());
    }

    /**
     * 评论评论
     * @return
     */
    @RequestMapping(value = "commentcomment", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse commentcomment(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return reportService.CommentComment(user.getUser_id());
    }

    /**
     * 文章点赞
     * @return
     */
    @RequestMapping(value = "articlefavor", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse articlefavor(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return reportService.ArticleFavor(user.getUser_id());
    }

    /**
     * 评论点赞
     * @return
     */
    @RequestMapping(value = "commentfavor", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse commentfavor(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return reportService.CommentFavor(user.getUser_id());
    }

    //admin
    /**
     * 获取游记举报
     * @return
     */
    @RequestMapping(value = "get_reportnotes", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_reportnotes(HttpSession session, int pn)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return reportService.GetReportNotes(pn);
    }

    /**
     * 获取评论举报
     * @return
     */
    @RequestMapping(value = "get_reportcomments", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_reportcomments(HttpSession session, int pn)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return reportService.GetReportComments(pn);
    }

    /**
     * 更新文章举报
     * @return
     */
    @RequestMapping(value = "update_report", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_report(HttpSession session, @RequestParam(value = "checkID[]") String[] checkID)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        int [] idArray = new int[checkID.length];
        for (int i=0;i<checkID.length;i++){         //把或取到的String数组转换为int数组
            idArray[i] = Integer.parseInt(checkID[i]);
        }
        return reportService.UpdateReport(idArray);
    }

    /**
     * 查询文章举报
     * @return
     */
    @RequestMapping(value = "search_report", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_report(HttpSession session, int pn, Integer status, String date1, String date2)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return reportService.SearchReport(pn, status, date1, date2);
    }

    /**
     * 更新一级评论举报
     * @return
     */
    @RequestMapping(value = "update_creport", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_creport(HttpSession session, @RequestParam(value = "checkID[]") String[] checkID)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        int [] idArray = new int[checkID.length];
        for (int i=0;i<checkID.length;i++){         //把或取到的String数组转换为int数组
            idArray[i] = Integer.parseInt(checkID[i]);
        }
        return reportService.UpdateCReport(idArray);
    }

    /**
     * 查询一级评论举报
     * @return
     */
    @RequestMapping(value = "search_creport", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_creport(HttpSession session, int pn, Integer status, String date1, String date2)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return reportService.SearchCReport(pn, status, date1, date2);
    }

    /**
     * 获取二级评论举报
     * @return
     */
    @RequestMapping(value = "get_reportcc", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_reportcc(HttpSession session, int pn)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return reportService.GetReportCC(pn);
    }

    /**
     * 更新二级评论举报
     * @return
     */
    @RequestMapping(value = "update_ccreport", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_ccreport(HttpSession session, @RequestParam(value = "checkID[]") String[] checkID)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        int [] idArray = new int[checkID.length];
        for (int i=0;i<checkID.length;i++){         //把或取到的String数组转换为int数组
            idArray[i] = Integer.parseInt(checkID[i]);
        }
        return reportService.UpdateCCReport(idArray);
    }

    /**
     * 查询二级评论举报
     * @return
     */
    @RequestMapping(value = "search_ccreport", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_ccreport(HttpSession session, int pn, Integer status, String date1, String date2)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return reportService.SearchCCReport(pn, status, date1, date2);
    }
}
