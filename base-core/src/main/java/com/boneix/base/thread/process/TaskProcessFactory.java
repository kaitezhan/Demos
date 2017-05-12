package com.boneix.base.thread.process;

/**
 * 异步任务执行器工厂
 * 
 * @author wangchong
 * 
 */
public class TaskProcessFactory {
	/**
	 * 核心线程数
	 */
	private static int DEFAULT_CORE_SIZE = Runtime.getRuntime().availableProcessors();

	/**
	 * 线程池大小
	 */
	private static int DEFAULT_POOL_SIZE = DEFAULT_CORE_SIZE * 2;

	private int coreSize = DEFAULT_CORE_SIZE;

	private int poolSize = DEFAULT_POOL_SIZE;

	public TaskProcess createTaskProcess(int coreSize, int poolSize) {
		return new TaskProcess(coreSize, poolSize);
	}

	public TaskProcess createTaskProcess() {
		return createTaskProcess(coreSize, poolSize);
	}

	public void setCoreSize(int coreSize) {
		this.coreSize = coreSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
}
