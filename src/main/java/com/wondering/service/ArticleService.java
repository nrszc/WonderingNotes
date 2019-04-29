package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.Article;

public interface ArticleService {
    Boolean insertnotes(Article article);

    ServerResponse getUserArticle(Integer user_id, int pn);

    ServerResponse getUserArticle1(Integer user_id, int pn);

    ServerResponse getUserArticle2(Integer user_id, Integer user_id1, int pn);

    ServerResponse getAllPage( int pn);

    ServerResponse getAllPage1(Integer user_id, int pn);

    ServerResponse getCarousel();

    ServerResponse getNewPage(Integer user_id, int pn);

    ServerResponse getHotPage(int pn);

    ServerResponse getHotPage1(Integer user_id, int pn);

    ServerResponse ShowArticle(String articleid);

    ServerResponse ShowArticle1(Integer user_id, String articleid);

    ServerResponse searchTitleByWord(String search_word, int pn);

    ServerResponse searchTitleByWord1(Integer user_id, String search_word, int pn);

    ServerResponse searchPlaceByWord(String search_word, int pn);

    ServerResponse searchPlaceByWord1(Integer user_id, String search_word, int pn);

    ServerResponse DeleteArticle(String articleid);

    ServerResponse GetSumArticle();

    ServerResponse GetStaArticle(String year);
}
