package com.boneix.base.jms;

import com.boneix.base.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

/**
 * Created by zhangrong5 on 2016/10/13.
 */
public abstract class JmsBaseProducerImpl implements JmsBaseProducer {

    private static final Logger LOG = LoggerFactory.getLogger(JmsBaseProducerImpl.class);

    @Resource
    private JmsTemplate jmsTemplate;

    public abstract Queue getDestination();

    @Override
    public boolean send(final String message) throws JMSException {
        boolean result = true;
        if (null == getDestination()) {
            LOG.error("JmsBaseProducerImpl's destination is null !");
            result = false;
        } else {
            try {
                jmsTemplate.send(getDestination(), new MessageCreator() {
                    public Message createMessage(Session session)
                            throws JMSException {
                        return session.createTextMessage(message);
                    }
                });
            } catch (Exception e) {
                result = false;
                LOG.error("JmsBaseProducerImpl Exception:{}", e);
                throw new JMSException(e.getMessage());
            }
        }
        return result;
    }

    @Override
    public boolean send(final Object message) throws JMSException {
        boolean result = true;
        if (null == getDestination()) {
            LOG.error("JmsBaseProducerImpl's destination is null !");
            result = false;
        } else {
            try {
                jmsTemplate.send(getDestination(), new MessageCreator() {
                    public Message createMessage(Session session)
                            throws JMSException {
                        return session.createTextMessage(JsonUtils.toString(message));
                    }
                });
            } catch (Exception e) {
                result = false;
                LOG.error("JmsBaseProducerImpl Exception:{}", e);
                throw new JMSException(e.getMessage());
            }
        }
        return result;
    }
}
