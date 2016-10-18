package com.boneix.elasticsearch.document;

import com.boneix.elasticsearch.domain.ParterBean;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zhangrong5 on 2016/9/21.
 */
@Document(indexName = "action-index", type = "ACTION", shards = 4, replicas = 0, refreshInterval = "30s")
public class ActionDocument {

    private @Id int actionId;

    private String actionName;

    private int userId;

    private String userName;

    private int chapterId;

    private String chapterName;

    //参与人员
    private @Field(type = FieldType.Nested) List<ParterBean> parters;

    private @Field(type = FieldType.Date,format = DateFormat.date_optional_time) String createTime;

    private int deleteFlag;

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

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
