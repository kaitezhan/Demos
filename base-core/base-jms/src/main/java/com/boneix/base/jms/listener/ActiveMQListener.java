package com.boneix.base.jms.listener;

import com.boneix.base.jms.consumer.IMessageHandler;
import com.boneix.base.jms.consumer.MessageHandlerRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * Created by rzhang on 2017/6/15.
 */
public class ActiveMQListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMQListener.class);

    @Resource
    private MessageHandlerRegister messageHandlerRegister;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            logger.warn("Only BytesMessage will be processed.");
            return;
        }
        try {
            TextMessage msg = (TextMessage) message;
            Destination destination = msg.getJMSDestination();
            IMessageHandler messageHandler = null;
            String destinationName = "";
            if (destination instanceof Queue) {
                destinationName = ((Queue) destination).getQueueName();
            } else if (destination instanceof Topic) {
                destinationName = ((Topic) destination).getTopicName();
            }
            if (StringUtils.isEmpty(destinationName)) {
                logger.warn("Only Quene and Topic support.");
            } else {
                messageHandler = messageHandlerRegister.findMessageHandler(destinationName);
                if (null == messageHandler) {
                    logger.warn("No MessageHandler is matched,destinationName is {}", destinationName);
                } else {
                    messageHandler.parse(msg);
                }
            }
        } catch (Exception e) {
            logger.error("消费 TextMessage {}  失败,Exception :{}", message, e);
        }
    }
}
