package com.boneix.base.jms.consumer;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.boneix.base.jms.exception.MessageParserException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rzhang on 2017/6/13.
 */
public abstract class MessageHandlerSupport implements IMessageHandler {

    private final Map<String, IMessageParser> parsers = new ConcurrentHashMap<>();

    @Override
    public Action parse(Message message, ConsumeContext consumeContext) {
        return findParserForMessage(message, consumeContext).parse(message, consumeContext);
    }

    private IMessageParser findParserForMessage(Message message, ConsumeContext consumeContext) {
        IMessageParser parser = this.parsers.get(message.getTag());
        if (parser == null) {
            throw new MessageParserException("No MessageParser is matched,tag is " + message.getTag());
        }
        return parser;
    }

    protected final void registerMessageParser(String elementName, IMessageParser parser) {
        this.parsers.put(elementName, parser);
    }

}
