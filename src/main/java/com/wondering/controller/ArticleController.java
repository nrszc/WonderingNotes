package com.wondering.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.Admin;
import com.wondering.pojo.Article;
import com.wondering.service.ArticleService;
import com.wondering.service.UserService;
import com.wondering.utils.DealWithFile;
import com.wondering.vo.ArticleVO;
import com.wondering.vo.UserInfo;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    DealWithFile dealWithFile;

    /**
     * 发表游记
     * @return
     */
    @RequestMapping(value = "submit_notes", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse submit_notes(Article article, MultipartFile file, HttpServletRequest request) throws IOException {
        UserInfo user = (UserInfo) request.getSession().getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        //上传图片
        if(file.getSize()==0) {
            return ServerResponse.createByErrorMessage("未上传图片");
        }
        String imgFileName = null;
        try {
            imgFileName = DealWithFile.uploadFile(request,file,Const.TITLE_IMG1,Const.TITLE_IMG2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(imgFileName);
        article.setTitle_img(imgFileName);
        System.out.println(article.getArticle());
        String txtFileName = null;
        txtFileName = DealWithFile.createTXTFile(request, article.getArticle());
        article.setArticle(txtFileName);
        article.setUser_id(user.getUser_id());
        article.setOutline(article.getOutline().replaceAll("&nbsp;", " "));
        Boolean result = articleService.insertnotes(article);
        if(result) {
            userService.addUserArticlenum(user.getUser_id());
            return ServerResponse.createBySuccessMessage("游记发表成功");
        }
        return ServerResponse.createByErrorMessage("游记发表失败");
    }

    /**
     * home.html
     * 获取登录用户个人游记
     * @return
     */
    @RequestMapping(value = "get_userarticle", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userarticle(HttpSession session,int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return articleService.getUserArticle(user.getUser_id(),pn);
    }


    /**
     * user.html
     * 获取用户个人游记
     * @return
     */
    @RequestMapping(value = "get_userarticle1", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_userarticle1(HttpSession session,Integer user_id, int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return articleService.getUserArticle1(user_id,pn);
        return articleService.getUserArticle2(user.getUser_id(), user_id, pn);
    }

    /**
     * 展示游记
     * @return
     */
    @RequestMapping(value = "show_notes", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse show_notes(HttpSession session,String articleid) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return articleService.ShowArticle(articleid);
        return articleService.ShowArticle1(user.getUser_id(),articleid);
    }



    @RequestMapping(value = "test")
    public void addUser(HttpServletRequest request ,MultipartFile pictureFile) throws Exception{
        System.out.println("ttttttttttttttttttttttttttttt");
        if(pictureFile.getSize()==0)
            System.out.println("yyyyyyyyyyyyyy");
        //使用UUID给图片重命名，并去掉四个“-”
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        //获取文件的扩展名
        String ext = FilenameUtils.getExtension(pictureFile.getOriginalFilename());
        //设置图片上传路径
        String url = "D:/test";
        System.out.println(url);
        //以绝对路径保存重名命后的图片
        pictureFile.transferTo(new File(url+"/"+name + "." + ext));
        //把图片存储路径保存到数据库

    }

    /**
     * main.html
     * 获取首页轮播图
     * @return
     */
    @RequestMapping(value = "get_carousel", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_carousel() {
        return articleService.getCarousel();
    }

    /**
     * main.html
     * 获取全部游记
     * @return
     */
    @RequestMapping(value = "get_allpage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_allpage(HttpSession session, int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return articleService.getAllPage(pn);
        return articleService.getAllPage1(user.getUser_id(),pn);
    }

    /**
     * main.html
     * 获取关注人最新游记（需登录）
     * @return
     */
    @RequestMapping(value = "get_newpage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_newpage(HttpSession session, int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return articleService.getNewPage(user.getUser_id(), pn);
    }

    /**
     * main.html
     * 获取榜单游记
     * @return
     */
    @RequestMapping(value = "get_hotpage", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_hotpage(HttpSession session,int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return articleService.getHotPage(pn);
        return articleService.getHotPage1(user.getUser_id(),pn);
    }

    /**
     * search_title.html
     * 获取榜单游记
     * @return
     */
    @RequestMapping(value = "search_titlebyword", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse search_titlebyword(HttpSession session,String search_word, int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
           return articleService.searchTitleByWord(search_word,pn);
        return articleService.searchTitleByWord1(user.getUser_id(),search_word,pn);
    }

    /**
     * search_place.html
     * 获取榜单游记
     * @return
     */
    @RequestMapping(value = "search_placebyword", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse search_placebyword(HttpSession session,String search_word, int pn) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return articleService.searchPlaceByWord(search_word,pn);
        return articleService.searchPlaceByWord1(user.getUser_id(),search_word,pn);
    }

    /**
     * home.html
     * 删除游记
     * @return
     */
    @RequestMapping(value = "delete_article", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete_article(HttpSession session, String articleid) {
        UserInfo user = (UserInfo) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
            return ServerResponse.createByErrorMessage("用户未登录");
        return articleService.DeleteArticle(articleid);
    }


    /**
     * 数据统计
     * @return
     */
    @RequestMapping(value = "get_sumarticle", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_sumarticle(HttpSession session) {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return articleService.GetSumArticle();
    }

    /**
     * 按年统计每月文章增长总数
     * @return
     */
    @RequestMapping(value = "get_staarticle", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_staarticle(HttpSession session, String year)
    {
        Admin admin = (Admin) session.getAttribute(Const.CURRENT_ADMIN);
        if (admin == null)
            return ServerResponse.createByErrorMessage("未登录");
        return articleService.GetStaArticle(year);
    }
}
