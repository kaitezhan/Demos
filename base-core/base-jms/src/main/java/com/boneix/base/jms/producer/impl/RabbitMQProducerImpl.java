package com.boneix.base.jms.producer.impl;

import com.boneix.base.jms.entity.JmsSendResult;
import com.boneix.base.jms.producer.IJmsBaseProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Created by rzhang on 2017/7/5.
 */
public abstract class RabbitMQProducerImpl implements IJmsBaseProducer {
    private static final Logger logger = LoggerFactory.getLogger(ActiveMQProducerImpl.class);

    @Resource
    private AmqpTemplate amqpTemplate;


    public abstract String getRoutingKey();

    @Override
    public JmsSendResult send(Object message) {
        JmsSendResult jmsSendResult = new JmsSendResult();
        try {
            // 发送消息到消息队列服务器中，并得到回馈内容
            Object object = amqpTemplate.convertSendAndReceive(getRoutingKey(), message);
            if (Objects.nonNull(object) && object instanceof Message) {
                Message receive = (Message) object;
                logger.debug("message Receive is {}", receive.getBody());
                jmsSendResult.setMessageId(receive.getMessageProperties().getMessageId());
                jmsSendResult.setDestination(getRoutingKey());
                jmsSendResult.setStatus(true);
                return jmsSendResult;
            }
        } catch (Exception e) {
            logger.error("When attempt to send Message,Exception is happened.{}", e);
        }
        jmsSendResult.setStatus(false);
        return jmsSendResult;
    }
}
