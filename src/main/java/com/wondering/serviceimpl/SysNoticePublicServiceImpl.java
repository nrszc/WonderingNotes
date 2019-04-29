package com.wondering.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wondering.common.Const;
import com.wondering.common.ServerResponse;
import com.wondering.dao.SysNoticePublicMapper;
import com.wondering.pojo.SysNoticePublic;
import com.wondering.service.SysNoticePublicService;
import com.wondering.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysNoticePublicService")
public class SysNoticePublicServiceImpl implements SysNoticePublicService {

    @Autowired
    SysNoticePublicMapper sysNoticePublicMapper;

    public ServerResponse SendNotice(SysNoticePublic sn) {
        if(sysNoticePublicMapper.SendNotice(sn)>0)
            return ServerResponse.createBySuccessMessage("发送成功");
        return ServerResponse.createByErrorMessage("发送失败");
    }

    public ServerResponse GetNoticePublic(int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<Notice> list = sysNoticePublicMapper.GetNoticePublic();
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }

    public ServerResponse GetMsg(Integer user_id, int pn) {
        PageHelper.startPage(pn, Const.articlecount);
        List<Notice> list = sysNoticePublicMapper.GetMsg(user_id);
        PageInfo page = new PageInfo(list);
        return ServerResponse.createBySuccess(page);
    }
}
