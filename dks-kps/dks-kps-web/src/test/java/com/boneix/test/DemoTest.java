package com.boneix.test;

import com.boneix.base.client.RestClient;
import com.boneix.base.domain.ResponseVo;
import com.boneix.base.thread.process.Executor;
import com.boneix.base.thread.process.TaskAction;
import com.boneix.base.util.JsonUtils;
import com.boneix.dks.domain.Comment;
import com.mysql.jdbc.util.LRUCache;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by rzhang on 2017/3/9.
 */
public class DemoTest {
    private static final Logger LOG = LoggerFactory.getLogger(DemoTest.class);

    @Test
    public void test() {
        System.out.println("aaa");
    }

    @Test
    public void testTime() {
        LOG.info(new Date().getTime()+"");
    }

    @Test
    public void testLRUCache(){
        LRUCache lruCache=new LRUCache(10);

        for(int i=0;i<15;i++){
            lruCache.put("key_"+i,i);
        }
        for(int i=2;i<17;i++){
            lruCache.put("key_"+i,i);
        }
        for(int i=3;i<20;i++){
            lruCache.put("key_"+i,i);
        }
        for(int i=4;i<10;i++){
            lruCache.put("key_"+i,i);
        }


        LOG.info(JsonUtils.toString(lruCache));

    }

    @Test
    public void testLock() {
        int topicId = 1;
        String url = "http://localhost:8180/demo/addComment";
        testAction(topicId, url);
    }

    @Test
    public void testUnLock() {
        int topicId = 2;
        String url = "http://localhost:8180/demo/addCommentNoLock";
        testAction(topicId, url);
    }

    private void testAction(int topicId, String url) {

        List<TaskAction<String>> tasks = new ArrayList<>();
        for (int i = 2; i < 100; i++) {
            Comment comment = new Comment();
            comment.setIp("127.0.0." + i);
            comment.setDetail(i + " say : hello");
            comment.setTopicId(topicId);

            TaskAction<String> serverTask = new TaskAction<String>() {
                @Override
                public String doInAction() throws Exception {
                    ResponseVo responseVo = JsonUtils.toBean(restData(url, comment), ResponseVo.class);
                    String data = String.valueOf(responseVo.getData());

                    return data;
                }
            };
            tasks.add(serverTask);
        }

        if (CollectionUtils.isNotEmpty(tasks)) {
            List<String> taskResult = Executor.getCommonTaskProcess().executeTask(tasks);
            if (CollectionUtils.isNotEmpty(taskResult)) {
                Set<String> tmpSet = new HashSet<>();
                for (String tmp : taskResult) {
                    if (StringUtils.isNotEmpty(tmp)) {
                        tmpSet.add(tmp);
                    }
                }
                LOG.info("result size:{} , Distinct size:{}", taskResult.size(), tmpSet.size());
            }
        }


    }

    private String restData(String url, Object obj) {
        String msg = "";
        String data = JsonUtils.toString(obj);
        RestClient rc = new RestClient(url, "POST", data);
        try {
            msg = rc.execute();
        } catch (Exception e) {
            LOG.error("请求失败，" + e);
        }
        return msg;
    }

    private String restGetData(String url, Object obj) {
        String msg = "";
        String data = JsonUtils.toString(obj);
        RestClient rc = new RestClient(url, "GET", data);
        try {
            msg = rc.execute();
        } catch (Exception e) {
            LOG.error("请求失败，" + e);
        }
        return msg;
    }


}
