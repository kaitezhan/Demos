package com.boneix.thread.process;

/**
 * 多线程执行器
 * 
 * @author huangyongfa
 */
public class Executor {
    /**
     * 多线程执行器的业务域
     */
    private static final String COMMON_BUSINESS = "GLOBAL_EXECUTOR";

    private static TaskProcessFactory taskProcessFactory = new TaskProcessFactory();
    static {
        taskProcessFactory.setCoreSize(100);
        taskProcessFactory.setPoolSize(1000);
    }

    private static TaskProcessFactory concurrentTaskProcessFactory = new TaskProcessFactory();
    static {
        concurrentTaskProcessFactory.setCoreSize(16);
        concurrentTaskProcessFactory.setPoolSize(16);
    }

    public static TaskProcess getCommonTaskProcess() {
        return TaskProcessManager.getTaskProcess(COMMON_BUSINESS, taskProcessFactory);
    }

    public static void asyncExecute(TaskAction<Object> action) {
        getCommonTaskProcess().asyncExecuteTask(action);
    }
}
