package com.boneix.base.jms.listener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.boneix.base.jms.consumer.IMessageHandler;
import com.boneix.base.jms.consumer.MessageHandlerRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * Created by rzhang on 2017/6/14.
 */
public class AliyunMQListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(AliyunMQListener.class);

    @Resource
    private MessageHandlerRegister messageHandlerRegister;

    @Override
    public Action consume(Message message, ConsumeContext context) {
        IMessageHandler messageHandler = messageHandlerRegister.findMessageHandler(message.getTopic());
        if (null == messageHandler) {
            logger.warn("No MessageHandler is matched,topic is {}", message.getTopic());
        } else {
            messageHandler.parse(message, context);
        }
        return  Action.CommitMessage;
    }

}
