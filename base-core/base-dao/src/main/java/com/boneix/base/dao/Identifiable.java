package com.boneix.base.dao;

import java.io.Serializable;

/**
 * 主键标识
 *
 * @author hu_guodong
 * @version [1.0, 2014年12月8日]
 */
public interface Identifiable extends Serializable {
    /**
     * 获取主键
     *
     * @return id
     */
    long getId();

    /**
     * 设置ID属性
     *
     * @param id id
     */
    void setId(long id);
}
