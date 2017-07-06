package com.boneix.base.jms.test;

import com.boneix.base.jms.producer.IJmsBaseProducer;
import com.boneix.base.jms.test.entity.Student;
import com.boneix.base.utils.serializer.ISerializer;
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

    @Resource
    private ISerializer iSerializer;

    @Test
    public void demoTest() {
    }

    @Test
    public void demoTestSendSerializableMessage() {
        iJmsBaseProducer.send(new Student(2, "阿斯顿·十六"));
    }

    @Test
    public void demoTestSendStringMessage() {
        String message = "aaaaa5aa123aaaaasdas41qwe1";
        iJmsBaseProducer.send(message);
    }

    @Test
    public void demoTestSendMessage() {
        logger.info("ready to send message");
        iJmsBaseProducer.send(iSerializer.writeObject(new Student(2, "阿斯顿·十六")));
        iJmsBaseProducer.send(iSerializer.writeObject(new Student(3, "阿斯顿·十六")));
        iJmsBaseProducer.send(iSerializer.writeObject(new Student(4, "阿斯顿·十六")));
        iJmsBaseProducer.send(iSerializer.writeObject(new Student(5, "阿斯顿·十六")));
    }

}
