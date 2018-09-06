package com.boneix.thread.process;

import java.util.concurrent.ExecutorService;

/**
 * Created by rzhang on 2018/3/15.
 */
public class TaskProcess extends AbstractTaskProcess {

    public TaskProcess(ExecutorService executorService) {
        this.executor = executorService;
    }
}
