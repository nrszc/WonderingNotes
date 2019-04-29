package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.Article;
import com.wondering.pojo.ArticleComment;
import com.wondering.pojo.CommentComment;
import com.wondering.service.ArticleCommentService;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articlecomment")
public class ArticleCommentController {

    @Autowired
    ArticleCommentService articleCommentService;

    /**
     * 提交评论
     * @return
     */
    @RequestMapping(value = "tocomment", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse insert_favor(HttpSession session, Integer articleid, String content) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        ArticleComment comment = new ArticleComment();
        comment.setArticle_id(articleid);
        comment.setUser_id(user.getUser_id());
        comment.setContent(content);
        return articleCommentService.ToComment(comment);
    }

    /**
     * 获取评论
     * @return
     */
    @RequestMapping(value = "get_comment", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_comment(HttpSession session, Integer articleid, int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return articleCommentService.getComment(articleid,pn);
        return articleCommentService.getComment1(user.getUser_id(),articleid,pn);
    }

    /**
     * 获取最新评论
     * @return
     */
    @RequestMapping(value = "get_newcomment", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_newcomment(HttpSession session, Integer articleid, int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return articleCommentService.getNewComment(articleid,pn);
        return articleCommentService.getNewComment1(user.getUser_id(),articleid,pn);
    }

    /**
     * 获取回复
     * @return
     */
    @RequestMapping(value = "get_reply", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_reply(Integer commentid) {
        return articleCommentService.getReply(commentid);
    }

    /**
     * 点赞评论
     * @return
     */
    @RequestMapping(value = "to_favorcomment", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse to_favorcomment(HttpSession session, Integer commentid) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return articleCommentService.ToFavorComment(user.getUser_id(),commentid);
    }

    /**
     * 取消点赞评论
     * @return
     */
    @RequestMapping(value = "cancel_favorcomment", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse cancel_favorcomment(HttpSession session, Integer commentid) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return articleCommentService.CancelFavorComment(user.getUser_id(),commentid);
    }

    /**
     * 回复评论
     * @return
     */
    @RequestMapping(value = "to_replyit", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse to_replyit(HttpSession session, CommentComment commentComment) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        commentComment.setUser_id(user.getUser_id());
        return articleCommentService.ToReplyit(commentComment);
    }
}
