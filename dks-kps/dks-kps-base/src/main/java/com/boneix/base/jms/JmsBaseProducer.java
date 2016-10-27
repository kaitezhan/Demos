package com.boneix.base.jms;

import javax.jms.JMSException;

/**
 * Created by zhangrong5 on 2016/10/13.
 */
public interface JmsBaseProducer {
    boolean send(final String message) throws JMSException;

    boolean send(final Object message) throws JMSException;
}
