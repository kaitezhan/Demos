package com.boneix.elasticsearch.document;

import com.boneix.elasticsearch.domain.ChapterBean;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zhangrong5 on 2016/9/21.
 */
@Document(indexName = "user-index", type = "USERINFO", shards = 4, replicas = 0, refreshInterval = "30s")
public class UserDocument {

    private @Id int userId;

    private String userName;

    private @Field(type = FieldType.Nested) List<ChapterBean> chapters;

    private @Field(type = FieldType.Date,format = DateFormat.date_optional_time) String updateTime;

    private int deleteFlag;

    public List<ChapterBean> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterBean> chapters) {
        this.chapters = chapters;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
}
