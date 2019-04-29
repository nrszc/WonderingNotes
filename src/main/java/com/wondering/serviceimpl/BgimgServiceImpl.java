package com.wondering.serviceimpl;

import com.wondering.common.ServerResponse;
import com.wondering.dao.BgimgMapper;
import com.wondering.pojo.Bgimg;
import com.wondering.service.BgimgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bgimgService")
public class BgimgServiceImpl implements BgimgService {

    @Autowired
    BgimgMapper bgimgMapper;

    public ServerResponse InsertImg(Bgimg bgimg) {
        Integer id = bgimgMapper.InsertImg(bgimg);
        if(id==null)
            return ServerResponse.createByErrorMessage("上传失败");
        bgimg.setId(id);
        return ServerResponse.createBySuccess("上传成功", bgimg);
    }

    public ServerResponse GetBgimg() {
        List<Bgimg> list = bgimgMapper.GetBgimg();
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse DeleteBgimg(Integer bgimg_id) {
        if(bgimgMapper.DeleteBgimg(bgimg_id)>0)
            return ServerResponse.createBySuccessMessage("删除成功");
        return ServerResponse.createByErrorMessage("删除失败");
    }

    public ServerResponse GetLoginBgimg() {
        Bgimg bgimg = bgimgMapper.GetLoginBgimg();
        return ServerResponse.createBySuccess(bgimg);
    }
}
