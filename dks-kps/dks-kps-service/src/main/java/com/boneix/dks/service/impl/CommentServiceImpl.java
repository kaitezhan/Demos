package com.boneix.dks.service.impl;

import com.boneix.base.util.JsonUtils;
import com.boneix.dks.dao.CommentDao;
import com.boneix.dks.dao.TopicDao;
import com.boneix.dks.domain.Comment;
import com.boneix.dks.domain.Topic;
import com.boneix.dks.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by rzhang on 2017/3/9.
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Resource
    private CommentDao commentDao;

    @Resource
    private TopicDao topicDao;

    @Override
    public  long addComment(Comment comment) {
        if(comment.getTopicId()>0){
            Topic topic=topicDao.selectById(comment.getTopicId());
            logger.info("帖子信息： "+JsonUtils.toString(topic));
            comment.setFloor(topic.getCommentSum()+1);
            if(topicDao.updateById(topic)>0){
                logger.info("评论信息： "+JsonUtils.toString(comment));
                return commentDao.insert(comment);
            }else{
                return addCommentAgain(comment);
            }
        }
        return -1;
    }

    private  long addCommentAgain(Comment comment) {
        if(comment.getTopicId()>0){
            Topic topic=topicDao.selectById(comment.getTopicId());
            logger.info("帖子信息： "+JsonUtils.toString(topic));
            comment.setFloor(topic.getCommentSum()+1);
            if(topicDao.updateById(topic)>0){
                logger.info("评论信息： "+JsonUtils.toString(comment));
                return commentDao.insert(comment);
            }
        }
        return -1;
    }

    @Override
    public long addCommentWithoutLock(Comment comment) {
        if(comment.getTopicId()>0){
            Topic topic=topicDao.selectById(comment.getTopicId());
            comment.setFloor(topic.getCommentSum()+1);
            commentDao.insert(comment);
            return topicDao.updateTopic(topic);
        }
        return -1;
    }
}
