package com.hgc;

/**
 * @author hgc
 * @version 1.0
 * @date ${DATE} ${TIME}
 */

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 接受消息
 */
public class Consumer_PubSub {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 1.创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 2.设置参数
        factory.setHost("192.168.126.12");
        factory.setPort(5672);
        factory.setVirtualHost("/gvhost");
        factory.setUsername("hgc");
        factory.setPassword("hgc");
        // 3. 创建连接 connection
        Connection connection = factory.newConnection();
        // 4. 创建 Channel
        // 不一样的客户端，channel也不一样
        Channel channel = connection.createChannel();

        String queue_name1 = "test_queue_fanout1";

        /**
         * basicConsume(String queue, DeliverCallback deliverCallback, ConsumerShutdownSignalCallback shutdownSignalCallback)
         * queue 队列名称
         * deliverCallback  是否自动回复
         * shutdownSignalCallback  回调对象
         */
        // 接收消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            /**
             * 回调方法 ， 接受消息后自动执行该方法
             * @param consumerTag 标识
             * @param envelope 获取一些信息， 交换机 路由key...
             * @param properties 配置信息
             * @param body  数据
             * @throws IOException
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("body : " + new String(body));
                System.out.println("将日志打印到控制台....");
            }
        };
        // 6. 消费队列中的消息
        channel.basicConsume(queue_name1,true,consumer);
        // 消费者一直监听程序，不需要关闭

    }
}