package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.ArticleFavor;

public interface ArticleFavorService {
    ServerResponse InsertFavor(ArticleFavor articleFavor);

    ServerResponse CancleFavor(ArticleFavor articleFavor);
}
