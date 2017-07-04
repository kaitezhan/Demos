package com.boneix.base.jms.listener;

import com.boneix.base.jms.consumer.IMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by rzhang on 2017/6/15.
 */
public class ActiveMQListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMQListener.class);

    @Resource
    private IMessageHandler iMessageHandler;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            logger.warn("Only BytesMessage will be processed.");
            return;
        }
        try {
            iMessageHandler.parse(message);
        } catch (Exception e) {
            logger.error("消费 TextMessage {}  失败,Exception :{}", message, e);
        }
    }
}
