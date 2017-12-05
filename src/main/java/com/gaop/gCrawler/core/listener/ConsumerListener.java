package com.gaop.gCrawler.core.listener;

import com.gaop.gCrawler.core.factory.impl.GroupBuyingResource;

/**
 * @description 	
 * 	消费线程监听器
 * @author gaop
 * @date 2017年10月25日 下午8:46:05
 * @param <T>
 */
public interface ConsumerListener<T> {

	/**
	 * default listener heartbeat
	 */
	public final static int DEFAULT_HEARTBEAT = 1000;
	
	/**
	 * default start line
	 */
	
	public final static int CONSUMER_LINE = 1;
	/**
	 * default batch number
	 */
	public final static int DEFAULT_BATCH_NUMBER = 10;
	
	void consumer(GroupBuyingResource<T> bufferResource);
}
