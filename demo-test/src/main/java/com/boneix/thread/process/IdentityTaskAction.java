package com.boneix.thread.process;
/**
 * 标记型任务
 * @author wangchong
 *
 * @param <T>
 */
public interface IdentityTaskAction<T> extends TaskAction<T> {
	String identity();
}
