package com.jssdream.chatdemo.redis;

import com.jssdream.chatdemo.bean.ChatMsg;
import com.jssdream.chatdemo.common.JsonUtil;
import com.jssdream.chatdemo.services.ChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @ClassName RedisListenerHandle
 * @Description: Redis订阅频道处理类
 * @Author Js
 * @Date 2019/11/11
 * @Version V1.0
 **/
@Component
public class RedisListenerHandle extends MessageListenerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(RedisListenerHandle.class);

    @Value("${redis.channel.msgToAll}")
    private String msgToAll;

    @Value("${redis.channel.userStatus}")
    private String userStatus;

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ChatService chatService;

    /**
     * 收到监听消息
     * @param message
     * @param bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String rawMsg;
        String topic;
        try {
            rawMsg = redisTemplate.getStringSerializer().deserialize(body);
            topic = redisTemplate.getStringSerializer().deserialize(channel);
            logger.info("Received raw message from topic:" + topic + ", raw message content：" + rawMsg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return;
        }


        if (msgToAll.equals(topic)) {
            logger.info("Send message to all users:" + rawMsg);
            ChatMsg chatMessage = JsonUtil.parseJsonToObj(rawMsg, ChatMsg.class);
            if (chatMessage != null) {
                chatService.sendMsg(chatMessage);
            }
            //userStatus频道的消息，并调用service
        } else if (userStatus.equals(topic)) {
            ChatMsg chatMessage = JsonUtil.parseJsonToObj(rawMsg, ChatMsg.class);
            if (chatMessage != null) {
                chatService.alertUserStatus(chatMessage);
            }
        }else {
            logger.warn("No further operation with this topic!");
        }
    }
}
