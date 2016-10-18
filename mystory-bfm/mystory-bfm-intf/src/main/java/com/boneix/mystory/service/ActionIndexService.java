package com.boneix.mystory.service;

import com.boneix.mystory.domain.ActionDocument;

/**
 * Created by zhangrong5 on 2016/9/23.
 */
public interface ActionIndexService {
     ActionDocument queryActionDocumentByActionId(int actionId);
}
