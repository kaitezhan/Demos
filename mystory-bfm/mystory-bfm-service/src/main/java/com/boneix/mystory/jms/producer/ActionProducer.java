package com.boneix.mystory.jms.producer;

import com.boneix.base.jms.JmsBaseProducerImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Queue;

/**
 * Created by zhangrong5 on 2016/10/13.
 */
@Service
public class ActionProducer extends JmsBaseProducerImpl {

    /**
     * 指定xml中配置的队列bean
     */
    @Resource(name = "actionQueue")
    private Queue destination;

    @Override
    public Queue getDestination() {
        return destination;
    }
}
