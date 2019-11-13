package com.jssdream.chatdemo.controller;

import com.jssdream.chatdemo.bean.ChatMsg;
import com.jssdream.chatdemo.common.BaseController;
import com.jssdream.chatdemo.common.JsonUtil;
import com.jssdream.chatdemo.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

/**
 * @ClassName ChatController
 * @Description: TODO
 * @Author Js
 * @Date 2019/11/11
 * @Version V1.0
 **/
@Controller
public class ChatController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Value("${redis.channel.msgToAll}")
    private String msgToAll;

    @Value("${redis.set.onlineUsers}")
    private String onlineUsers;

    @Value("${redis.channel.userStatus}")
    private String userStatus;

    @Autowired
    private ChatService chatService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送消息
     * @param chatMessage
     */
    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMsg chatMessage) {
        try {
            redisTemplate.convertAndSend(msgToAll, JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 加入聊天室
     * @param chatMessage
     * @param headerAccessor
     */
    @MessageMapping("/chat.addUser")
    public void addUser(@Payload ChatMsg chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        logger.info("用户加入聊天室:" + chatMessage.getSender());
        try {
            headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
            //广播用户上线的消息
            redisTemplate.opsForSet().add(onlineUsers, chatMessage.getSender());
            redisTemplate.convertAndSend(userStatus, JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /*@MessageMapping("/chat.sendMessage")//@MessageMapping 和 @RequestMapping 功能类似，浏览器向服务器发起请求时，映射到该地址
    @SendTo("/topic/public")//如果服务器接受到了消息，就会对订阅了 @SendTo 括号中的地址的浏览器发送消息
    public ChatMsg sendMessage(@Payload ChatMsg chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMsg addUser(@Payload ChatMsg chatMessage,
                           SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }*/
}
