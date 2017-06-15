package com.boneix.base.thread.process;
/**
 * 标记型任务
 * @author wangchong
 *
 * @param <T>
 */
public interface IdentityTaskAction<T> extends TaskAction<T> {
	String identity();
}
