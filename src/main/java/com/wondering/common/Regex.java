package com.wondering.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    /**
     * 邮箱
     */
    public static final String REGEX_EMAIL = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$";
    /**
     * 手机号码
     */
    public static final String REGEX_PHONE = "^1[34578]\\d{9}$";

    /**
     * 校验手机
     * @param phone
     * @return 符合正则表达式返回true，否则返回false
     * @author lqyao
     * @since 2015-09-24
     */
    public static final boolean isPhoneValidateCode(String phone){
        Pattern regex = Pattern.compile(REGEX_PHONE);
        Matcher matcher = regex.matcher(phone);
        return matcher.matches();
    }

    /**
     * 校验邮箱
     * @param email
     * @return 符合正则表达式返回true，否则返回false
     * @author lqyao
     * @since 2015-09-24
     */
    public static final boolean isEmailValidateCode(String email){
        Pattern regex = Pattern.compile(REGEX_EMAIL);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

}
