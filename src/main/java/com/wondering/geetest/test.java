package com.wondering.geetest;

import com.wondering.common.Const;
import com.wondering.service.UserAuthsService;
import com.wondering.service.UserService;
import com.wondering.serviceimpl.UserAuthsServiceImpl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

    public static void main(String args[]) {
        UserAuthsService userAuthsService = new UserAuthsServiceImpl();
        System.out.println(Const.QQ);
        if(userAuthsService.checkIdentifier("ijijin", Const.QQ)>0) {
            System.out.println("登录成功");
        }
    }
}
