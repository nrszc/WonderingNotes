package com.wondering.service;

import com.wondering.common.ServerResponse;
import com.wondering.pojo.Message;

public interface MessageService {
    ServerResponse getMessage(Integer user_id);

    ServerResponse getUserMessage(Integer user_id, String user_id1);

    ServerResponse UpdateMessage(String from_user_id, String to_user_id);

    ServerResponse InsertMessage(Message message);
}
