package com.wondering.serviceimpl;

import com.wondering.common.ServerResponse;
import com.wondering.dao.ArticleFavorMapper;
import com.wondering.dao.ArticleMapper;
import com.wondering.pojo.ArticleFavor;
import com.wondering.service.ArticleFavorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleFavorService")
public class ArticleFavorServiceImpl implements ArticleFavorService {

    @Autowired
    ArticleFavorMapper articleFavorMapper;

    @Autowired
    ArticleMapper articleMapper;

    public ServerResponse InsertFavor(ArticleFavor articleFavor) {
        if(articleFavorMapper.InsertFavor(articleFavor)>0) {
            articleMapper.UpFavor(articleFavor.getArticle_id());
            return ServerResponse.createBySuccessMessage("点赞成功");
        }
        return ServerResponse.createByErrorMessage("点赞失败");
    }

    public ServerResponse CancleFavor(ArticleFavor articleFavor) {
        if(articleFavorMapper.CancleFavor(articleFavor)>0) {
            articleMapper.DownFavor(articleFavor.getArticle_id());
            return ServerResponse.createBySuccessMessage("取消点赞成功");
        }
        return ServerResponse.createByErrorMessage("取消点赞失败");
    }
}
