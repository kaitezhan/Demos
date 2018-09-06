package com.boneix.thread.pool;


import com.boneix.thread.process.TaskProcess;

/**
 * Created by rzhang on 2017/8/14.
 */
public interface TaskProcessPool {

    TaskProcess build();
}
