package com.boneix.mystory.service.impl;

import com.boneix.base.util.DateFormatUtils;
import com.boneix.elasticsearch.document.UserDocument;
import com.boneix.elasticsearch.domain.ActionBean;
import com.boneix.elasticsearch.domain.ChapterBean;
import com.boneix.elasticsearch.repository.UserRepository;
import com.boneix.mq.domain.ActionMQBean;
import com.boneix.mystory.service.UserChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangrong5 on 2016/9/22.
 */
@Service
public class UserChapterServiceImpl implements UserChapterService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUserChapter(ActionMQBean actionMQBean) {
        //检查字段有效性
        ActionMQBean.checkactionMQBean(actionMQBean);
        //判断id是否存在
        UserDocument userDocument=userRepository.findByUserId(actionMQBean.getUserId());
        if(null!=userDocument){
            List<ChapterBean> chapters= userDocument.getChapters();
            boolean existFlag=false;
            //是否存在chapter
            for(ChapterBean chapter:chapters){
                if(chapter.getChapterId()==actionMQBean.getChapterId()){
                    existFlag=true;
                    boolean existActionFlag=false;
                    //更新chapter
                    List<ActionBean> details=chapter.getDetails();
                    //是否存在action
                    for(ActionBean actionBean:details){
                        if(actionBean.getActionId()==actionMQBean.getActionId()){
                            existActionFlag=true;
                            //更新action
                            actionBean.setActionName(actionMQBean.getActionName());
                            actionBean.setParters(actionMQBean.getParters());
                            actionBean.setCreateTime(actionMQBean.getCreateTime());
                            break;
                        }
                    }
                    //不存在则添加action
                    if(!existActionFlag){
                        ActionBean actionBean=new ActionBean();
                        actionBean.setActionId(actionMQBean.getActionId());
                        actionBean.setActionName(actionMQBean.getActionName());
                        actionBean.setCreateTime(actionMQBean.getCreateTime());
                        actionBean.setParters(actionMQBean.getParters());
                        details.add(actionBean);
                    }
                    break;
                }
            }
            //不存在则添加chapter
            if(!existFlag){
                List<ActionBean> details=new ArrayList<ActionBean>();
                ActionBean actionBean=new ActionBean();
                actionBean.setActionId(actionMQBean.getActionId());
                actionBean.setActionName(actionMQBean.getActionName());
                actionBean.setCreateTime(actionMQBean.getCreateTime());
                actionBean.setParters(actionMQBean.getParters());
                details.add(actionBean);

                ChapterBean chapterBean=new ChapterBean();
                chapterBean.setChapterId(actionMQBean.getChapterId());
                chapterBean.setChapterName(actionMQBean.getChapterName());
                chapterBean.setCreateTime(actionMQBean.getCreateTime());
                chapterBean.setDetails(details);
                chapters.add(chapterBean);
            }
        }else{
            userDocument= new UserDocument();
            List<ChapterBean> chapters= new ArrayList<ChapterBean>();
            List<ActionBean> details=new ArrayList<ActionBean>();
            ActionBean actionBean=new ActionBean();
            actionBean.setActionId(actionMQBean.getActionId());
            actionBean.setActionName(actionMQBean.getActionName());
            actionBean.setCreateTime(actionMQBean.getCreateTime());
            actionBean.setParters(actionMQBean.getParters());
            details.add(actionBean);

            ChapterBean chapterBean=new ChapterBean();
            chapterBean.setChapterId(actionMQBean.getChapterId());
            chapterBean.setChapterName(actionMQBean.getChapterName());
            chapterBean.setCreateTime(actionMQBean.getCreateTime());
            chapterBean.setDetails(details);
            chapters.add(chapterBean);
            userDocument.setChapters(chapters);
            userDocument.setUpdateTime(DateFormatUtils.formatDate(new Date()));
            userDocument.setUserId(actionMQBean.getUserId());
            userDocument.setUserName(actionMQBean.getUserName());
        }
        //保存文档
        userRepository.save(userDocument);

    }


}
