package com.wondering.service;

import com.wondering.common.ServerResponse;

public interface ArticleCollectService {
    ServerResponse getArticleCollect(String articleid, String user_id);

    ServerResponse ToCollect(String articleid, String user_id);

    ServerResponse CancelCollect(String articleid, String user_id);

    ServerResponse getUserCollectArticle(Integer user_id, int pn);
}
