package com.wondering.websocket;

import java.io.IOException;
import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wondering.common.Const;
import com.wondering.pojo.Message;
import com.wondering.pojo.User;
import com.wondering.service.MessageService;
import com.wondering.vo.UserInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.websocket.Session;


public class SpringWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    MessageService messageService;

    private static final ArrayList<WebSocketSession> users;//这个会出现性能问题，最好用Map来存储，key用userid
    private static Logger logger = Logger.getLogger(SpringWebSocketHandler.class);
    static {
        users = new ArrayList<WebSocketSession>();
    }

    public SpringWebSocketHandler() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 连接成功时候，会触发页面上onopen方法
     */
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("connect to the websocket success......当前数量:"+users.size());
        users.add(session);
        //这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        //TextMessage returnMessage = new TextMessage("你将收到的离线");
        //session.sendMessage(returnMessage);
    }

    /**
     * 关闭连接时触发
     */
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        logger.debug("websocket connection closed......");
        //String username= (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        UserInfo user = (UserInfo)session.getAttributes().get(Const.CURRENT_USER);
        System.out.println("用户"+user.getUser_id()+"已退出！");
        users.remove(session);
        System.out.println("剩余在线用户"+users.size());
    }

    /**
     * js调用websocket.send时候，会调用该方法
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
          System.out.println(message.getPayload()+"22222222222");
          super.handleTextMessage(session, message);
          JSONObject chat = JSON.parseObject(message.getPayload());
          JSONObject message1 = JSON.parseObject(chat.get("message").toString());
          if((chat.get("type").toString()).equals("letter")){
              Message mage = new Message();
              mage.setContent(message1.get("content").toString());
              mage.setFrom_user_id(Integer.valueOf(message1.get("from_user_id").toString()));
              mage.setTo_user_id(Integer.valueOf(message1.get("to_user_id").toString()));
              if(messageService.InsertMessage(mage).isSuccess()) {
                  System.out.println(message1.get("to_user_id").toString()+"iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
                  sendMessageToUser(Integer.valueOf(message1.get("to_user_id").toString()), message);
              }
          }
    }

    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){session.close();}
        logger.debug("websocket connection closed......");
        users.remove(session);
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param user_id
     * @param message
     */
    public void sendMessageToUser(Integer user_id, TextMessage message) {
        System.out.println("yyyyyy");
        for (WebSocketSession user : users) {
            UserInfo u = (UserInfo) user.getAttributes().get(Const.CURRENT_USER);
            if (u.getUser_id()==user_id) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        System.out.println("nnnnnn");
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}