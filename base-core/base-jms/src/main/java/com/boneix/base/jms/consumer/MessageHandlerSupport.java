package com.boneix.base.jms.consumer;

import com.boneix.base.jms.entity.JmsAction;
import com.boneix.base.jms.exception.MessageParserException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rzhang on 2017/6/13.
 */
public abstract class MessageHandlerSupport<T> implements IMessageHandler<T> {

    private final Map<String, IMessageParser> parsers = new ConcurrentHashMap<>();

    @Override
    public JmsAction parse(T message) {
        return findParserForMessage(message).parse(message);
    }

    private IMessageParser findParserForMessage(T message) {
        IMessageParser parser = this.parsers.get(getDestination(message));
        if (parser == null) {
            throw new MessageParserException("No MessageParser is matched,Destination is " + getDestination(message));
        }
        return parser;
    }

    protected final void registerMessageParser(String elementName, IMessageParser parser) {
        this.parsers.put(elementName, parser);
    }

}