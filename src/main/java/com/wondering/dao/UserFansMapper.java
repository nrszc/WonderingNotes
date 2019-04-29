package com.wondering.dao;

import com.wondering.pojo.UserFans;
import com.wondering.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFansMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFans record);

    int insertSelective(UserFans record);

    UserFans selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFans record);

    int updateByPrimaryKey(UserFans record);

    Integer getUserFollow(@Param("follow_who")String follow_who, @Param("who_follow")String who_follow);

    Integer ToFollow(@Param("follow_who")String follow_who, @Param("who_follow")String who_follow);

    Integer CalcelFollow(@Param("follow_who")String follow_who, @Param("who_follow")String who_follow);

    List<UserVO> getUserFollows(Integer user_id);

    List<UserVO> getUserFans(Integer user_id);

    List<UserVO> getUserFansByLogin(@Param("user_id")Integer user_id, @Param("user_id1")String user_id1);

    List<UserVO> getUserFansByNoLogin(String user_id);

    List<UserVO> getUserFollowsByNoLogin(String user_id);

    List<UserVO> getUserFollowsByLogin(@Param("user_id")Integer user_id, @Param("user_id1")String user_id1);
}