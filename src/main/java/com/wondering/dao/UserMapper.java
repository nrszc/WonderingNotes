package com.wondering.dao;

import com.wondering.pojo.User;
import com.wondering.vo.ArticleVO;
import com.wondering.vo.StatisticsVO;
import com.wondering.vo.UserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer checkNickName(String nickname);

    Integer InsertUser(User user);

    void addUserArticlenum(Integer user_id);

    Integer updateBgimg(@Param("user_id")Integer user_id, @Param("bgimg")String bgimg);

    Integer updateAvatar(@Param("user_id")Integer user_id, @Param("avatar")String avatar);

    Integer updateMusic(@Param("user_id")Integer user_id, @Param("music")String music);

    Integer updateSignature(@Param("user_id")Integer user_id, @Param("signature")String signature);

    List<UserVO> searchUserByWordNoLogin(String search_word);

    List<UserVO> searchUserByWordLogin(@Param("search_word")String search_word, @Param("user_id")Integer user_id);

    Integer addFans(String follow_who);

    Integer addFollows(String who_follow);

    Integer subFans(String follow_who);

    Integer subFollows(String who_follow);

    Integer Save(User user);

    List<UserVO> GetAdminUser();

    Integer BanUser(int[] idArray);

    Integer UnBanUser(int[] idArray);

    List<UserVO> SearchByWord(@Param("word")String word, @Param("status")Integer status);

    Integer GetSumUser();

    List<StatisticsVO> GetStaUser(String year);

}