package com.boneix.demo.consumer.result;


import com.boneix.demo.consumer.result.base.BaseResult;

/**
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/3/17.
 */
class SingleResult<T> extends BaseResult<T> {

    private static final long serialVersionUID = -5848222953862452406L;

    private T entity;

    public SingleResult() {
    }

    SingleResult(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }
}