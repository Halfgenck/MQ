package com.hgc;

import com.rabbitmq.client.BuiltinExchangeType;
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
public class Producer_PubSub {
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
        /**
         * exchangeDeclare(String exchange, BuiltinExchangeType type, boolean durable, boolean autoDelete, boolean internal, Map<String, Object> arguments)
         * exchange 交换机名称
         * type 交换机类型
         *  - direct 定向
         *  - fanout 扇形 广播 到每一个绑定的队列
         *  - topic 通配符方式
         *  - headers 参数匹配
         * durable 是否持久化
         * autoDelete 是否自动删除
         * internal : 内部使用
         * arguments : 参数
         */
        // 5. 创建交换机
        String exchange_name = "test_fanout";
        channel.exchangeDeclare(exchange_name, BuiltinExchangeType.FANOUT,true,false,false,null);
        // 6. 创建队列
        String queue_name1 = "test_queue_fanout1";
        String queue_name2 = "test_queue_fanout2";
        channel.queueDeclare(queue_name1,true,false,false,null);
        channel.queueDeclare(queue_name2,true,false,false,null);
        /**
         * queueBind(String queue, String exchange, String routingKey, Map<String, Object> arguments)
         * queue 绑定队列名称
         * exchange 绑定的交换机名称
         * routingKey 路由键 绑定规则 如果 交换机类型为 fanout routingKey 设置为""
         * arguments
         */
        // 7. bind队列
        channel.queueBind(queue_name1,exchange_name,"");
        channel.queueBind(queue_name2,exchange_name,"");
        // 8. 发送消息
        String message = "日志 : queue error....";
        channel.basicPublish(exchange_name,"",null,message.getBytes());
        // 关闭连接
        channel.close();
        connection.close();
    }
}