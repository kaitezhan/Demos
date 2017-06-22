package com.boneix.base.thread.process;

/**
 * 标记型任务
 *
 * @param <T>
 * @author wangchong
 */
public interface IdentityTaskAction<T> extends TaskAction<T> {
    String identity();
}
