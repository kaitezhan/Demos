package com.boneix.thread.process;

import com.boneix.thread.pool.FixedTaskProcessPool;
import com.boneix.thread.pool.TaskProcessPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rzhang on 2018/3/15.
 */
public class TaskProcessManager {

    /**
     * 执行器的容器，根据业务域来区分
     * 每个业务域只有一个执行器
     */
    private static Map<String, TaskProcess> taskProcessContainer = new ConcurrentHashMap<>();
    /**
     * 默认异步任务执行器工厂
     */
    private static TaskProcessPool defaultTaskProcessPool = new FixedTaskProcessPool();

    private TaskProcessManager() {
    }

    public static TaskProcess getTaskProcess(String businessDomain, TaskProcessPool factory) {
        if (factory == null) {
            factory = defaultTaskProcessPool;
        }
        TaskProcess tmpProcess = taskProcessContainer.get(businessDomain);
        if (tmpProcess == null) {
            // DCL双重检查
            synchronized (TaskProcessManager.class) {
                tmpProcess = taskProcessContainer.get(businessDomain);
                if (tmpProcess == null) {
                    tmpProcess = factory.build();
                    taskProcessContainer.put(businessDomain, tmpProcess);
                }
            }
        }
        return tmpProcess;
    }
}
