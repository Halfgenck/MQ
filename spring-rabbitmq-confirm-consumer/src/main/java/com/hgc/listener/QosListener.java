package com.hgc.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Consumer 限流机制
 *   1、确保Ack机制为手动确认
 *   2、listener-container配置属性
 *       preFetch = 1，表示消费端每次从mq中拉取1条消息来消费，直到手动确认消费完毕后，才会继续拉取下一条消息。
 * */

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/13/0013  20:58
 */
@Component
public class QosListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        //1、获取消息
        System.out.println(new String(message.getBody()));

        //2、处理业务逻辑
        int i = 3 / 0;
        //3、签收
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
