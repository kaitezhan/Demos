package com.boneix.mystory.service;

import com.boneix.mq.domain.ActionMQBean;

/**
 * Created by zhangrong5 on 2016/9/23.
 */
public interface ActionService {
    void saveAction(ActionMQBean actionMQBean);

}
