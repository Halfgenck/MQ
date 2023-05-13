package com.hgc;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class SpringRabbitmqConfirmProducerApplicationTests {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 确认模式
     * 1. 开启确认模式 ： ConnectionFactory中开启，publisher-confirms="true"
     * 2. 定义回调函数
     * 3. 发送消息
     */
    @Test
    public void testConfirm() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {

            /**
             *
             * @param correlationData 相关配置信息，在convertAncSend中参数配置信息
             * @param ack exchange交换机，是否成功收到了消息。true成功，false代表失败
             * @param cause 失败原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("执行了 confirm ....");
                if (ack) {
                    // 接收成功
                    System.out.println("接收成功 ： " + cause);
                } else {
                    // 接收失败
                    System.out.println("接收失败 ： " + cause);
                    //做一些处理，让消息再次发送
                }
            }
        });
        rabbitTemplate.convertAndSend("exchange_test111", "confirm", "message confirm");


    }

    /**
     * 回退模式：当消息发送给exchange后，exchange路由到Queue失败时，才会执行returnCallback
     * 步骤：
     * 1、开启回退模式：也是在ConnectionFactory中开启，publisher-returns="true"
     * 2、设置ReturnCallback
     * 3、设置Exchange处理消息的模式：
     * ①如果消息没有路由到Queue，则丢弃消息（默认）
     * ②如果消息没有路由到Queue，返回给消息发送方ReturnCallback
     */
    @Test
    public void testReturn() {
        //设置交换机处理失败消息的模式
        //不设置该项的话，默认将消息丢弃，也不会触发回调
        rabbitTemplate.setMandatory(true);

        //设置ReturnCallback
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            /**
             *
             * @param message   消息对象
             * @param replyCode 错误码
             * @param replyText 错误信息
             * @param exchange  交换机
             * @param routingKey    路由键
             */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("执行了return");
                System.out.println(message);
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(exchange);
                System.out.println(routingKey);
            }
        });

        //3、发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message confirm...");
    }

    @Test
    public void testSend() {
        int i = 0;
        while (i++ < 10) {
            rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "hello" + i);
        }
    }
}