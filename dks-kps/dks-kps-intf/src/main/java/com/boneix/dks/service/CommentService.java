package com.boneix.dks.service;

import com.boneix.dks.domain.Comment;

/**
 * Created by rzhang on 2017/3/9.
 */
public interface CommentService  {
    long addComment(Comment comment);

    long addCommentWithoutLock(Comment comment);
}
