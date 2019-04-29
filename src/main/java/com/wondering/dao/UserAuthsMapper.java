package com.wondering.dao;

import com.wondering.pojo.UserAuths;
import com.wondering.vo.UserInfo;
import org.apache.ibatis.annotations.Param;

public interface UserAuthsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserAuths record);

    int insertSelective(UserAuths record);

    UserAuths selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserAuths record);

    int updateByPrimaryKey(UserAuths record);

    Integer check(@Param("identifier") String identifier, @Param("status") int status, @Param("identity_type") int identity_type);

    Integer InsertUserAuths(UserAuths userAuths);

    UserInfo LoginByPW(UserAuths userAuths);

    UserInfo LoginByCode(UserAuths userAuths);

    Integer updatepwd(UserAuths userAuths);

    UserInfo getUserInfo(@Param("identifier")String identifier, @Param("identity_type")int identity_type);

    UserInfo getUpdateUser(Integer user_id);
}