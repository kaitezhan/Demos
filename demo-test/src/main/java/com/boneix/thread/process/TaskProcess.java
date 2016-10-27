package com.boneix.thread.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

/**
 * 异步任务执行器
 * 
 * @author wangchong
 */
public class TaskProcess {
    private static Logger logger = LoggerFactory.getLogger(TaskProcess.class);

    /**
     * concurrent执行器
     */
    private ExecutorService executor;

    /**
     * 核心线程数
     */
    private int coreSize;

    /**
     * 线程池数
     */
    private int poolSize;

    TaskProcess(int coreSize, int poolSize) {
        this.coreSize = coreSize;
        this.poolSize = poolSize;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        // 线程工厂创建后台运行线程       
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r, "Taskprocess-Thread");
                t.setDaemon(true);
                return t;
            }
        };
        // 有界队列
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(coreSize);
        executor = new ThreadPoolExecutor(coreSize, poolSize, 60, TimeUnit.SECONDS, queue, factory,
                new ThreadPoolExecutor.CallerRunsPolicy());

        // JDK回调钩子
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                if (executor == null) {
                    return;
                }
                try {
                    executor.shutdown();
                    executor.awaitTermination(5, TimeUnit.MINUTES);
                } catch (InterruptedException e) {
                    logger.warn("interrupted when shuting down the process executor:\n{}", e);
                }
            }
        });
    }

    /**
     * 执行TaskAction并等待执行结果
     * 
     * @param tasks
     * @return 执行结果
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> executeTask(List<TaskAction<T>> tasks) {
        TaskAction<T>[] actions = new TaskAction[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            actions[i] = tasks.get(i);
        }
        return executeTask(actions);
    }

    public <T> Holder<T> syncExecuteWaiting(final TaskAction<T> task) {
        final CountDownLatch latch = new CountDownLatch(1);
        Future<T> future = executor.submit(new Callable<T>() {
            @Override
            public T call() throws Exception {
                try {
                    return task.doInAction();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            }
        });
        return new Holder<T>(latch, future);
    }

    /**
     * 异步执行TaskAction，无须等待执行结果
     * 
     * @param tasks
     */
    public void asyncExecuteTask(List<TaskAction<?>> tasks) {
        TaskAction<?>[] actions = new TaskAction[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            actions[i] = tasks.get(i);
        }
        asyncExecuteTask(actions);
    }

    /**
     * 异步执行TaskAction，无须等待执行结果
     * 
     * @param tasks
     */
    public void asyncExecuteTask(TaskAction<?>... tasks) {
        for (final TaskAction<?> runnable : tasks) {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        runnable.doInAction();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> exeucteTaskByConcurrentControl(final int concurrentCount, List<TaskAction<T>> tasks) {
        TaskAction<T>[] actions = new TaskAction[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            actions[i] = tasks.get(i);
        }
        return exeucteTaskByConcurrentControl(concurrentCount, actions);
    }

    private <T> List<Future<T>> barrier(final int concurrentCount, TaskAction<T>... tasks) {
        List<Future<T>> futures = new ArrayList<Future<T>>(tasks.length);
        int lacth = tasks.length < concurrentCount ? tasks.length : concurrentCount;
        int devide = tasks.length / concurrentCount == 0 ? 1 : tasks.length / concurrentCount;
        for (int i = 0; i < devide; i++) {
            TaskAction<T>[] newTaskActionAry = Arrays
                    .copyOfRange(tasks, i * concurrentCount, (i + 1) * concurrentCount);
            final CountDownLatch latch = new CountDownLatch(lacth);
            for (final TaskAction<T> action : newTaskActionAry) {
                if (action == null) {
                    continue;
                }
                Future<T> future = executor.submit(new Callable<T>() {
                    @Override
                    public T call() throws Exception {
                        try {
                            return action.doInAction();
                        } finally {
                            latch.countDown();
                        }
                    }
                });
                futures.add(future);
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.info("Executing Task is interrupt.");
            }
        }
        return futures;
    }

    /**
     * 并发控制任务执行
     * 
     * @param concurrentCount 并行任务数
     * @param tasks
     * @return
     */
    public <T> List<T> exeucteTaskByConcurrentControl(int concurrentCount, TaskAction<T>... tasks) {
        int modTaskCount = tasks.length;
        if (concurrentCount > coreSize) {
            concurrentCount = coreSize;
        }
        int remainTaskCount = tasks.length % concurrentCount;
        List<T> resultList = new ArrayList<T>(modTaskCount);
        List<Future<T>> futures = new ArrayList<Future<T>>(tasks.length);

        Map<Integer, TaskAction<T>[]> currentTaskMap = new HashMap<Integer, TaskAction<T>[]>(2);
        currentTaskMap.put(concurrentCount, Arrays.copyOf(tasks, modTaskCount));

        if (remainTaskCount != 0 && remainTaskCount != modTaskCount) {
            modTaskCount = tasks.length - remainTaskCount;
            currentTaskMap.put(concurrentCount, Arrays.copyOf(tasks, modTaskCount));
            currentTaskMap.put(remainTaskCount, Arrays.copyOfRange(tasks, modTaskCount, tasks.length));
        }
        for (Entry<Integer, TaskAction<T>[]> entry : currentTaskMap.entrySet()) {
            futures.addAll(barrier(entry.getKey(), entry.getValue()));
        }

        for (Future<T> future : futures) {
            try {
                T result = future.get();
                if (result != null) {
                    resultList.add(result);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    /**
     * 执行TaskAction并等待执行结果
     * 
     * @param tasks
     * @return 执行结果
     */
    public <T> List<T> executeTask(TaskAction<T>... tasks) {
        final CountDownLatch latch = new CountDownLatch(tasks.length);

        List<Future<T>> futures = new ArrayList<Future<T>>();
        List<T> resultList = new ArrayList<T>();

        for (final TaskAction<T> runnable : tasks) {
            Future<T> future = executor.submit(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    //Stopwatch sw=Stopwatch.createStarted();
                    try {
                        return runnable.doInAction();
                    } finally {
                        latch.countDown();
//                        sw.stop();
//                        logger.info("cost time:{}",sw.toString());
                    }

                }
            });
            futures.add(future);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.info("Executing Task is interrupt.");
        }

        for (Future<T> future : futures) {
            try {
                T result = future.get();
                if (result != null) {
                    resultList.add(result);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

    /**
     * 标识性任务执行，等待执行结果，对任务执行结果分类
     * 
     * @param tasks
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> Map<String, T> executeIdentityTask(List<IdentityTaskAction<T>> tasks) {
        IdentityTaskAction<T>[] actions = new IdentityTaskAction[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            actions[i] = tasks.get(i);
        }
        return executeIdentityTask(actions);
    }

    /**
     * 标识性任务执行，等待执行结果，对任务执行结果分类
     * 
     * @param tasks
     * @return
     */
    public <T> Map<String, T> executeIdentityTask(IdentityTaskAction<T>... tasks) {
        final CountDownLatch latch = new CountDownLatch(tasks.length);

        Map<String, Future<T>> futures = new HashMap<String, Future<T>>();
        Map<String, T> resultMap = new HashMap<String, T>();

        for (final IdentityTaskAction<T> runnable : tasks) {
            Future<T> future = executor.submit(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    long time = System.currentTimeMillis();
                    try {
                        return runnable.doInAction();
                    } finally {
                        logger.debug("Executing Task : {} ,time :{}", runnable.identity(), System.currentTimeMillis()
                                - time);
                        latch.countDown();
                    }

                }
            });
            futures.put(runnable.identity(), future);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            logger.info("Executing Task is interrupt.");
        }

        Iterator<Entry<String, Future<T>>> it = futures.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, Future<T>> entry = it.next();
            try {
                T result = entry.getValue().get();
                resultMap.put(entry.getKey(), result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultMap;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> executeFutureTask(List<TaskAction<T>> tasks) {
        TaskAction<T>[] actions = new TaskAction[tasks.size()];
        for (int i = 0; i < tasks.size(); i++) {
            actions[i] = tasks.get(i);
        }

        List<Future<T>> futures = new ArrayList<Future<T>>();
        List<T> resultList = new ArrayList<T>();

        for (final TaskAction<T> runnable : actions) {
            Future<T> future = executor.submit(new Callable<T>() {
                @Override
                public T call() throws Exception {
                    //Stopwatch sw=Stopwatch.createStarted();
                    try {
                        return runnable.doInAction();
                    } finally {
                        //sw.stop();
                        //logger.info("cost time:{}",sw.toString());
                    }
                }
            });
            futures.add(future);
        }

        for (Future<T> future : futures) {
            try {
                T result = future.get();
                if (result != null) {
                    resultList.add(result);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return resultList;
    }

}
