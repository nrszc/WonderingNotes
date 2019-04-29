package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.Regex;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.UserAuths;
import com.wondering.service.UserAuthsService;
import com.wondering.service.UserService;
import com.wondering.submail.MailSend;
import com.wondering.submail.MessageXsend;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Controller
@RequestMapping("/userauths")
public class UserAuthsController {

    @Autowired
    UserService userService;

    @Autowired
    UserAuthsService userAuthsService;

    /**
     * 注册给手机发送验证码
     * @return
     */
    @RequestMapping(value = "send_phonecode", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse send_phonecode(HttpSession session,@RequestParam(value = "phone") String phone)
    {
        if(userAuthsService.checkIdentifier(phone, Const.PHONE)>0)
            return ServerResponse.createByErrorMessage("该手机号已被注册");
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000); //生成6位验证码
        MessageXsend messageXsend = new MessageXsend();
        messageXsend.SendPhoneCode(verifyCode,phone);
        session.setMaxInactiveInterval(10*60);
        session.setAttribute("phoneCode", verifyCode);
        session.setAttribute("phone", phone);
        System.out.println(verifyCode);
        System.out.println(phone);
        return ServerResponse.createBySuccessMessage("发送手机验证码成功");
    }

    /**
     * 注册给邮箱发送验证码
     * @return
     */
    @RequestMapping(value = "send_emailcode", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse send_emailcode(HttpSession session,@RequestParam(value = "email") String email) throws UnsupportedEncodingException {
        if(userAuthsService.checkIdentifier(email, Const.EMAIL)>0)
            return ServerResponse.createByErrorMessage("该邮箱已被注册");
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000); //生成6位验证码
        MailSend mailSend = new MailSend();
        System.out.println("ttt"+email+"ttt");
        mailSend.SendEmailCode(verifyCode, email);
        session.setMaxInactiveInterval(10*60);
        session.setAttribute("emailCode", verifyCode);
        session.setAttribute("email", email);
        System.out.println(verifyCode);
        System.out.println(email);
        return ServerResponse.createBySuccessMessage("发送邮箱验证码成功");
    }

    /**
     * 检验手机验证码是否正确
     * 如果正确，再检查该手机号是否被注册
     * 否则，提示用户错误
     * @return
     */
    @RequestMapping(value = "regist_phonefirst")
    @ResponseBody
    public ServerResponse regist_phonefirst(HttpSession session,@RequestParam(value = "phone") String phone,@RequestParam(value = "code") String code){
        String verifyCode = (String)session.getAttribute("phoneCode");
        String sphone = (String)session.getAttribute("phone");
        if((!(verifyCode.equals(code)))||(!(phone.equals(sphone))))
            return ServerResponse.createByErrorMessage("验证码错误");
        if(userAuthsService.checkIdentifier(phone, Const.PHONE)>0)
            return ServerResponse.createByErrorCodeMessage(2,  "该手机号已被注册");
        return ServerResponse.createBySuccessMessage("这个手机号可以进行注册");
    }

    /**
     * 检验邮箱验证码是否正确
     * 如果正确，再检查该邮箱是否被注册
     * 否则，提示用户错误
     * @return
     */
    @RequestMapping(value = "regist_emailfirst")
    @ResponseBody
    public ServerResponse regist_emailfirst(HttpSession session,@RequestParam(value = "email") String email,@RequestParam(value = "code") String code) throws UnsupportedEncodingException {
        String verifyCode = (String)session.getAttribute("emailCode");
        String semail = (String)session.getAttribute("email");
        if((!(verifyCode.equals(code)))||(!(email.equals(semail))))
            return ServerResponse.createByErrorMessage("验证码错误");
        if(userAuthsService.checkIdentifier(email, Const.EMAIL)>0)
            return ServerResponse.createByErrorCodeMessage(2,  "该邮箱已被注册");
        return ServerResponse.createBySuccessMessage("这个邮箱可以进行注册");
    }

    /**
     * 检验nickname是否已存在
     * 如果正确，进行注册
     * 否则，提示用户错误
     * @return
     */
    @RequestMapping(value = "regist_phonesecond")
    @ResponseBody
    public ServerResponse regist_phonesecond(@RequestParam(value = "phone") String phone,@RequestParam(value = "nickname") String nickname, @RequestParam(value = "password") String password) {
        if(userService.checkNickName(nickname)>0)
            return ServerResponse.createByErrorMessage("该昵称已被注册，请另起昵称");
        Integer userid = userService.Insert(nickname);
        System.out.println(userid+"-------------------------------");
        if(userid<0)
            return ServerResponse.createByErrorMessage("注册失败");
        UserAuths userAuths = new UserAuths();
        userAuths.setUser_id(userid);
        userAuths.setIdentifier(phone);
        userAuths.setIdentity_type((byte) Const.PHONE);
        userAuths.setCredential(password);
        if(userAuthsService.Insert(userAuths)>0)
            return ServerResponse.createBySuccessMessage("插入成功");
        return ServerResponse.createByErrorCodeMessage(2,"插入失败");
    }

    /**
     * 检验nickname是否已存在
     * 如果正确，进行注册
     * 否则，提示用户错误
     * @return
     */
    @RequestMapping(value = "regist_emailsecond")
    @ResponseBody
    public ServerResponse regist_emailsecond(@RequestParam(value = "email") String email,@RequestParam(value = "nickname") String nickname, @RequestParam(value = "password") String password) {
        if(userService.checkNickName(nickname)>0)
            return ServerResponse.createByErrorMessage("该昵称已被注册，请另起昵称");
        Integer userid = userService.Insert(nickname);
        System.out.println(userid+"++++++++++");
        if(userid<0)
            return ServerResponse.createByErrorMessage("注册失败");
        UserAuths userAuths = new UserAuths();
        userAuths.setUser_id(userid);
        userAuths.setIdentifier(email);
        userAuths.setIdentity_type((byte) Const.EMAIL);
        userAuths.setCredential(password);
        if(userAuthsService.Insert(userAuths)>0)
            return ServerResponse.createBySuccessMessage("插入成功");
        return ServerResponse.createByErrorCodeMessage(2,"插入失败");

    }

    /**
     * 登录给邮箱发送验证码
     * @return
     */
    @RequestMapping(value = "send_emailcodeLogin", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse send_emailcodeLogin(HttpSession session,@RequestParam(value = "email") String email) throws UnsupportedEncodingException {
        if(userAuthsService.checkIdentifier(email, Const.EMAIL)==0)
            return ServerResponse.createByErrorMessage("该邮箱未注册");
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000); //生成6位验证码
        MailSend mailSend = new MailSend();
        System.out.println("ttt"+email+"ttt");
        mailSend.SendEmailCode(verifyCode, email);
        session.setMaxInactiveInterval(10*60);
        session.setAttribute("loginCode", verifyCode);
        session.setAttribute("loginidentifier", email);
        System.out.println(verifyCode);
        System.out.println(email);
        return ServerResponse.createBySuccessMessage("发送邮箱验证码成功");
    }

    /**
     * 登录给手机发送验证码
     * @return
     */
    @RequestMapping(value = "send_phonecodeLogin", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse send_phonecodeLogin(HttpSession session,@RequestParam(value = "phone") String phone) throws UnsupportedEncodingException {
        if(userAuthsService.checkIdentifier(phone, Const.PHONE)==0)
            return ServerResponse.createByErrorMessage("该手机未注册");
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000); //生成6位验证码
        MessageXsend messageXsend = new MessageXsend();
        messageXsend.SendPhoneCode(verifyCode,phone);
        session.setMaxInactiveInterval(10*60);
        session.setAttribute("loginCode", verifyCode);
        session.setAttribute("loginidentifier", phone);
        System.out.println(verifyCode);
        System.out.println(phone);
        return ServerResponse.createBySuccessMessage("发送手机验证码成功");
    }


    /**
     * updatepw.html
     * 修改密码给手机或邮箱发送验证码
     * @return
     */
    @RequestMapping(value = "send_code", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse send_code(HttpSession session,@RequestParam(value = "identifier") String identifier) throws UnsupportedEncodingException {
        System.out.println("---------------------");
        if(Regex.isPhoneValidateCode(identifier))
        {
            //如果该手机号未注册
            if(userAuthsService.checkIdentifier(identifier, Const.PHONE)==0)
                return ServerResponse.createByErrorMessage("请填写正确的手机号或邮箱");
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000); //生成6位验证码
            System.out.println(verifyCode);
            MessageXsend messageXsend = new MessageXsend();
            messageXsend.SendPhoneCode(verifyCode,identifier);
            session.setMaxInactiveInterval(10*60);
            session.setAttribute("updatepwcode", verifyCode);
            session.setAttribute("identifier", identifier);
            return ServerResponse.createBySuccessMessage("验证码已发送");
        }
        else if(Regex.isEmailValidateCode(identifier))
        {
            //如果该手机号未注册
            if(userAuthsService.checkIdentifier(identifier, Const.EMAIL)==0)
                return ServerResponse.createByErrorMessage("请填写正确的手机号或邮箱");
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000); //生成6位验证码
            System.out.println(verifyCode);
            MailSend mailSend = new MailSend();
            System.out.println("ttt"+identifier+"ttt");
            mailSend.SendEmailCode(verifyCode, identifier);
            session.setMaxInactiveInterval(10*60);
            session.setAttribute("updatepwcode", verifyCode);
            session.setAttribute("identifier", identifier);
            return ServerResponse.createBySuccessMessage("验证码已发送");
        }
        else
            return ServerResponse.createByErrorMessage("请填写正确的手机号或邮箱");
    }

    /**
     * updatepw.html
     * 检验忘记密码验证码是否正确
     * 如果正确，跳到下一步
     * 否则，提示用户错误
     * @return
     */
    @RequestMapping(value = "updatepw_first", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatepw_first(HttpSession session,@RequestParam(value = "identifier") String identifier,@RequestParam(value = "code") String code){
        String verifyCode = (String)session.getAttribute("updatepwcode");
        String identifier1 = (String)session.getAttribute("identifier");
        if((!(verifyCode.equals(code)))||(!(identifier.equals(identifier1))))
            return ServerResponse.createByErrorMessage("验证码错误");
        return ServerResponse.createBySuccessMessage("校验正确");
    }

    /**
     * updatepw.html
     * 进行更改密码
     * @return
     */
    @RequestMapping(value = "updatepwd_second", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse updatepwd_second(@RequestParam(value = "identifier") String identifier,@RequestParam(value = "password") String password){
        UserAuths userAuths = new UserAuths();
        userAuths.setIdentifier(identifier);
        userAuths.setCredential(password);
        if(userAuthsService.updatepwd(userAuths)>0)
            return ServerResponse.createBySuccessMessage("更新成功");
        return ServerResponse.createByErrorMessage("更新失败");

    }

    /**
     * updatepw1.html
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "get_updateuser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_updateuser(HttpSession session){
       UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
       if(user==null)
           return ServerResponse.createByErrorMessage("用户未登陆");
       return userAuthsService.getUpdateUser(user.getUser_id());
    }

}
