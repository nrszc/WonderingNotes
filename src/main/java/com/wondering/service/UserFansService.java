package com.wondering.service;

import com.wondering.common.ServerResponse;

public interface UserFansService {

    ServerResponse getUserFollow(String follow_who, String who_follow);

    ServerResponse ToFollow(String follow_who, String who_follow);

    ServerResponse CancelFollow(String follow_who, String who_follow);
}
