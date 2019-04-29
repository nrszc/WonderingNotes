package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.ArticleComment;
import com.wondering.pojo.CommentComment;

public interface ArticleCommentService {
    ServerResponse ToComment(ArticleComment comment);

    ServerResponse getComment(Integer articleid, int pn);

    ServerResponse getComment1(Integer user_id, Integer articleid, int pn);

    ServerResponse getNewComment(Integer articleid, int pn);

    ServerResponse getNewComment1(Integer user_id, Integer articleid, int pn);

    ServerResponse getReply(Integer commentid);

    ServerResponse ToFavorComment(Integer user_id, Integer commentid);

    ServerResponse CancelFavorComment(Integer user_id, Integer commentid);

    ServerResponse ToReplyit(CommentComment commentComment);
}
