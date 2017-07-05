package com.boneix.base.jms.test;

import com.boneix.base.jms.producer.IJmsBaseProducer;
import com.boneix.base.jms.test.entity.Student;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by rzhang on 2017/7/3.
 */
public class DemoRMQTest extends BaseSpringTest {
    private static final Logger logger = LoggerFactory.getLogger(DemoRMQTest.class);

    @Resource
    private IJmsBaseProducer iJmsBaseProducer;

    @Test
    public void demoTest() {
    }

    @Test
    public void demoTestSendMessage() {
        iJmsBaseProducer.send(new Student(2, "阿斯顿·十六"));
    }

}
