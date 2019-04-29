package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.Admin;
import com.wondering.pojo.User;
import com.wondering.service.UserService;
import com.wondering.utils.DealWithFile;
import com.wondering.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * index.html
     * 退出登录
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse logout(HttpSession session)
    {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("退出成功");
    }

    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping(value = "getInfo", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse getInfo(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return ServerResponse.createBySuccess(user.getIdentity_type());
    }

    /**
     * setting.html
     * 获取登录用户个人信息
     * @return
     */
    @RequestMapping(value = "get_user", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_user(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return userService.getUser(user.getUser_id());
    }

    /**
     * setting.html
     * 检查昵称的唯一性
     * @return
     */
    @RequestMapping(value = "check_nickname", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse check_nickname(HttpSession session, String nickname)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        if(user.getNickname().equals(nickname))
            return ServerResponse.createBySuccessMessage("该昵称可以用");
        if(userService.checkNickName(nickname)>0) {
            return ServerResponse.createByErrorMessage("该昵称已存在");
        }
        return ServerResponse.createBySuccessMessage("该昵称可以用");
    }

    /**
     * setting.html
     * 检查昵称的唯一性
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse save(HttpSession session, User user)
    {
        UserInfo userinfo = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (userinfo == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        user.setId(userinfo.getUser_id());
        System.out.println(userinfo.getNickname());
        if(user.getNickname().equals(userinfo.getNickname())) {
            if(userService.Save(user).isSuccess())
            {
                userinfo.setNickname(user.getNickname());
                session.setAttribute(Const.CURRENT_USER, userinfo);
                return ServerResponse.createBySuccessMessage("保存成功");
            }
            return ServerResponse.createByErrorMessage("保存失败");
        }
        if(userService.checkNickName(user.getNickname())>0) {
            return ServerResponse.createByErrorMessage("该昵称已存在");
        }
        if(userService.Save(user).isSuccess())
        {
            userinfo.setNickname(user.getNickname());
            session.setAttribute(Const.CURRENT_USER, userinfo);
            return ServerResponse.createBySuccessMessage("保存成功");
        }
        return ServerResponse.createByErrorMessage("保存失败");
    }

    /**
     * 获取登录用户个人信息
     * @return
     */
    @RequestMapping(value = "get_userstate", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userstate(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return ServerResponse.createBySuccess("用户已登录");
    }

    /**
     * 获取登录用户个人信息
     * @return
     */
    @RequestMapping(value = "get_userinfo", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userinfo(HttpSession session)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return userService.getUserInfo(user.getUser_id());
    }

    /**
     * 获取用户个人信息
     * @return
     */
    @RequestMapping(value = "get_userinfo1", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userinfo1( String user_id)
    {
        return userService.getUserInfo(Integer.parseInt(user_id));
    }

    /**
     * 更新个人用户封面图片
     * @return
     */
    @RequestMapping(value = "update_bgimg", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_bgimg(HttpSession session, MultipartFile file, HttpServletRequest request)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        if(file.getSize()==0) {
            return ServerResponse.createByErrorMessage("未上传图片");
        }
        String imgFileName = null;
        try {
            imgFileName = DealWithFile.uploadFile1(request,file,Const.BGIMG1,Const.BGIMG2, user.getUser_id());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userService.updateBgimg(user.getUser_id(), imgFileName);
    }

    /**
     * 更新个人头像
     * @return
     */
    @RequestMapping(value = "update_avatar", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_avatar(HttpSession session, MultipartFile avatar, HttpServletRequest request)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        if(avatar.getSize()==0) {
            return ServerResponse.createByErrorMessage("未上传图片");
        }
        String imgFileName = null;
        try {
            imgFileName = DealWithFile.uploadFile1(request,avatar,Const.AVATAR1, Const.AVATAR2, user.getUser_id());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userService.updateAvatar(user.getUser_id(), imgFileName);
    }

    /**
     * 更新个人音乐
     * @return
     */
    @RequestMapping(value = "update_music", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_music(HttpSession session, MultipartFile music, HttpServletRequest request)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        if(music.getSize()==0) {
            return ServerResponse.createByErrorMessage("未上传音乐");
        }
        String imgFileName = null;
        try {
            imgFileName = DealWithFile.uploadFile1(request,music,Const.MUSIC1, Const.MUSIC2, user.getUser_id());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(userService.updateMusic(user.getUser_id(), imgFileName).isSuccess())
            return ServerResponse.createBySuccess("更新音乐成功" , imgFileName);
        return ServerResponse.createByErrorMessage("更新音乐失败");
    }

    /**
     * 更新个人签名
     * @return
     */
    @RequestMapping(value = "update_signature", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse update_signature(HttpSession session, String signature)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        if(userService.updateSignature(user.getUser_id(), signature).isSuccess())
            return ServerResponse.createBySuccess("保存签名成功");
        return ServerResponse.createByErrorMessage("保存签名失败");
    }

    /**
     * 搜索找人
     * @return
     */
    @RequestMapping(value = "search_userbyword", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse search_userbyword(HttpSession session, String search_word, int pn)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return userService.searchUserByWordNoLogin(search_word, pn);
        }
        return userService.searchUserByWordLogin(search_word, pn, user.getUser_id());
    }

    /**
     * 查看关注人
     * @return
     */
    @RequestMapping(value = "get_userfollows", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userfollows(HttpSession session, int pn)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.getUserFollows(user.getUser_id(), pn);
    }

    /**
     * 查看关注人1
     * @return
     */
    @RequestMapping(value = "get_userfollows1", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userfollows1(HttpSession session,String user_id, int pn)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return userService.getUserFollowsByNoLogin(user_id,pn);
        }
        return userService.getUserFollowsByLogin(user.getUser_id(),user_id, pn);
    }

    /**
     * 查看粉丝
     * @return
     */
    @RequestMapping(value = "get_userfans", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userfans(HttpSession session,String user_id, int pn)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.getUserFans(user.getUser_id(), pn);
    }

    /**
     * 查看粉丝1
     * @return
     */
    @RequestMapping(value = "get_userfans1", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userfans1(HttpSession session,String user_id, int pn)
    {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return userService.getUserFansByNoLogin(user_id,pn);
        }
        return userService.getUserFansByLogin(user.getUser_id(),user_id, pn);
    }


    ///admin
    /**
     * 查看所有用户
     * @return
     */
    @RequestMapping(value = "get_adminuser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_adminuser(HttpSession session, int pn)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null) {
            return ServerResponse.createByErrorMessage("未登录");
        }
        return userService.GetAdminUser(pn);
    }

    /**
     * 封禁用户
     * @return
     */
    @RequestMapping(value = "ban_user", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse ban_user(HttpSession session,@RequestParam(value = "checkID[]") String[] checkID)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        int [] idArray = new int[checkID.length];
        for (int i=0;i<checkID.length;i++){         //把或取到的String数组转换为int数组
            idArray[i] = Integer.parseInt(checkID[i]);
        }
        return userService.BanUser(idArray);
    }

    /**
     * 解封用户
     * @return
     */
    @RequestMapping(value = "unban_user", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse unban_user(HttpSession session,@RequestParam(value = "checkID[]") String[] checkID)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        int [] idArray = new int[checkID.length];
        for (int i=0;i<checkID.length;i++){         //把或取到的String数组转换为int数组
            idArray[i] = Integer.parseInt(checkID[i]);
        }
        return userService.UnBanUser(idArray);
    }

    /**
     * 查找用户
     * @return
     */
    @RequestMapping(value = "search_byword", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse search_byword(HttpSession session,String word,Integer status, int pn)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return userService.SearchByWord(word,status,pn);
    }

    /**
     * 统计用户总数
     * @return
     */
    @RequestMapping(value = "get_sumuser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_sumuser(HttpSession session)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return userService.GetSumUser();
    }

    /**
     * 按年统计每月用户增长总数
     * @return
     */
    @RequestMapping(value = "get_stauser", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_stauser(HttpSession session, String year)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return userService.GetStaUser(year);
    }


}
