package com.boneix.base.jms.consumer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rzhang on 2017/6/13.
 */
public class MessageHandlerRegister {
    private Map<String, IMessageHandler> container = new ConcurrentHashMap<>();

    private Map<String, String> initParam = new ConcurrentHashMap<>();

    /**
     * @param initParam key : topic ; value : 注册类名;
     */
    public MessageHandlerRegister(Map<String, String> initParam) {
        this.initParam = initParam;
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        for (Map.Entry<String, String> entry : initParam.entrySet()) {
            String className = entry.getValue();
            try {
                Class<?> handlerClass = ClassUtils.forName(className, classLoader);
                if (!IMessageHandler.class.isAssignableFrom(handlerClass)) {
                    throw new FatalBeanException("Class [" + className + "]  does not implement the [" + IMessageHandler.class.getName() + "] interface");
                }
                IMessageHandler namespaceHandler = (IMessageHandler) BeanUtils.instantiateClass(handlerClass);
                namespaceHandler.init();
                container.put(entry.getKey(), namespaceHandler);
            } catch (ClassNotFoundException ex) {
                throw new FatalBeanException("NamespaceHandler class [" + className + "]  not found", ex);
            } catch (LinkageError err) {
                throw new FatalBeanException("Invalid NamespaceHandler class [" + className + "] : problem with handler class file or dependent class", err);
            }
        }
    }

    public Map<String, String> getInitParam() {
        return initParam;
    }

    public void setInitParam(Map<String, String> initParam) {
        this.initParam = initParam;
    }

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
