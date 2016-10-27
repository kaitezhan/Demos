package com.boneix.demo.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneix.base.util.JsonUtils;
import com.boneix.thread.process.Executor;
import com.boneix.thread.process.TaskAction;
import com.google.common.base.Stopwatch;

public class ThreadCase {

    private static Logger logger = LoggerFactory.getLogger(RatingCase.class);

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    /**
     * 使用TaskAction方式调用多线程方法
     */
    @Test
    public void testThread() {
        Stopwatch sw = Stopwatch.createStarted();
        List<TaskAction<Integer>> tasks = new ArrayList<>();
        try {
            for(int i=0;i<100;i++){
                final int k=i;
                TaskAction<Integer> serverTask = new TaskAction<Integer>() {
                    @Override
                    public Integer doInAction() throws Exception {
                        return costSecondServer(k);
                    }
                };
                tasks.add(serverTask);
            }
            
            if (CollectionUtils.isNotEmpty(tasks)) {
                List<Integer> taskResult = Executor.getCommonTaskProcess().executeTask(tasks);
                if (CollectionUtils.isNotEmpty(taskResult)) {
                    logger.error("this fuction's result:,{}", JsonUtils.toString(taskResult));
                }
            }
        } catch (Exception e) {
            logger.error("this fuction throws Exception:,{}", e);
        }
        logger.error("this fuction cost time:,{}", sw.toString());
    }

    /**
     * 执行多线程时不使用CountDownLatch 直接调用future的get方法
     */
    @Test
    public void testThreadWithOutCountDownLatch() {
        Stopwatch sw = Stopwatch.createStarted();
        List<TaskAction<Integer>> tasks = new ArrayList<>();
        try {
            for(int i=0;i<100;i++){
                final int k=i;
                TaskAction<Integer> serverTask = new TaskAction<Integer>() {
                    @Override
                    public Integer doInAction() throws Exception {
                        return costSecondServer(k);
                    }
                };
                tasks.add(serverTask);
            }
            
            if (CollectionUtils.isNotEmpty(tasks)) {
                List<Integer> taskResult = Executor.getCommonTaskProcess().executeFutureTask(tasks);
                if (CollectionUtils.isNotEmpty(taskResult)) {
                    logger.error("this fuction's result:,{}", JsonUtils.toString(taskResult));
                }
            }
        } catch (Exception e) {
            logger.error("this fuction throws Exception:,{}", e);
        }
        logger.error("this fuction cost time:,{}", sw.toString());
    }

    private int costSecondServer(int iStart) throws InterruptedException { 
        if(Thread.currentThread().getId()!=1){
            logger.info("this thread-id is "+Thread.currentThread().getId());
        }else{
            logger.info("this is main thread");
        }
                 
        return iStart;
    }

}
