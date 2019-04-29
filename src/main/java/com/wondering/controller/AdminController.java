package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    /**
     * 退出登录
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(HttpSession session)
    {
        session.removeAttribute(Const.CURRENT_ADMIN);
        return ServerResponse.createBySuccessMessage("退出成功");
    }

    /**
     * 管理登录
     * @return
     */
    @RequestMapping(value = "adminlogin", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse adminlogin(HttpSession session, String name, String password)
    {
        ServerResponse s = adminService.AdminLogin(name,password);
        if(s.isSuccess())
        {
            session.setAttribute(Const.CURRENT_ADMIN, s.getData());
            session.setMaxInactiveInterval(15*60);
            return ServerResponse.createBySuccessMessage("登录成功");
        }
        return ServerResponse.createByErrorMessage("账号或密码错误");
    }

    /**
     * 检查用户是否已登录
     * @return
     */
    @RequestMapping(value = "check", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse check(HttpSession session)
    {
        if(session.getAttribute(Const.CURRENT_ADMIN)==null)
            return ServerResponse.createByErrorMessage("未登录");
        return ServerResponse.createBySuccessMessage("已登录");
    }
}
