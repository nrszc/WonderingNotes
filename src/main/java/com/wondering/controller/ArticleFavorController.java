package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.ArticleFavor;
import com.wondering.service.ArticleFavorService;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/articlefavor")
public class ArticleFavorController {

    @Autowired
    ArticleFavorService articleFavorService;

    /**
     * 点赞
     * @return
     */
    @RequestMapping(value = "insert_favor", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse insert_favor(HttpSession session,Integer articleid) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        ArticleFavor articleFavor = new ArticleFavor();
        articleFavor.setArticle_id(articleid);
        articleFavor.setUser_id(user.getUser_id());
        return articleFavorService.InsertFavor(articleFavor);
    }

    /**
     * 取消点赞
     * @return
     */
    @RequestMapping(value = "cancel_favor", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse cancel_favor(HttpSession session,Integer articleid) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        ArticleFavor articleFavor = new ArticleFavor();
        articleFavor.setArticle_id(articleid);
        articleFavor.setUser_id(user.getUser_id());
        return articleFavorService.CancleFavor(articleFavor);
    }
}
