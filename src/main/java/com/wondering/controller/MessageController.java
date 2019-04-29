package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.Message;
import com.wondering.service.MessageService;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    /**
     * letter.html
     * 获取聊天列表信息
     * @return
     */
    @RequestMapping(value = "get_message", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_message(HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return messageService.getMessage(user.getUser_id());
    }

    /**
     * chat.html
     * 获取聊天列表信息
     * @return
     */
    @RequestMapping(value = "get_usermessage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_usermessage(HttpSession session,String user_id) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return messageService.getUserMessage(user.getUser_id(),user_id);
    }

    /**
     * chat.html
     * 获取聊天列表信息
     * @return
     */
    @RequestMapping(value = "update_message", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_message(HttpSession session,String from_user_id, String to_user_id) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return messageService.UpdateMessage(from_user_id, to_user_id);
    }
}
