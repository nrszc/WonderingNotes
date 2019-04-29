package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.service.UserFansService;
import com.wondering.service.UserService;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/userfans")
public class UserFansController {

    @Autowired
    UserFansService userFansService;

    /**
     * 获取用户是否已被关注(by user_id)
     * @return
     */
    @RequestMapping(value = "get_userfollow", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userfollow(HttpSession session, String user_id)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createBySuccess("用户未登录", false);
        if(user_id.equals(user.getUser_id()+""))
            return ServerResponse.createByErrorMessage( "该页面为登录用户界面");
        return userFansService.getUserFollow(user_id, user.getUser_id()+"");
    }

    /**
     * 关注用户
     * @return
     */
    @RequestMapping(value = "tofollow", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse tofollow(HttpSession session, String user_id)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createBySuccess("用户未登录", false);
        if(user_id.equals(user.getUser_id()+""))
            return ServerResponse.createByErrorCodeMessage(2,"不能关注自己");
        return userFansService.ToFollow(user_id, user.getUser_id()+"");
    }

    /**
     * 取消关注
     * @return
     */
    @RequestMapping(value = "cancelfollow", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse cancelfollow(HttpSession session, String user_id)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createBySuccess("用户未登录", true);
        return userFansService.CancelFollow(user_id, user.getUser_id()+"");
    }
}
