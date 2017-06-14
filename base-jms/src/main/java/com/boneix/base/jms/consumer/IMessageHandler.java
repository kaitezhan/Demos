package com.boneix.base.jms.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

/**
 * Created by rzhang on 2017/6/13.
 */
public interface IMessageHandler {
    void init();

    Action parse(Message message, ConsumeContext consumeContext);
}
