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
public class Consumer {
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
        // 5. 创建队列
        /**
         * queueDeclare(String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments)
         * queue ： 队列名称
         * durable ： 是否持久化
         * exclusive ：
         *      - 是否独占 ，只能由一个消费者监听这个队列
         *      - 当 connection 关闭时 ， 是否删除队列
         * autoDelete ： 是否自定删除，没有 consumer 的时候自动删除
         * arguments
         */
        // 如果没有叫 hello_hgc的队列，则会创建队列，有则不会创建
        // 队列已经在发送者创建 ，有没有都一样
        channel.queueDeclare("work_queues",true,false,false,null);
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
                System.out.println("consumerTag : " + consumerTag);
                System.out.println("envelope : " + envelope);
                System.out.println("properties : " + properties);
                System.out.println("body : " + new String(body));
//                super.handleDelivery(consumerTag, envelope, properties, body);
            }
        };
        // 6. 消费队列中的消息
        channel.basicConsume("work_queues",true,consumer);
        // 消费者一直监听程序，不需要关闭

    }
}