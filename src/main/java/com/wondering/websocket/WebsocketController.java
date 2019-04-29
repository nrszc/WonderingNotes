package com.wondering.websocket;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;


@Controller
public class WebsocketController {
    @Bean//这个注解会从Spring容器拿出Bean
    public SpringWebSocketHandler infoHandler() {
        return new SpringWebSocketHandler();
    }

    @RequestMapping("/websocket/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        System.out.println(username+"登录");
        request.getSession().setAttribute("SESSION_USERNAME", username);
        response.sendRedirect("/view/user/demo1.html");
        //return new ModelAndView("user/test");
    }

//    @RequestMapping("/websocket/send")
//    @ResponseBody
//    public String send(HttpServletRequest request) {
//        String username = request.getParameter("username");
//        infoHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
//        return null;
//    }
}