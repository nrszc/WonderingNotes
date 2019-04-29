package com.wondering.serviceimpl;

import com.wondering.common.ServerResponse;
import com.wondering.dao.MessageMapper;
import com.wondering.pojo.Message;
import com.wondering.service.MessageService;
import com.wondering.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    public ServerResponse getMessage(Integer user_id) {
        List<MessageVO> list = messageMapper.getMessage(user_id);
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse getUserMessage(Integer user_id, String user_id1) {
        List<MessageVO> list = messageMapper.getUserMessage(user_id, user_id1);
        Integer result = messageMapper.updateMessage(user_id, user_id1);
        if (result>0)
            return ServerResponse.createBySuccess(list);
        return ServerResponse.createBySuccessMessage("更新失败");
    }

    public ServerResponse UpdateMessage(String from_user_id, String to_user_id) {
        Integer result = messageMapper.updateMessage(Integer.valueOf(to_user_id), from_user_id);
        if (result>0)
            return ServerResponse.createBySuccessMessage("更新成功");
        return ServerResponse.createByErrorMessage("更新失败");
    }

    public ServerResponse InsertMessage(Message message) {
        Integer result = messageMapper.InsertMessage(message);
        if (result>0)
            return ServerResponse.createBySuccessMessage("插入成功");
        return ServerResponse.createByErrorMessage("插入失败");
    }
}
