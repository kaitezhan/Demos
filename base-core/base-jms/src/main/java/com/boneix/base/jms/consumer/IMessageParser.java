package com.boneix.base.jms.consumer;

import com.boneix.base.jms.entity.JmsAction;

/**
 * Created by rzhang on 2017/6/13.
 */
public interface IMessageParser<T> {
    JmsAction parse(T message);
}
