package com.boneix.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zhangrong5 on 2016/9/21.
 */
public class ChapterBean {

    private int chapterId;

    private String chapterName;

    private @Field(type = FieldType.Nested) List<ActionBean> details;

    private @Field(type = FieldType.Date,format= DateFormat.date_optional_time) String createTime;

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

    public List<ActionBean> getDetails() {
        return details;
    }

    public void setDetails(List<ActionBean> details) {
        this.details = details;
    }
}
