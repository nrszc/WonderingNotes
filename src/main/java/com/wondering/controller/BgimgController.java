package com.wondering.controller;

import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.pojo.Admin;
import com.wondering.pojo.Bgimg;
import com.wondering.service.BgimgService;
import com.wondering.utils.DealWithFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/bgimg")
public class BgimgController {

    @Autowired
    BgimgService bgimgService;

    /**
     * 上传登录背景图片
     * @return
     */
    @RequestMapping(value = "upload_bgimg", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload_bgimg(MultipartFile file, HttpSession session, HttpServletRequest request)
    {
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin==null)
            return ServerResponse.createByErrorMessage("未登录");
        if(file.getSize()==0) {
            return ServerResponse.createByErrorMessage("未上传图片");
        }
        String imgFileName = null;
        try {
            imgFileName = DealWithFile.uploadLoginImg(request,file,Const.LOGINIMG1,Const.LOGINIMG2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bgimg bgimg = new Bgimg();
        bgimg.setAdmin_id(admin.getId());
        bgimg.setUrl(imgFileName);
        return bgimgService.InsertImg(bgimg);
    }

    /**
     * 获取所有登录背景图片
     * @return
     */
    @RequestMapping(value = "get_bgimg", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_bgimg(HttpSession session)
    {
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin==null)
            return ServerResponse.createByErrorMessage("未登录");
        return bgimgService.GetBgimg();
    }

    /**
     * 删除登录背景图片
     * @return
     */
    @RequestMapping(value = "delete_bgimg", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse delete_bgimg(HttpSession session, Integer bgimg_id)
    {
        Admin admin = (Admin)session.getAttribute(Const.CURRENT_ADMIN);
        if(admin==null)
            return ServerResponse.createByErrorMessage("未登录");
        return bgimgService.DeleteBgimg(bgimg_id);
    }

    /**
     * 获取所有登录背景图片
     * @return
     */
    @RequestMapping(value = "get_loginbgimg", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse get_loginbgimg(HttpSession session)
    {
        return bgimgService.GetLoginBgimg();
    }

}
