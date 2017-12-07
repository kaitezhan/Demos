package com.boneix.client.result;

import com.boneix.client.result.base.BaseResult;

import java.util.List;

/**
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/3/16.
 */
class ListResult<T> extends BaseResult<T> {

    private static final long serialVersionUID = -1891369940301562356L;

    private List<T> entities;

    ListResult() {
    }

    ListResult(List<T> entities) {
        this.entities = entities;
    }

    public List<T> getEntities() {
        return entities;
    }
}