package com.boneix.mystory.service;

import com.boneix.mystory.domain.UserDocument;

/**
 * Created by zhangrong5 on 2016/9/23.
 */
public interface UserIndexService {
    UserDocument queryUserInfoByUserId(int userId);
}
