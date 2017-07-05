package com.boneix.base.jms.test.rabbitmq.handler;

import com.boneix.base.jms.consumer.MessageHandlerSupport;
import com.boneix.base.jms.test.rabbitmq.constants.RabbitMQQueue;
import com.boneix.base.jms.test.rabbitmq.parser.RMQDemoMessageParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import javax.annotation.Resource;

/**
 * Created by rzhang on 2017/7/4.
 */
public class RMQDemoMessageHandler extends MessageHandlerSupport<Message> {

    private static final Logger logger = LoggerFactory.getLogger(RMQDemoMessageHandler.class);


    @Resource
    private RabbitMQQueue rabbitMQQueue;


    @Override
    public void init() {
        registerMessageParser(rabbitMQQueue.getDemoQueue(), new RMQDemoMessageParser());
    }

    @Override
    public String getDestination(Message message) {
        return message.getMessageProperties().getReceivedRoutingKey();
    }
}
