package com.boneix.base.thread.process;

/**
 * 任务回调封装
 *
 * @param <T> 返回类型
 * @author wangchong
 */
public interface TaskAction<T> {
    T doInAction();
}

