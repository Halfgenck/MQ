package com.hgc.rabbitmq.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/9/0009  12:20
 */
public class TopicListenerStar implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // 打印监听的消息
        System.out.println(new String(message.getBody()));
    }
}
