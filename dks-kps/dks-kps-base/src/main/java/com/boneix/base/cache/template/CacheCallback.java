/*
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 * Author: wangchong
 * Date: 2014-8-23
 * Description:Callback.java 
 */
package com.boneix.base.cache.template;

import com.boneix.base.cache.pool.Credis;

/**
 * @author wangchong
 */
public interface CacheCallback<T> {
    T doInAction(Credis jedis);

    String getIdentity();
}
