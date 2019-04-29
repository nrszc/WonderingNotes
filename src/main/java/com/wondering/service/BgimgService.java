package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.Bgimg;

public interface BgimgService {
    ServerResponse InsertImg(Bgimg bgimg);

    ServerResponse GetBgimg();

    ServerResponse DeleteBgimg(Integer bgimg_id);

    ServerResponse GetLoginBgimg();
}
