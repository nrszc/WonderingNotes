package com.wondering.serviceimpl;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.dao.UserAuthsMapper;
import com.wondering.pojo.UserAuths;
import com.wondering.service.UserAuthsService;
import com.wondering.utils.MD5Util;
import com.wondering.vo.UserInfo;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userAuthsService")
public class UserAuthsServiceImpl implements UserAuthsService {

    @Autowired
    UserAuthsMapper userAuthsMapper;

    public int checkIdentifier(String identifier, int identity_type) {
        if(userAuthsMapper.check(identifier, Const.REGIST_DONE, identity_type)>0||userAuthsMapper.check(identifier, Const.REGIST_BAN, identity_type)>0) {
            System.out.println("gggggggggggggggggggggggggggggggggggg");
            return 1;
        }
        else {System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
            return 0;
        }
    }

    public UserInfo getUserInfo(String identifier, int identity_type){
        return userAuthsMapper.getUserInfo(identifier, identity_type);
    }


    public Integer Insert(UserAuths userAuths) {
        if(userAuths.getCredential()==null)
            userAuths.setCredential(null);
        else {
            String md5Password = MD5Util.MD5EncodeUtf8(userAuths.getCredential());
            System.out.println(md5Password);
            userAuths.setCredential(md5Password);
        }
        return userAuthsMapper.InsertUserAuths(userAuths);
    }

    public ServerResponse LoginByPW(UserAuths userAuths) {
        String md5Password = MD5Util.MD5EncodeUtf8(userAuths.getCredential());
        System.out.println(md5Password);
        userAuths.setCredential(md5Password);
        UserInfo user = userAuthsMapper.LoginByPW(userAuths);
        if (user == null) {
            return ServerResponse.createByErrorMessage("账号或密码错误");
        }
        if(user.getStatus() == 1)
            return ServerResponse.createByErrorMessage("该账号已被封禁");
        user.setCredential(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功", user);
    }

    public ServerResponse LoginByCode(UserAuths userAuths) {
        UserInfo user = userAuthsMapper.LoginByCode(userAuths);
        if (user == null) {
            return ServerResponse.createByErrorMessage("账号错误");
        }
        if(user.getStatus() == 1)
            return ServerResponse.createByErrorMessage("该账号已被封禁");
        user.setCredential(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功" ,user);
    }

    public Integer updatepwd(UserAuths userAuths) {
        String md5Password = MD5Util.MD5EncodeUtf8(userAuths.getCredential());
        System.out.println(md5Password);
        userAuths.setCredential(md5Password);
        return userAuthsMapper.updatepwd(userAuths);
    }

    public ServerResponse getUpdateUser(Integer user_id) {
        UserInfo user = userAuthsMapper.getUpdateUser(user_id);
        if (user==null)
            return ServerResponse.createByErrorMessage("找不到用户信息");
        return ServerResponse.createBySuccess(user);
    }


}
