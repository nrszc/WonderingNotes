package com.wondering.serviceimpl;

import com.wondering.dao.SysNoticePrivateMapper;
import com.wondering.service.SysNoticePrivateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("sysNoticePrivateService")
public class SysNoticePrivateServiceImpl implements SysNoticePrivateService {

    @Autowired
    SysNoticePrivateMapper sysNoticePrivateMapper;
}
