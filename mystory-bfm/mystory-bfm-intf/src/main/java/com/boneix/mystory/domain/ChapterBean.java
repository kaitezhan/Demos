package com.boneix.mystory.domain;


import java.util.List;

/**
 * Created by zhangrong5 on 2016/9/21.
 */
public class ChapterBean {

    private int chapterId;

    private String chapterName;

    private List<ActionBean> details;

    private String createTime;

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
