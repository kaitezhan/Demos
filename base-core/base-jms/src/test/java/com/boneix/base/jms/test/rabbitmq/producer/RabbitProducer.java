package com.boneix.base.jms.test.rabbitmq.producer;

import com.boneix.base.jms.producer.impl.RabbitMQProducerImpl;
import com.boneix.base.jms.test.rabbitmq.constants.RabbitMQQueue;

import javax.annotation.Resource;

/**
 * Created by rzhang on 2017/7/5.
 */
public class RabbitProducer extends RabbitMQProducerImpl {

    @Resource
    private RabbitMQQueue rabbitMQQueue;

    @Override
    public String getRoutingKey() {
        return rabbitMQQueue.getDemoQueue();
    }
}
