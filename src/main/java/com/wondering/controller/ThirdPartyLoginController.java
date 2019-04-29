package com.wondering.controller;

import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.PageFans;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.PageFansBean;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.javabeans.weibo.Company;
import com.qq.connect.oauth.Oauth;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.UserAuths;
import com.wondering.service.UserAuthsService;
import com.wondering.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import weibo4j.Users;
import weibo4j.model.User;
import weibo4j.model.WeiboException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Controller
@RequestMapping("/thirdpartylogin")
public class ThirdPartyLoginController {

    @Autowired
    UserAuthsService userAuthsService;

    @Autowired
    UserService userService;

    /**
     * QQ登录
     * @return
     */
    @RequestMapping(value = "QQLogin")
    public void QQLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        try {
            response.sendRedirect(new com.qq.connect.oauth.Oauth().getAuthorizeURL(request));
        } catch (QQConnectException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微博登录
     * @return
     */
    @RequestMapping(value = "weiboLogin")
    public void weiboLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("jjjjjjjjjjjjjj");
        weibo4j.Oauth oauth = new weibo4j.Oauth();
        String url = null;
        try {
            url = oauth.authorize("code",null);
        } catch (WeiboException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        response.sendRedirect(url);
    }

    /**
     * QQ登录回调
     * @return
     */
    @RequestMapping(value = "QQafterlogin")
    public void QQafterlogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");

        try {
            AccessToken accessTokenObj = (new com.qq.connect.oauth.Oauth()).getAccessTokenByRequest(request);

            String accessToken = null, openID = null;
            long tokenExpireIn = 0L;

            if (accessTokenObj.getAccessToken().equals("")) {
//                我们的网站被CSRF攻击了或者用户取消了授权   也有可能是因为刚调用QQ登录页面前的网站地址和回调网址不一致
//                做一些数据统计工作

                response.sendRedirect("/view/common/error.html");
            } else {
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();

                request.getSession().setAttribute("demo_access_token", accessToken);
                request.getSession().setAttribute("demo_token_expirein", String.valueOf(tokenExpireIn));
                System.out.println("----------------------------------------------");
                // 利用获取到的accessToken 去获取当前用的openid -------- start
                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();

                System.out.println(openID+"----------------------------------------------");

                //已注册用户直接登录
                if(userAuthsService.checkIdentifier(openID, Const.QQ)>0) {
                    request.getSession().setMaxInactiveInterval(30*60);
                    request.getSession().setAttribute(Const.CURRENT_USER, userAuthsService.getUserInfo(openID, Const.QQ));
                    System.out.println("登录成功");
                    response.sendRedirect("/view/user/index.html");
                }else {
                    //未注册用户执行下面语句
                    UserAuths userAuths = new UserAuths();
                    userAuths.setIdentifier(openID);
                    userAuths.setIdentity_type((byte) Const.QQ);
                    UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                    UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

                    if (userInfoBean.getRet() == 0) {
                        String nickname = userInfoBean.getNickname() + "_" + (int) (Math.random() * 900 + 100);
                        while (userService.checkNickName(nickname) > 0) {
                            nickname = userInfoBean.getNickname() + "_" + (int) (Math.random() * 900 + 100);
                        }
                        System.out.println(nickname);
                        Integer userid = userService.Insert(nickname);
                        if (userid < 0) {
                            System.out.println("插入User失败");
                            response.sendRedirect("/view/common/error.html");
                        }
                        userAuths.setUser_id(userid);
                        if (userAuthsService.Insert(userAuths) > 0) {
                            request.getSession().setMaxInactiveInterval(30*60);
                            request.getSession().setAttribute(Const.CURRENT_USER, userAuthsService.getUserInfo(openID, Const.QQ));
                            System.out.println("注册并且登录成功");
                            response.sendRedirect("/view/user/index.html");
                        } else {
                            System.out.println("插入UserAuths失败");
                            response.sendRedirect("/view/common/error.html");
                        }
                    } else {
                        response.sendRedirect("/view/common/error.html");
                        //out.println("很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
                    }
                }

            }
        } catch (QQConnectException e) {
            response.sendRedirect("/view/common/error.html");
        }


    }

    /**
     * 微博登录回调
     * @return
     */
    @RequestMapping(value = "weiboafterlogin")
    public void weiboafterlogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = request.getParameter("code");
        try {
            weibo4j.Oauth oauth = new weibo4j.Oauth();
            String token = oauth.getAccessTokenByCode(code).toString();
            String str[] = token.split(",");
            String accessToken = str[0].split("=")[1];
            String str1[] = str[3].split("]");
            String uid = str1[0].split("=")[1];
            Users um = new Users(accessToken);
            User user = um.showUserById(uid);
            System.out.println(user);

            //已注册用户直接登录
            if (userAuthsService.checkIdentifier(user.getId(), Const.WEIBO) > 0) {
                request.getSession().setMaxInactiveInterval(30*60);
                request.getSession().setAttribute(Const.CURRENT_USER, userAuthsService.getUserInfo(user.getId(), Const.WEIBO));
                System.out.println(userAuthsService.getUserInfo(user.getId(), Const.WEIBO).getUser_id());
                System.out.println("登录成功");
                response.sendRedirect("/view/user/index.html");
            }else {
                //未注册用户执行下面语句
                UserAuths userAuths = new UserAuths();
                userAuths.setIdentifier(user.getId());
                userAuths.setIdentity_type((byte) Const.WEIBO);

                String nickname = user.getName() + "_" + (int) (Math.random() * 900 + 100);
                while (userService.checkNickName(nickname) > 0) {
                    nickname = user.getName() + "_" + (int) (Math.random() * 900 + 100);
                }
                System.out.println(nickname);
                Integer userid = userService.Insert(nickname);
                if (userid < 0) {
                    System.out.println("插入User失败");
                    response.sendRedirect("/view/common/error.html");
                }
                userAuths.setUser_id(userid);
                if (userAuthsService.Insert(userAuths) > 0) {
                    request.getSession().setMaxInactiveInterval(30*60);
                    request.getSession().setAttribute(Const.CURRENT_USER, userAuthsService.getUserInfo(user.getId(), Const.WEIBO));
                    System.out.println("注册并且登录成功");
                    response.sendRedirect("/view/user/index.html");
                } else {
                    System.out.println("插入UserAuths失败");
                    response.sendRedirect("/view/common/error.html");
                }
            }
        }
        catch (WeiboException e)
        {
            System.out.println("这里------------------");
            response.sendRedirect("/view/common/error.html");
        }
    }


}
