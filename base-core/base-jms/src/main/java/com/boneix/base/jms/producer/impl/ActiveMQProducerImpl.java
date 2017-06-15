package com.boneix.base.jms.producer.impl;

import com.boneix.base.jms.entity.JmsSendResult;
import com.boneix.base.jms.producer.IJmsBaseProducer;
import com.boneix.base.utils.serializer.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 * Created by rzhang on 2017/6/15.
 */
public abstract class ActiveMQProducerImpl implements IJmsBaseProducer {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQProducerImpl.class);

    @Resource
    private JmsTemplate jmsTemplate;

    public abstract Destination getDestination();

    @Override
    public JmsSendResult send(Object message) {
        JmsSendResult jmsSendResult = new JmsSendResult();
        if (null == getDestination()) {
            logger.error("ActiveMQProducerImpl's destination is null !");
            jmsSendResult.setStatus(false);
        } else {
            try {
                Destination destination = getDestination();
                String destinationName = "";
                if (destination instanceof Queue) {
                    destinationName = ((Queue) destination).getQueueName();
                } else if (destination instanceof Topic) {
                    destinationName = ((Topic) destination).getTopicName();
                }
                if (StringUtils.isEmpty(destinationName)) {
                    logger.warn("Only Quene and Topic support.");
                    jmsSendResult.setStatus(false);
                } else {
                    Message msgReceive = jmsTemplate.sendAndReceive(destination, session -> session.createTextMessage(JsonUtils.toString(message)));
                    jmsSendResult.setMessageId(msgReceive.getJMSMessageID());
                    jmsSendResult.setDestination(destinationName);
                    jmsSendResult.setStatus(true);
                }
            } catch (Exception e) {
                logger.error("ActiveMQProducerImpl  Exception:{}", e);
                jmsSendResult.setStatus(false);
            }
        }
        return jmsSendResult;
    }
}
