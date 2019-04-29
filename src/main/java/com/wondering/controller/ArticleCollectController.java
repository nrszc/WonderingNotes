package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.ArticleCollect;
import com.wondering.service.ArticleCollectService;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articlecollect")
public class ArticleCollectController {

    @Autowired
    ArticleCollectService articleCollectService;

    /**
     * 获取用户是否已收藏文章(by user_id)
     * @return
     */
    @RequestMapping(value = "get_articlecollect", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_articlecollect(HttpSession session, String articleid)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createBySuccess("用户未登录", false);
        return articleCollectService.getArticleCollect(articleid, user.getUser_id()+"");
    }

    /**
     * 收藏游记
     * @return
     */
    @RequestMapping(value = "tocollect", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse tocollect(HttpSession session, String articleid)
    {
        System.out.println(articleid+"yyyyyyyyyyyyyyyyyyyyyyy");
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createBySuccess("用户未登录", false);
        return articleCollectService.ToCollect(articleid, user.getUser_id()+"");
    }

    /**
     * 取消收藏
     * @return
     */
    @RequestMapping(value = "cancelcollect", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse cancelcollect(HttpSession session, String articleid)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createBySuccess("用户未登录", true);
        return articleCollectService.CancelCollect(articleid, user.getUser_id()+"");
    }

    /**
     * home.html
     * 获取登录用户收藏游记
     * @return
     */
    @RequestMapping(value = "get_usercollectarticle", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_usercollectarticle(HttpSession session,int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return articleCollectService.getUserCollectArticle(user.getUser_id(),pn);
    }
}
