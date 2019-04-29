package com.wondering.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.dao.ArticleCollectMapper;
import com.wondering.service.ArticleCollectService;
import com.wondering.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("articleCollectService")
public class ArticleCollectServiceImpl implements ArticleCollectService {

    @Autowired
    ArticleCollectMapper articleCollectMapper;

    public ServerResponse getArticleCollect(String articleid, String user_id) {
        if(articleCollectMapper.getArticleCollect(articleid, user_id)>0)
            return ServerResponse.createBySuccess("已收藏", true);
        return ServerResponse.createBySuccess("未收藏", false);
    }

    public ServerResponse ToCollect(String articleid, String user_id) {
        if(articleCollectMapper.getArticleCollect(articleid, user_id)>0)
            return ServerResponse.createBySuccess("已收藏", true);
        if(articleCollectMapper.ToCollect(articleid, user_id)>0)
            return ServerResponse.createBySuccess("收藏成功", true);
        return ServerResponse.createBySuccess("收藏失败", false);
    }

    public ServerResponse CancelCollect(String articleid, String user_id) {
        if(articleCollectMapper.getArticleCollect(articleid, user_id)>0){
            if(articleCollectMapper.CancelCollect(articleid, user_id)>0)
                return ServerResponse.createBySuccess("取消收藏成功", false);
        }
        return ServerResponse.createBySuccess("用户本身没有收藏该游记,不能取消", true);
    }

    public ServerResponse getUserCollectArticle(Integer user_id, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<ArticleVO> list = articleCollectMapper.getUserCollectArticle(user_id);
        System.out.println("1-------------------2");
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }
}
