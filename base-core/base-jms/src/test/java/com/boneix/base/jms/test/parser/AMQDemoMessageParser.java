package com.boneix.base.jms.test.parser;

import com.boneix.base.jms.consumer.IMessageParser;
import com.boneix.base.jms.entity.JmsAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by rzhang on 2017/7/4.
 */
public class AMQDemoMessageParser implements IMessageParser<Message> {


    private static final Logger logger = LoggerFactory.getLogger(AMQDemoMessageParser.class);

    @Override
    public JmsAction parse(Message message) {

        TextMessage msg = (TextMessage) message;

        try {
            logger.info("Got message! context is {}", msg.getText());
        } catch (JMSException e) {
            logger.error("When attempt get message, exception is happened .{}", e);
        }
        return null;
    }
}
