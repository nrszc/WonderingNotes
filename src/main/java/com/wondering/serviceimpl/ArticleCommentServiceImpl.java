package com.wondering.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.dao.ArticleCommentMapper;
import com.wondering.pojo.ArticleComment;
import com.wondering.pojo.CommentComment;
import com.wondering.service.ArticleCollectService;
import com.wondering.service.ArticleCommentService;
import com.wondering.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("articleCommentService")
public class ArticleCommentServiceImpl implements ArticleCommentService {

    @Autowired
    ArticleCommentMapper articleCommentMapper;

    public ServerResponse ToComment(ArticleComment comment) {
        if(articleCommentMapper.ToComment(comment)>0) {
            if (articleCommentMapper.UpdateComment(comment.getArticle_id()) > 0) {
                return ServerResponse.createBySuccessMessage("评论成功");
            }
        }
        return ServerResponse.createByErrorMessage("评论失败");
    }

    public ServerResponse getComment(Integer articleid, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<CommentVO> list = articleCommentMapper.getCommment(articleid);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getComment1(Integer user_id, Integer articleid, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<CommentVO> list = articleCommentMapper.getCommment1(articleid, user_id);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getNewComment(Integer articleid, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<CommentVO> list = articleCommentMapper.getNewCommment(articleid);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getNewComment1(Integer user_id, Integer articleid, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<CommentVO> list = articleCommentMapper.getNewCommment1(articleid, user_id);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getReply(Integer commentid) {
        List<CommentVO> list = articleCommentMapper.getReply(commentid);
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse ToFavorComment(Integer user_id, Integer commentid) {
        if(articleCommentMapper.UpFavorComment(commentid)>0)
        {
            if(articleCommentMapper.ToFavorComment(user_id, commentid)>0)
                return ServerResponse.createBySuccessMessage("点赞成功");
        }
        return ServerResponse.createByErrorMessage("点赞失败");
    }

    public ServerResponse CancelFavorComment(Integer user_id, Integer commentid) {
        if(articleCommentMapper.DownFavorComment(commentid)>0)
        {
            if(articleCommentMapper.CancelFavorComment(user_id, commentid)>0)
                return ServerResponse.createBySuccessMessage("取消点赞成功");
        }
        return ServerResponse.createByErrorMessage("取消点赞失败");
    }

    public ServerResponse ToReplyit(CommentComment commentComment) {
        if(articleCommentMapper.ToReplyit(commentComment)>0) {
            if (articleCommentMapper.UpdateArticleComment(commentComment.getComment_id()) > 0) {
                return ServerResponse.createBySuccessMessage("评论成功");
            }
        }
        return ServerResponse.createByErrorMessage("评论失败");
    }
}
