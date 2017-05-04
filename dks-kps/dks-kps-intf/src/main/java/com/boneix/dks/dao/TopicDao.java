package com.boneix.dks.dao;

import com.boneix.base.dao.BaseDao;
import com.boneix.dks.domain.Topic;

/**
 * Created by rzhang on 2017/3/9.
 */
public interface TopicDao extends BaseDao<Topic> {
    int updateTopic(Topic topic);
}
