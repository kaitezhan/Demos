package com.boneix.base.jms.listener;

import com.boneix.base.jms.consumer.IMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import javax.annotation.Resource;

/**
 * Created by rzhang on 2017/7/5.
 */
public class RabbitMQListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(AliyunMQListener.class);

    @Resource
    private IMessageHandler iMessageHandler;


    @Override
    public void onMessage(Message message) {
        try {
            iMessageHandler.parse(message);
        } catch (Exception e) {
            logger.error("消费 Message {}  失败,Exception :{}", message, e);
        }
    }
}
