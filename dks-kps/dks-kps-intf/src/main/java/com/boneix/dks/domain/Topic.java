package com.boneix.dks.domain;

import com.boneix.base.domain.BaseEntity;

/**
 * Created by rzhang on 2017/3/9.
 */
public class Topic extends BaseEntity {
    String title;
    int commentSum;

    public Topic() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCommentSum() {
        return commentSum;
    }

    public void setCommentSum(int commentSum) {
        this.commentSum = commentSum;
    }
}
