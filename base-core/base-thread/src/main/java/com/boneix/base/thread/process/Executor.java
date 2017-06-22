package com.boneix.base.thread.process;

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
        taskProcessFactory.setCoreSize(2);
        taskProcessFactory.setPoolSize(5);
    }

    private static TaskProcessFactory concurrentTaskProcessFactory = new TaskProcessFactory();

    static {
        concurrentTaskProcessFactory.setCoreSize(10);
        concurrentTaskProcessFactory.setPoolSize(16);
    }

    public static TaskProcess getCommonTaskProcess() {
        return TaskProcessManager.getTaskProcess(COMMON_BUSINESS, taskProcessFactory);
    }

    public static void asyncExecute(TaskAction<Object> action) {
        getCommonTaskProcess().asyncExecuteTask(action);
    }
}
