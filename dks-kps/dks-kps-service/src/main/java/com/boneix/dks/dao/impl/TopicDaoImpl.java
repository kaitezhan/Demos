package com.boneix.dks.dao.impl;

import com.boneix.base.dao.impl.BaseDaoImpl;
import com.boneix.dks.dao.TopicDao;
import com.boneix.dks.domain.Topic;
import org.springframework.stereotype.Repository;

/**
 * Created by rzhang on 2017/3/9.
 */
@Repository
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements TopicDao {

    @Override
    public int updateTopic(Topic topic) {
        return sqlSessionTemplate.update(getSqlName("updateTopic"), topic);
    }
}
