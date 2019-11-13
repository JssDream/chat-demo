package com.jssdream.chatdemo.config;

import com.jssdream.chatdemo.Enum.EnumMsgType;
import com.jssdream.chatdemo.bean.ChatMsg;
import com.jssdream.chatdemo.common.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @ClassName WebSocketFilter
 * @Description: websocket过滤器
 * @Author Js
 * @Date 2019/11/11
 * @Version V1.0
 **/
@Component
public class WebSocketFilter {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketFilter.class);

    // 使用 SimpMessagingTemplate 向浏览器发送信息
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Value("${server.port}")
    private String serverPort;

    @Value("${redis.set.onlineUsers}")
    private String onlineUsers;

    @Value("${redis.channel.userStatus}")
    private String userStatus;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        InetAddress localHost;
        try {
            localHost = Inet4Address.getLocalHost();
            logger.info("Received a new web socket connection from:" + localHost.getHostAddress() + ":" + serverPort);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 用户关闭网页时，websocket会调用该方法
     *
     * 在这里需要把用户从redis的在线用户set里删除，并且向集群发送广播，说明该用户退出聊天室
     *
     * @param event
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            logger.info("User Disconnected : " + username);

            ChatMsg chatMessage = new ChatMsg();
            chatMessage.setMsgType(EnumMsgType.LEAVE.getIndex());
            chatMessage.setSender(username);
            try {
                redisTemplate.opsForSet().remove(onlineUsers, username);
                redisTemplate.convertAndSend(userStatus, JsonUtil.parseObjToJson(chatMessage));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
//                messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }

}
