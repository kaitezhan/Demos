package com.boneix.thread.pool;

import com.boneix.thread.process.TaskProcess;

import java.util.concurrent.Executors;

/**
 * Created by rzhang on 2018/3/15.
 */
public class FixedTaskProcessPool implements TaskProcessPool {

    /**
     * 核心线程数
     */
    private int coreSize;

    public FixedTaskProcessPool(int coreSize) {
        this.coreSize = coreSize;
    }

    public FixedTaskProcessPool() {
        this.coreSize = Runtime.getRuntime().availableProcessors();
    }

    @Override
    public TaskProcess build() {

        return new TaskProcess(Executors.newFixedThreadPool(coreSize));
    }
}
