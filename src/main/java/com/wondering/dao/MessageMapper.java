package com.wondering.dao;

import com.wondering.pojo.Message;
import com.wondering.service.MessageService;
import com.wondering.vo.MessageVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Message record);

    int insertSelective(Message record);

    Message selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);

    List<MessageVO> getMessage(Integer user_id);

    List<MessageVO> getUserMessage(@Param("user_id") Integer user_id, @Param("user_id1")String user_id1);

    Integer updateMessage(@Param("user_id")Integer user_id,@Param("user_id1") String user_id1);

    Integer InsertMessage(Message message);
}