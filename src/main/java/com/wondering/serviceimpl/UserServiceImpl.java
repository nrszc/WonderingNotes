package com.wondering.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.dao.UserFansMapper;
import com.wondering.dao.UserMapper;
import com.wondering.pojo.User;
import com.wondering.service.UserService;
import com.wondering.vo.ArticleVO;
import com.wondering.vo.StatisticsVO;
import com.wondering.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserFansMapper userFansMapper;


    public Integer checkNickName(String nickname) {
        return userMapper.checkNickName(nickname);
    }


    public Integer Insert(String nickname) {
        User user = new User();
        user.setNickname(nickname);
        if(userMapper.InsertUser(user)>0)
            return user.getId();
        else
            return -1;
    }

    public ServerResponse getUserInfo(int userid) {
        User user = userMapper.selectByPrimaryKey(userid);
        if (user != null) {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessage("用户ID不存在");
    }

    public void addUserArticlenum(Integer user_id) {
        userMapper.addUserArticlenum(user_id);
    }

    public ServerResponse updateBgimg(Integer user_id, String imgFileName) {
        Integer result;
        result = userMapper.updateBgimg(user_id, imgFileName);
        if(result>0)
            return ServerResponse.createBySuccess("更新封面图片成功");
        return ServerResponse.createByErrorMessage("更新封面图片失败");
    }

    public ServerResponse updateAvatar(Integer user_id, String imgFileName) {
        Integer result;
        result = userMapper.updateAvatar(user_id, imgFileName);
        if(result>0)
            return ServerResponse.createBySuccess("更新头像成功");
        return ServerResponse.createByErrorMessage("更新头像失败");
    }

    public ServerResponse updateMusic(Integer user_id, String imgFileName) {
        Integer result;
        result = userMapper.updateMusic(user_id, imgFileName);
        if(result>0)
            return ServerResponse.createBySuccess("更新音乐成功");
        return ServerResponse.createByErrorMessage("更新音乐失败");
    }

    public ServerResponse updateSignature(Integer user_id, String signature) {
        Integer result;
        result = userMapper.updateSignature(user_id, signature);
        if(result>0)
            return ServerResponse.createBySuccess("保存签名成功");
        return ServerResponse.createByErrorMessage("保存签名失败");
    }

    public ServerResponse searchUserByWordNoLogin(String search_word, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<UserVO> list = userMapper.searchUserByWordNoLogin(search_word);
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse searchUserByWordLogin(String search_word, int pn, Integer user_id) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<UserVO> list = userMapper.searchUserByWordLogin(search_word, user_id);
        System.out.println(search_word);
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserFollows(Integer user_id, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userFansMapper.getUserFollows(user_id);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserFans(Integer user_id, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userFansMapper.getUserFans(user_id);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserFansByNoLogin(String user_id, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userFansMapper.getUserFansByNoLogin(user_id);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserFansByLogin(Integer user_id, String user_id1, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userFansMapper.getUserFansByLogin(user_id,user_id1);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserFollowsByNoLogin(String user_id, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userFansMapper.getUserFollowsByNoLogin(user_id);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserFollowsByLogin(Integer user_id, String user_id1, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userFansMapper.getUserFollowsByLogin(user_id,user_id1);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUser(Integer user_id) {
        User user = userMapper.selectByPrimaryKey(user_id);
        if (user==null)
            return ServerResponse.createByErrorMessage("查找用户信息出错");
        else
            return ServerResponse.createBySuccess(user);
    }

    public ServerResponse Save(User user) {
         if (userMapper.Save(user) > 0)
             return ServerResponse.createBySuccessMessage("保存成功");
         return ServerResponse.createByErrorMessage("保存失败");
    }

    public ServerResponse GetAdminUser(int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userMapper.GetAdminUser();
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse BanUser(int[] idArray) {
        Integer result = userMapper.BanUser(idArray);
        if(result>0)
            return ServerResponse.createBySuccessMessage("封禁成功");
        return ServerResponse.createByErrorMessage("封禁失败");
    }

    public ServerResponse UnBanUser(int[] idArray) {
        Integer result = userMapper.UnBanUser(idArray);
        if(result>0)
            return ServerResponse.createBySuccessMessage("解封成功");
        return ServerResponse.createByErrorMessage("解封失败");
    }

    public ServerResponse SearchByWord(String word,Integer status,int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<UserVO> list = userMapper.SearchByWord(word, status);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse GetSumUser() {
        return ServerResponse.createBySuccess(userMapper.GetSumUser());
    }

    public ServerResponse GetStaUser(String year) {
        List<StatisticsVO> list = userMapper.GetStaUser(year);
        return ServerResponse.createBySuccess(list);
    }


}
