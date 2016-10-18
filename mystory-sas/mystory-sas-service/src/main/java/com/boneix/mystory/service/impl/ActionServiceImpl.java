package com.boneix.mystory.service.impl;

import com.boneix.elasticsearch.document.ActionDocument;
import com.boneix.elasticsearch.repository.ActionRepository;
import com.boneix.mq.domain.ActionMQBean;
import com.boneix.mystory.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangrong5 on 2016/9/23.
 */
@Service
public class ActionServiceImpl implements ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Override
    public void saveAction(ActionMQBean actionMQBean) {
        //检查字段有效性
        ActionMQBean.checkactionMQBean(actionMQBean);
        //
        ActionDocument actionDocument= actionRepository.findByActionIdAndUserId(actionMQBean.getActionId(),actionMQBean.getUserId());

        if(null!=actionDocument){
            actionDocument.setActionName(actionMQBean.getActionName());
            actionDocument.setCreateTime(actionMQBean.getCreateTime());
            actionDocument.setParters(actionMQBean.getParters());
            actionDocument.setChapterId(actionMQBean.getChapterId());
            actionDocument.setChapterName(actionMQBean.getChapterName());
        }else{
            actionDocument=new ActionDocument();
            actionDocument.setActionName(actionMQBean.getActionName());
            actionDocument.setCreateTime(actionMQBean.getCreateTime());
            actionDocument.setParters(actionMQBean.getParters());
            actionDocument.setChapterId(actionMQBean.getChapterId());
            actionDocument.setChapterName(actionMQBean.getChapterName());
            actionDocument.setUserId(actionMQBean.getUserId());
            actionDocument.setUserName(actionMQBean.getUserName());
            actionDocument.setActionId(actionMQBean.getActionId());
            actionDocument.setActionName(actionMQBean.getActionName());
        }
        actionRepository.save(actionDocument);

    }
}
