package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {

    Integer checkNickName(String nickname);

    Integer Insert(String nickname);

    ServerResponse getUserInfo(int userid);

    void addUserArticlenum(Integer user_id);

    ServerResponse updateBgimg(Integer user_id, String imgFileName);

    ServerResponse updateAvatar(Integer user_id, String imgFileName);

    ServerResponse updateMusic(Integer user_id, String imgFileName);

    ServerResponse updateSignature(Integer user_id, String signature);

    ServerResponse searchUserByWordNoLogin(String search_word, int pn);

    ServerResponse searchUserByWordLogin(String search_word, int pn, Integer user_id);

    ServerResponse getUserFollows(Integer user_id, int pn);

    ServerResponse getUserFans(Integer user_id, int pn);

    ServerResponse getUserFansByNoLogin(String user_id, int pn);

    ServerResponse getUserFansByLogin(Integer user_id, String user_id1, int pn);

    ServerResponse getUserFollowsByNoLogin(String user_id, int pn);

    ServerResponse getUserFollowsByLogin(Integer user_id, String user_id1, int pn);

    ServerResponse getUser(Integer user_id);

    ServerResponse Save(User user);

    ServerResponse GetAdminUser(int pn);

    ServerResponse BanUser(int[] idArray);

    ServerResponse SearchByWord(String word,Integer status, int pn);

    ServerResponse GetSumUser();

    ServerResponse GetStaUser(String year);

    ServerResponse UnBanUser(int[] idArray);
}
