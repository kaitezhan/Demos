package com.boneix.demo.consumer.result.base;

import java.io.Serializable;

/**
 * @Authro qygu.
 * @Email qiyao.gu@qq.com.
 * @Date 2017/3/16.
 */
public abstract class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = -7965606689257003880L;

    /**
     * 返回包含正常数据结果的ResultEntity
     */
    public ResultEntity toResultEntity() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setData(this);
        return resultEntity;
    }
}