package com.gaop.gCrawler.core.listener;

import com.gaop.gCrawler.core.factory.BufferResource;

/**
 * @description 	
 * 	生产线程监听器
 * @author gaop
 * @date 2017年10月24日 下午9:47:20
 */
public interface ProduceListener<T> {

	/**
	 * default listener heartbeat
	 */
	public final static int DEFAULT_HEARTBEAT = 1000;
	
	/**
	 * default lowest line
	 */
	public final static int DEFAULT_LOWEST = 100;
	
	/**
	 * default batch number
	 */
	public final static int DEFAULT_BATCH_NUMBER = 40;
	
	void lisnten(BufferResource<T> bufferResource);
}
