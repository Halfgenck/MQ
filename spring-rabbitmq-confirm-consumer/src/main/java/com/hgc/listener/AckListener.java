package com.hgc.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ChannelListener;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/13/0013  19:51
 */


/*
 * Consumer ACK机制：
 *   1、设置手动签收。acknowledge=“manual”
 *   2、让监听器类实现ChannelAwareMessageListener接口
 *   3、如果消息成功处理，调用channel的basicAck()签收
 *   4、如果消息处理失败，则调用channel的basicNack()拒绝签收，broker重新发送给consumer
 * */
@Component
public class AckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        long deliverTag = message.getMessageProperties().getDeliveryTag();
        try {
            //1.接收转换消息
            System.out.println(new String(message.getBody()));

            //2.处理业务逻辑
            System.out.println("处理业务逻辑...");
            int i = 3/0;//出现错误
            // 3. 手动签收
            channel.basicAck(deliverTag,true);
        } catch (Exception e) {
            //4.拒绝签收
            /*
             * 第三个参数：requeue：重回队列。如果设置为true，则消息重新回到queue，broker会重新发送该消息给消费端
             *
             * */
            channel.basicNack(deliverTag,true,true);
            //channel.basicReject(deliveryTag,true);只允许单条确认
//            channel.basicReject(deliverTag,false);
        }
    }
}
