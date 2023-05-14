import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/14/0014  9:59
 */

/*
 * TTL:过期时间
 * 1、队列统一过期时间
 *
 * 2、消息单独过期时间
 *
 * 注意：
 *   ①如果设置了消息的过期时间，也设置了队列的过期时间，以短的为准
 *   ②队列过期后，会将队列所有消息全部移除
 *   ③消息过期后，只有消息在队列顶端，才会判断其是否过期（移除掉）
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-rabbitmq-producer.xml")
public class QueueTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testTtl() {
        /*
          1. 设置队列统一过期时间
          队列过期后，队列内的消息会被清空
         */
//        for (int i = 0; i < 10; i++) {
//            rabbitTemplate.convertAndSend("test_exchange_ttl","ttl.hahahahh","我是一个消息，等ttl过期");
//        }
        /*
          定义消息后处理器
         */
        MessagePostProcessor postProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        };
        /*
          2. 单独设置消息过期
         */

        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hahahahh", "我是一个消息，等ttl过期", postProcessor);
            } else {
                rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl.hahahahh", "我是一个消息，等ttl过期");
            }
        }

    }
    /*
     * 发送测试死信消息
     *   1、过期时间
     *   2、长度限制
     *   3、消息拒收
     *
     * */

    @Test
    public void testDlx() {
        // 1、过期时间
//        rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.haha", "测试过期时间，死信消息");
        //  2、长度限制
//        for (int i = 0; i < 20; i++) {
//            rabbitTemplate.convertAndSend("test_exchange_dlx", "test.dlx.haha", "测试长度，死信消息");
//        }
        //3、测试消息拒收，死信消息
        rabbitTemplate.convertAndSend("test_exchange_dlx","test.dlx.haha","测试过期时间，死信消息");
    }

    @Test
    public void testDelay() throws InterruptedException {
        //1、发送订单消息。将来是在订单系统中，下单成功后，发送消息
        rabbitTemplate.convertAndSend("order_exchange","order.msg","订单信息：id=1,time=333333");

        //2、打印倒计时
        for (int i = 0; i < 10; i++) {
            System.out.println(i+"...");
            Thread.sleep(1000);
        }
    }
}
