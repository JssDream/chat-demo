package com.jssdream.chatdemo.services;

import com.jssdream.chatdemo.bean.ChatMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * @ClassName ChatService
 * @Description: 发送消息服务
 * @Author Js
 * @Date 2019/11/11
 * @Version V1.0
 **/
@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    //spring-websocket 定义了一个 SimpMessageSendingOperations 接口（或者使用SimpMessagingTemplate ），
    // 可以实现自由的向任意目的地发送消息，并且订阅此目的地的所有用户都能收到消息
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    public void sendMsg(@Payload ChatMsg chatMessage) {
        logger.info("Send msg by simpMessageSendingOperations:" + chatMessage.toString());
        simpMessageSendingOperations.convertAndSend("/topic/public", chatMessage);
    }

    /**
     * 广播用户上线或者下线消息
     * @param chatMessage
     */
    public void alertUserStatus(@Payload ChatMsg chatMessage) {
        logger.info("Alert user online by simpMessageSendingOperations:" + chatMessage.toString());
        simpMessageSendingOperations.convertAndSend("/topic/public", chatMessage);
    }
}
