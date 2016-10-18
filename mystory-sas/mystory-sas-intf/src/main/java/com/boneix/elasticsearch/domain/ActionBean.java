package com.boneix.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zhangrong5 on 2016/9/21.
 */
public class ActionBean {
    //事件id
    private int actionId;

    //事件名称
    private String actionName;

    //参与人员
    private @Field(type = FieldType.Nested)
    List<ParterBean> parters;

    private @Field(type = FieldType.Date,format = DateFormat.date_optional_time) String createTime;

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ParterBean> getParters() {
        return parters;
    }

    public void setParters(List<ParterBean> parters) {
        this.parters = parters;
    }
}
