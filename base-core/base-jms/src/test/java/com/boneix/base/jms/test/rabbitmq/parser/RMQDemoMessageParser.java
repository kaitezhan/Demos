package com.boneix.base.jms.test.rabbitmq.parser;

import com.boneix.base.jms.consumer.IMessageParser;
import com.boneix.base.jms.entity.JmsAction;
import com.boneix.base.jms.test.entity.Student;
import com.boneix.base.utils.serializer.ISerializer;
import com.boneix.base.utils.serializer.JsonUtils;
import com.boneix.base.utils.serializer.impl.ProtoStuffSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;

import javax.annotation.Resource;


/**
 * Created by rzhang on 2017/7/4.
 */
public class RMQDemoMessageParser implements IMessageParser<Message> {


    private static final Logger logger = LoggerFactory.getLogger(RMQDemoMessageParser.class);

    @Resource
    private ISerializer iSerializer;

    @Override
    public JmsAction parse(Message message) {
        try {
            switch (message.getMessageProperties().getContentType()) {
                case MessageProperties.CONTENT_TYPE_SERIALIZED_OBJECT:
                    Student person = (Student) SerializationUtils.deserialize(message.getBody());
                    logger.info("Got message! context is {}", JsonUtils.toString(person));
                    break;
                case MessageProperties.CONTENT_TYPE_BYTES:
                    // 这边的parser应该是在与主线程不同的另外一个线程中，无法通过@Resource进行注入，所以这里直接new 一个
                    if (null == iSerializer) {
                        this.iSerializer = new ProtoStuffSerializer();
                        logger.info("new ProtoStuffSerializer .............");
                    }
                    Student student = iSerializer.readObject(message.getBody(), Student.class);
                    logger.info("Got message! context is {}", student.toString());
                    break;
                case MessageProperties.CONTENT_TYPE_TEXT_PLAIN:
                    String msg = new String(message.getBody());
                    logger.info("Got message! context is {}", msg);
                    break;
                default:
                    logger.error("When attempt get message, ContentType is not parsed .{}", message.getMessageProperties().getContentType());
            }
        } catch (Exception e) {
            logger.error("When attempt get message, exception is happened .{}", e);
        }
        return null;
    }
}
