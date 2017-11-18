package com.gaop.gCrawler.core.factory;

/**
 * @description 	
 * 	读写资源缓冲区
 * @author gaop
 * @date 2017年10月23日 下午4:33:07
 * @param <T>
 */
public interface BufferResource<T> {
	
	/**
	 * default buffer capacity
	 */
	public static final int DEFAULT_SIZE = 100;
	
	/**
	 * default lowest line
	 */
	public final static int DEFAULT_LOWEST = 10;
	
	/**
	 * default start line
	 */
	
	public final static int CONSUMER_LINE = 1;
	
	/**
	 * 生产方法
	 */
	public void produce();
	
	/**
	 * 消费方法
	 */
	public void comsumer();
}
