package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.SysNoticePublic;

public interface SysNoticePublicService {
    ServerResponse SendNotice(SysNoticePublic sn);

    ServerResponse GetNoticePublic(int pn);

    ServerResponse GetMsg(Integer id, int pn);
}
