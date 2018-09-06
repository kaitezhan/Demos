package com.boneix.thread.process;


import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * Created by rzhang on 2017/8/14.
 */
public abstract class AbstractTaskProcess {

    /**
     * concurrent执行器
     */
    protected ExecutorService executor;

    /**
     * 执行TaskAction并等待执行结果
     */
    public <T> List<T> executeTask(List<TaskAction<T>> tasks) throws InterruptedException {
        TaskAction<T>[] actions = new TaskAction[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            actions[i] = tasks.get(i);
        }
        return doExecuteTask(actions);
    }

    /**
     * 异步执行TaskAction，无须等待执行结果
     */
    public void asyncExecuteTask(List<TaskAction<?>> tasks) {
        TaskAction<?>[] actions = new TaskAction[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            actions[i] = tasks.get(i);
        }
        doAsyncExecuteTask(actions);
    }


    private <T> List<T> doExecuteTask(TaskAction<T>[] tasks) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(tasks.length);
        List<T> resultList = new CopyOnWriteArrayList<>();
        for (final TaskAction<T> runnable : tasks) {
            executor.execute(() -> {
                try {
                    T result = runnable.doInAction();
                    if (Objects.nonNull(result)) {
                        resultList.add(result);
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        return resultList;
    }

    private void doAsyncExecuteTask(TaskAction<?>[] tasks) {
        for (final TaskAction<?> runnable : tasks) {
            executor.execute(runnable::doInAction);
        }
    }

}
