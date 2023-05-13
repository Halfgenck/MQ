package com.hgc.listen;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/9/0009  12:48
 */
@Component
public class RabbitMqListener {

    @RabbitListener(queues = "boot_queue")
    void ListenerQueue(Message message) {
        System.out.println(new String(message.getBody()));
    }
}
