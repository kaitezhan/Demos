package com.boneix.mq.domain;

import com.boneix.elasticsearch.domain.ParterBean;

import java.util.List;

/**
 * Created by zhangrong5 on 2016/9/22.
 */
public class ActionMQBean {
    private int userId;
    private String userName;
    private int chapterId;
    private String chapterName;
    private int actionId;
    private String actionName;
    private List<ParterBean> parters;
    private String createTime;

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

    //检查数据完整性
    public static void checkactionMQBean(ActionMQBean actionMQBean) {
        boolean nullFlag=null==actionMQBean||actionMQBean.getActionId()==0
                ||actionMQBean.getChapterId()==0||actionMQBean.getUserId()==0;
        if(nullFlag){
            throw new IllegalArgumentException("message is illegal!");
        }
    }
}
