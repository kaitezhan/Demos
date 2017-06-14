package com.boneix.base.jms.producer;

import com.boneix.base.jms.entity.JmsSendResult;

/**
 * Created by rzhang on 2017/6/13.
 */
public interface IJmsBaseProducer {
    JmsSendResult send(final Object message);

}
