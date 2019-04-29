package com.wondering.service;

import com.wondering.common.ServerResponse;

public interface AdminService {

    ServerResponse AdminLogin(String name, String password);
}
