package com.jssdream.chatdemo.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * @ClassName RedisListenerBean
 * @Description: redis监听器
 * @Author Js
 * @Date 2019/11/11
 * @Version V1.0
 **/
@Component
public class RedisListenerBean {
    private static final Logger logger = LoggerFactory.getLogger(RedisListenerBean.class);

    @Value("${server.port}")
    private String serverPort;

    @Value("${redis.channel.msgToAll}")
    private String msgToAll;

    @Value("${redis.channel.userStatus}")
    private String userStatus;

    /**
     * redis消息监听器容器
     * 可以添加多个监听不同话题的redis监听器，只需要把消息监听器和相应的消息订阅处理器绑定，该消息监听器
     * 通过反射技术调用消息订阅处理器的相关方法进行一些业务处理
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 监听msgToAll
        container.addMessageListener(listenerAdapter, new PatternTopic(msgToAll));
        //监听用户状态
        container.addMessageListener(listenerAdapter, new PatternTopic(userStatus));
        logger.info("Subscribed Redis channel: " + msgToAll);
        return container;
    }
}
