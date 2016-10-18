package com.boneix.mystory.mq.listener;

import com.boneix.base.util.JsonUtils;
import com.boneix.mq.domain.ActionMQBean;
import com.boneix.mystory.service.ActionService;
import com.boneix.mystory.service.UserChapterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by zhangrong5 on 2016/9/22.
 */
public class ActionMQListener implements MessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ActionMQListener.class);
    public static Integer flag = 0;

    @Resource
    private UserChapterService userChapterService;

    @Resource
    private ActionService actionService;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof TextMessage)) {
            logger.warn("Only TextMessage will be processed.");
            return;
        }
        try {
            String textMessage = ((TextMessage) message).getText();

            ActionMQBean actionMQBean = JsonUtils.toBean(textMessage, ActionMQBean.class);
            if (flag == 0) {
                logger.info("收到消息,{}", textMessage);
                //保存索引文档
                actionService.saveAction(actionMQBean);
                //保存关系文档
                userChapterService.saveUserChapter(actionMQBean);

            } else {
                logger.info("停止消费,{}", textMessage);
            }
        } catch (Exception e) {
            logger.error("消费 TextMessage  {}  失败,Exception :{}", message, e);
        }
    }
}
