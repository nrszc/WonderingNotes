package com.wondering.serviceimpl;

import com.wondering.common.ServerResponse;
import com.wondering.dao.UserFansMapper;
import com.wondering.dao.UserMapper;
import com.wondering.service.UserFansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userFansService")
public class UserFansServiceImpl implements UserFansService {

    @Autowired
    UserFansMapper userFansMapper;

    @Autowired
    UserMapper userMapper;

    public ServerResponse getUserFollow(String follow_who, String who_follow) {
        if(userFansMapper.getUserFollow(follow_who, who_follow)>0)
            return ServerResponse.createBySuccess("已关注", true);
        return ServerResponse.createBySuccess("未关注", false);
    }

    public ServerResponse ToFollow(String follow_who, String who_follow) {
        if(userFansMapper.getUserFollow(follow_who, who_follow)>0)
            return ServerResponse.createBySuccess("已关注", true);
        if(userFansMapper.ToFollow(follow_who, who_follow)>0) {
            if(userMapper.addFans(follow_who)>0&&userMapper.addFollows(who_follow)>0)
               return ServerResponse.createBySuccess("关注成功", true);
        }
        return ServerResponse.createBySuccess("关注失败", false);
    }

    public ServerResponse CancelFollow(String follow_who, String who_follow) {
        if(userFansMapper.getUserFollow(follow_who, who_follow)>0){
            if(userFansMapper.CalcelFollow(follow_who, who_follow)>0)
                if(userMapper.subFans(follow_who)>0&&userMapper.subFollows(who_follow)>0)
                  return ServerResponse.createBySuccess("取消关注成功", false);
        }
        return ServerResponse.createBySuccess("用户本身没有关注该用户,不能取消", true);
    }
}
