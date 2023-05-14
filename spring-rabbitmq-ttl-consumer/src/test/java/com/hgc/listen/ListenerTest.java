package com.hgc.listen;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author hgc
 * @version 1.0
 * @date 2023/5/14/0014  10:39
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-rabbitmq-consumer.xml")
public class ListenerTest extends TestCase {
    @Test
    public void testDlx() {
        while(true) {

        }
    }@Test
    public void testDelay() {
        while(true) {

        }
    }

}