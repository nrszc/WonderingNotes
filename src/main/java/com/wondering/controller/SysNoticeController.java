package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.Admin;
import com.wondering.pojo.SysNoticePublic;
import com.wondering.pojo.User;
import com.wondering.service.SysNoticePrivateService;
import com.wondering.service.SysNoticePublicService;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sysnotice")
public class SysNoticeController {

    @Autowired
    SysNoticePublicService sysNoticePublicService;

    @Autowired
    SysNoticePrivateService sysNoticePrivateService;

    /**
     * public
     * 发送通知
     * @return
     */
    @RequestMapping(value = "send_notice", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse send_notice(HttpSession session, String content)
    {
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin==null)
            return ServerResponse.createByErrorMessage("未登录");
        SysNoticePublic sn = new SysNoticePublic();
        sn.setAdmin_id(admin.getId());
        sn.setContent(content);
        return sysNoticePublicService.SendNotice(sn);
    }

    /**
     * public
     * 发送通知
     * @return
     */
    @RequestMapping(value = "get_noticepublic", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_noticepublic(HttpSession session, int pn)
    {
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin==null)
            return ServerResponse.createByErrorMessage("未登录");
        return sysNoticePublicService.GetNoticePublic(pn);
    }

    /**
     * public
     * 获取所有通知
     * @return
     */
    @RequestMapping(value = "get_msg", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_msg(HttpSession session, int pn)
    {
        UserInfo user = (UserInfo)session.getAttribute(Const.CURRENT_USER);
        if(user==null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return sysNoticePublicService.GetMsg(user.getUser_id(),pn);
    }
}
