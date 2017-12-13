package com.gaop.gCrawler.core.factory;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * @description 	
 * 	消费者对象
 * @author gaop
 * @date 2017年10月25日 上午12:03:23
 * @param <T>
 */
public class Consumer<T> implements Runnable {
	
	/**
	 * 消费目标资源池
	 */
	private BufferResource<T> resource;

	public void setResource(BufferResource<T> resource) {
		this.resource = resource;
	}
	
	public Consumer(){}
	
	public Consumer(BufferResource<T> resource) {
		this.resource = resource;
	}

	@Override
	public void run() {
		resource.comsumer();
	}

}
