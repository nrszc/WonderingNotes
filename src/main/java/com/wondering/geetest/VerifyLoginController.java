package com.wondering.geetest;

import com.wondering.common.*;
import com.wondering.pojo.UserAuths;
import com.wondering.service.UserAuthsService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/verifylogin")
public class VerifyLoginController {

    @Autowired
    UserAuthsService userAuthsService;

    /**
     * 二次验证（API2），包括正常流程和宕机情况
     * @return
     */
    @RequestMapping(value = "verify")
    @ResponseBody
    public ServerResponse verify(HttpServletRequest request, HttpSession session) throws IOException {
        GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(), GeetestConfig.getGeetest_key(),
                GeetestConfig.isnewfailback());
        System.out.println(gtSdk);

        String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
        String validate = request.getParameter(GeetestLib.fn_geetest_validate);
        String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);


        //从session中获取gt-server状态
        int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //从session中获取userid
        String userid = (String)request.getSession().getAttribute("userid");

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("user_id", userid); //网站用户id
        param.put("client_type", "web"); //web:电脑上的浏览器；h5:手机上的浏览器，包括移动应用内完全内置的web_view；native：通过原生SDK植入APP应用的方式
        param.put("ip_address", "127.0.0.1"); //传输用户请求验证时所携带的IP

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证

            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证

            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }

        if (gtResult == 1) {
            // 验证成功
            String identifier = request.getParameter("identifier");
            String credential = request.getParameter("credential");
            Byte logintype = Byte.valueOf(request.getParameter("logintype"));
            UserAuths userAuths = new UserAuths();
            userAuths.setIdentifier(identifier);
            userAuths.setCredential(credential);
            String checkemail = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
            String checkphone = "^1[34578]\\d{9}$";
            ServerResponse userinfo;
            if(logintype == 0) {
                //用于判断手机或邮箱
                if (Regex.isPhoneValidateCode(identifier)) //手机登录
                {
                    userAuths.setIdentity_type((byte) Const.PHONE);
                    userinfo = userAuthsService.LoginByPW(userAuths);
                    if(userinfo.isSuccess()) {
                        session.setMaxInactiveInterval(30*60);
                        session.setAttribute(Const.CURRENT_USER, userinfo.getData());
                    }
                    return userinfo;
                }
                else{
                    if(Regex.isEmailValidateCode(identifier)) //邮箱登录
                    {
                        userAuths.setIdentity_type((byte) Const.EMAIL);
                        userinfo = userAuthsService.LoginByPW(userAuths);
                        if(userinfo.isSuccess()) {
                            session.setMaxInactiveInterval(30*60);
                            session.setAttribute(Const.CURRENT_USER, userinfo.getData());
                        }
                        return userinfo;
                    }
                    else
                        return ServerResponse.createByErrorMessage("账号或密码错误");
                }
            }
            else{
                //用于判断手机或邮箱
                System.out.println((String)session.getAttribute("loginCode")+(String)session.getAttribute("loginidentifier"));
                if((!(credential.equals((String)session.getAttribute("loginCode"))))||(!(identifier.equals((String)session.getAttribute("loginidentifier")))))
                    return ServerResponse.createByErrorMessage("验证码错误");
                if (Regex.isPhoneValidateCode(identifier))  //手机登录
                {
                    userAuths.setIdentity_type((byte) Const.PHONE);
                    userinfo = userAuthsService.LoginByCode(userAuths);
                    if(userinfo.isSuccess()) {
                        session.setMaxInactiveInterval(30*60);
                        session.setAttribute(Const.CURRENT_USER, userinfo.getData());
                    }
                    return userinfo;
                }
                else{
                    if(Regex.isEmailValidateCode(identifier))  //邮箱登录
                    {
                        userAuths.setIdentity_type((byte) Const.EMAIL);
                        userinfo = userAuthsService.LoginByCode(userAuths);
                        if(userinfo.isSuccess()) {
                            session.setMaxInactiveInterval(30*60);
                            session.setAttribute(Const.CURRENT_USER, userinfo.getData());
                        }
                        return userinfo;
                    }
                    else
                        return ServerResponse.createByErrorMessage("账号或密码错误");
                }
            }
//            JSONObject data = new JSONObject();
//            try {
//                data.put("status", "success");
//                data.put("version", gtSdk.getVersionInfo());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            PrintWriter out = response.getWriter();
//            out.println(data.toString());
        }
        else {
            // 验证失败
            return ServerResponse.createBySuccessMessage("验证错误");
//            JSONObject data = new JSONObject();
//            try {
//                data.put("status", "fail");
//                data.put("version", gtSdk.getVersionInfo());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            PrintWriter out = response.getWriter();
//            out.println(data.toString());
        }



    }
}
