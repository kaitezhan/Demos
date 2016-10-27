package com.boneix.base.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 基类，提供公共属性及默认toString实现。
 *
 * @author huguodong
 * @version [1.0, 2015年11月3日]
 */
public abstract class BaseModelEntity implements Identifiable {
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

    /**
     * 创建人ID
     */
    private int createUserId;

    /**
     * 创建人用户名
     */
    private String createUsername;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    /**
     * 最后更新人ID
     */
    private int updateUserId;

    /**
     * 最后更新人名称
     */
    private String updateUsername;

    public BaseModelEntity() {

    }

    public BaseModelEntity(Date createTime, int createUserId, String createUsername, Date updateTime, int updateUserId,
                           String updateUsername) {
        this.createTime = createTime;
        this.createUserId = createUserId;
        this.createUsername = createUsername;
        this.updateTime = updateTime;
        this.updateUserId = updateUserId;
        this.updateUsername = updateUsername;
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

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(int updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUsername() {
        return updateUsername;
    }

    public void setUpdateUsername(String updateUsername) {
        this.updateUsername = updateUsername;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
