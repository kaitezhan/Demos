package com.boneix.base.jms.consumer;

import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rzhang on 2017/6/13.
 */
public class MessageHandlerRegister {
    Map<String, IMessageHandler> container = new ConcurrentHashMap<>();

    public Map<String, IMessageHandler> getContainer() {
        return container;
    }

    public void setContainer(Map<String, IMessageHandler> container) {
        this.container = container;
    }

    public IMessageHandler findMessageHandler(String topicName) {
        if (CollectionUtils.isEmpty(container)) {
            return null;
        } else {
            return container.get(topicName);
        }
    }
}
