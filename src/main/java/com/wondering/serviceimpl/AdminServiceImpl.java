package com.wondering.serviceimpl;

import com.wondering.common.ServerResponse;
import com.wondering.dao.AdminMapper;
import com.wondering.pojo.Admin;
import com.wondering.service.AdminService;
import com.wondering.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    public ServerResponse AdminLogin(String name, String password) {
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        Admin admin = new Admin();
        admin.setName(name);
        admin.setPassword(md5Password);
        Admin admin1 = adminMapper.AdminLogin(admin);
        admin.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        if(admin1==null)
            return ServerResponse.createByErrorMessage("账号或密码错误");
        return ServerResponse.createBySuccess("登录成功",admin1);
    }
}
