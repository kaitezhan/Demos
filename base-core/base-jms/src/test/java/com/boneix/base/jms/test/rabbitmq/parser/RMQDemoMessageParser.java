package com.boneix.base.jms.test.rabbitmq.parser;

import com.boneix.base.jms.consumer.IMessageParser;
import com.boneix.base.jms.entity.JmsAction;
import com.boneix.base.jms.test.entity.Student;
import com.boneix.base.utils.serializer.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;


/**
 * Created by rzhang on 2017/7/4.
 */
public class RMQDemoMessageParser implements IMessageParser<Message> {


    private static final Logger logger = LoggerFactory.getLogger(RMQDemoMessageParser.class);


    @Override
    public JmsAction parse(Message message) {
        try {
            Student person = (Student) new ObjectInputStream(new ByteArrayInputStream(message.getBody())).readObject();
            logger.info("Got message! context is {}", JsonUtils.toString(person));
        } catch (Exception e) {
            logger.error("When attempt get message, exception is happened .{}", e);
        }
        return null;
    }
}
