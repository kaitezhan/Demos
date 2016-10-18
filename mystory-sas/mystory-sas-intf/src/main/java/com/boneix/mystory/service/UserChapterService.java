package com.boneix.mystory.service;

import com.boneix.mq.domain.ActionMQBean;

/**
 * Created by zhangrong5 on 2016/9/22.
 */
public interface UserChapterService {
    void saveUserChapter(ActionMQBean userChapterBean);
}
