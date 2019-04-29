package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.UserAuths;
import com.wondering.vo.UserInfo;

public interface UserAuthsService {
    int checkIdentifier(String identifier, int identity_type);

    public UserInfo getUserInfo(String identifier, int identity_type);

    Integer Insert(UserAuths userAuths);

    ServerResponse LoginByPW(UserAuths userAuths);

    ServerResponse LoginByCode(UserAuths userAuths);

    Integer updatepwd(UserAuths userAuths);

    ServerResponse getUpdateUser(Integer user_id);
}
