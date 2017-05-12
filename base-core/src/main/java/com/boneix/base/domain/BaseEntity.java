package com.boneix.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 基类，提供公共属性及默认toString实现。
 *
 * @author huguodong
 * @version [1.0, 2015年11月3日]
 */
public abstract class BaseEntity implements Identifiable {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 6179877143112441458L;

    /**
     * 主键，唯一标识
     */
    private long id;

    public enum DeleteFlag {
        AVAILABLE("未删除", 0), USABLE("已删除", 1);

        private final int value;

        private final String name;

        DeleteFlag(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return this.name;
        }

        public int getValue() {
            return this.value;
        }
    }

    /**
     * 逻辑删除标记，0：未删除；1：已删除
     */
    private int deleteFlag = DeleteFlag.AVAILABLE.getValue();

    /**
     * 创建时间
     */
    private Date createTime;

    public BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
