package com.boneix.base.jms.test.handler;

import com.boneix.base.jms.consumer.MessageHandlerSupport;
import com.boneix.base.jms.test.constants.ActiveMQQueue;
import com.boneix.base.jms.test.parser.AMQDemoMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * Created by rzhang on 2017/7/4.
 */
public class AMQDemoMessageHandler extends MessageHandlerSupport<Message> {

    private static final Logger logger = LoggerFactory.getLogger(AMQDemoMessageHandler.class);

    @Resource
    private ActiveMQQueue activeMQQueue;


    @Override
    public void init() {
        registerMessageParser(activeMQQueue.getDemoQueue(), new AMQDemoMessageParser());

    }

    @Override
    public String getDestination(Message message) {
        String destinationName = "";
        try {
            Destination destination = message.getJMSDestination();
            if (destination instanceof Queue) {
                destinationName = ((Queue) destination).getQueueName();
            } else if (destination instanceof Topic) {
                destinationName = ((Topic) destination).getTopicName();
            }
        } catch (Exception e) {
            logger.error("When attempt to get destination,Exception is happened.{}", e);
        }
        return destinationName;
    }
}
