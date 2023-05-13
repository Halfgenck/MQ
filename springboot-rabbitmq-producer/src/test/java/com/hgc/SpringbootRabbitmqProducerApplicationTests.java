package com.hgc;

import com.hgc.config.RabbitMqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqProducerApplicationTests {

    @Test
    void contextLoads() {
    }

    //1、注入rabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,"boot.haaha","这是一条发给topics模式的消息！");

    }

}
