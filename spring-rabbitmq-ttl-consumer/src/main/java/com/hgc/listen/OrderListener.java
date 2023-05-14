package com.hgc.listen;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/14/0014  10:24
 */
@Component
public class OrderListener implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        Thread.sleep(1000);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 1.接受消息体
            System.out.println(new String(message.getBody()));
            // 2. 处理业务逻辑

            //2.处理业务逻辑
            System.out.println("处理业务逻辑...");
            System.out.println("根据订单id查询其状态...");
            System.out.println("判断状态是否为支付成功？");
            System.out.println("取消订单，回滚库存！");
            // 3. 手动签收消息
            channel.basicAck(deliveryTag,true);
        }catch (Exception e) {
            System.out.println("出现异常，拒绝接收！");
            // 不要返回原队列
            channel.basicNack(deliveryTag,true,false);
        }
    }
}
