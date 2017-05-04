package com.boneix.dks.domain;

import com.boneix.base.domain.BaseEntity;

/**
 * Created by rzhang on 2017/3/9.
 */
public class Comment extends BaseEntity {
    int topicId;
    int parentId;
    String ip;
    String detail;
    int floor;

    public Comment() {
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
