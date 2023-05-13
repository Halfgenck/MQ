package com.hgc.rabbitmq.listener;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/9/0009 11:19
 */

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 定义监听类 SpringQueueListener
 */
public class SpringQueueListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        // 打印监听的消息
        System.out.println(new String(message.getBody()));
    }
}
