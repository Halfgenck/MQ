package com.hgc;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author hgc
 * @version 1.0
 * @date ${DATE} ${TIME}
 */

/**
 * 发送消息
 */
public class Producer_WorkQueues {
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
        channel.queueDeclare("work_queues", true, false, false, null);
        /**
         * basicPublish(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body)
         * exchange 交换机名称 ，简单模式下交换机默认为 ”“
         * routingKey 路由名称
         * props 配置信息
         * body 发送消息数据
         */
        int i = 0;
        while (i ++ < 10) {
            // 6. 发送消息
            String body = "I am work_queues " + i + ", Mr.gc";
            channel.basicPublish("", "work_queues", null, body.getBytes());
        }
        // 关闭连接
        channel.close();
        connection.close();
    }
}