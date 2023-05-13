package com.hgc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/9/0009  11:28
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class ProducerTest {
    // 1. 注入rabbitTemplate
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testHelloWord() {
        // 发送消息
        rabbitTemplate.convertAndSend("spring_queue","hello world spring ...");
    }

    @Test
    public void testFanout(){
        //2、发送消息
        rabbitTemplate.convertAndSend("spring_fanout_exchange","","spring fanout");
    }
    @Test
    public void testTopic(){
        //2、发送消息
        rabbitTemplate.convertAndSend("spring_topic_exchange","hgc.hg.mmmm","spring topics");
    }
}
