package com.boneix.base.jms.producer.impl;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import com.boneix.base.jms.entity.JmsSendResult;
import com.boneix.base.jms.producer.IJmsBaseProducer;
import com.boneix.base.jms.serializer.ISerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by rzhang on 2017/6/13.
 */
public abstract class RocketMQProducerImpl implements IJmsBaseProducer {
    private static final Logger logger = LoggerFactory.getLogger(RocketMQProducerImpl.class);

    @Resource
    private ProducerBean producerBean;

    @Resource
    private ISerializer iSerializer;

    public abstract Message getMessage(byte[] message);

    @Override
    public JmsSendResult send(Object message) {
        JmsSendResult jmsSendResult = new JmsSendResult();
        try {
            SendResult sendResult = producerBean.send(getMessage(iSerializer.writeObject(message)));
            jmsSendResult.setMessageId(sendResult.getMessageId());
            jmsSendResult.setDestination(sendResult.getTopic());
            jmsSendResult.setStatus(true);
        } catch (Exception e) {
            logger.error("RocketMQProducerImpl send {} , Exception:{}", message, e);
            jmsSendResult.setStatus(false);
        }
        return jmsSendResult;
    }

}
