package com.boneix.elasticsearch.repository;

import com.boneix.elasticsearch.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * Created by zhangrong5 on 2016/9/21.
 */
public interface UserRepository extends ElasticsearchRepository<UserDocument,Integer> {
    UserDocument findByUserId(int userId);
}
