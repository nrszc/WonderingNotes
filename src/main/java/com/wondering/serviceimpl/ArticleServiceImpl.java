package com.wondering.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.dao.ArticleMapper;
import com.wondering.pojo.Article;
import com.wondering.service.ArticleService;
import com.wondering.utils.DealWithFile;
import com.wondering.vo.ArticleVO;
import com.wondering.vo.StatisticsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper arcticleMapper;

    public Boolean insertnotes(Article article) {
        Integer result = 0;
        result = arcticleMapper.InsertArticle(article);
        if(result>0)
            return true;
        return false;
    }

    public ServerResponse getUserArticle(Integer user_id, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getUserArticle(user_id);
        System.out.println("1-------------------2");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserArticle1(Integer user_id, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getUserArticle1(user_id);
        System.out.println("1-------------------2");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getUserArticle2(Integer user_id, Integer user_id1, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getUserArticle2(user_id,user_id1);
        System.out.println("1-------------------2");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }


    public ServerResponse getAllPage(int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getAllPage();
        System.out.println("1-------------------2");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getAllPage1(Integer user_id, int pn) {
        System.out.println(user_id);
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getAllPage1(user_id);
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getCarousel() {
        List<Article> list = arcticleMapper.getCarousel();
        Collections.shuffle(list);
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse getNewPage(Integer user_id, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getNewPage(user_id);
        System.out.println("2");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getHotPage(int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getHotPage();
        System.out.println("3");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse getHotPage1(Integer user_id, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.getHotPage1(user_id);
        System.out.println("3");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse ShowArticle(String articleid) {
        ArticleVO articleVO = arcticleMapper.ShowArticle(articleid);
        articleVO.setArticle(DealWithFile.readToString(articleVO.getArticle()));
        return ServerResponse.createBySuccess(articleVO);
    }

    public ServerResponse ShowArticle1(Integer user_id, String articleid) {
        System.out.println(user_id+"       "+articleid);
        ArticleVO articleVO = arcticleMapper.ShowArticle1(user_id,articleid);
        articleVO.setArticle(DealWithFile.readToString(articleVO.getArticle()));
        System.out.println("nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
        return ServerResponse.createBySuccess(articleVO);
    }

    public ServerResponse searchTitleByWord(String search_word, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.searchTitleByWord(search_word);
        System.out.println("3");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse searchTitleByWord1(Integer user_id,String search_word, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.searchTitleByWord1(user_id,search_word);
        System.out.println("3");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse searchPlaceByWord(String search_word, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.searchPlaceByWord(search_word);
        System.out.println("3");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse searchPlaceByWord1(Integer user_id, String search_word, int pn) {
        //startPage是PageHelper的静态方法，参数1：默认开始页面，参数2：每页显示数据的条数
        PageHelper.startPage(pn, Const.articlecount);
        //从当前类下注入的业务层实现类userService中调用方法，该方法所在类利用注入的userDao来调用真正的查询方法查询数据库信息。
        List<ArticleVO> list = arcticleMapper.searchPlaceByWord1(user_id, search_word);
        System.out.println("3");
        //使用PageInfo包装查询页面，封装了详细的分页信息.第二个参数表示连续显示的页数
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse DeleteArticle(String articleid) {
        if(arcticleMapper.DeleteArticle(articleid)>0)
            return ServerResponse.createBySuccess("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    public ServerResponse GetSumArticle() {
        return ServerResponse.createBySuccess(arcticleMapper.GetSumArticle());
    }

    public ServerResponse GetStaArticle(String year) {
        List<StatisticsVO> list = arcticleMapper.GetStaArticle(year);
        return ServerResponse.createBySuccess(list);
    }

}
