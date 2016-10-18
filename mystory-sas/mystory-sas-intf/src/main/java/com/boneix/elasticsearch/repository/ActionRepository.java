package com.boneix.elasticsearch.repository;

import com.boneix.elasticsearch.document.ActionDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zhangrong5 on 2016/9/21.
 */
public interface  ActionRepository extends ElasticsearchRepository<ActionDocument,Integer> {
    ActionDocument findByActionIdAndUserId(int actionId, int userId);
}
