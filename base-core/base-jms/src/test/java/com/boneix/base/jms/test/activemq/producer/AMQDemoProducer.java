package com.boneix.base.jms.test.activemq.producer;

import com.boneix.base.jms.producer.impl.ActiveMQProducerImpl;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;

/**
 * Created by rzhang on 2017/7/4.
 */
public class AMQDemoProducer extends ActiveMQProducerImpl {

    @Resource(name = "actionQueue")
    private Queue queue;

    @Override
    public Destination getDestination() {
        return queue;
    }
}
