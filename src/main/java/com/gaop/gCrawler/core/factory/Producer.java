package com.gaop.gCrawler.core.factory;

/**
 * @description 	
 * 	生产者对象
 * @author gaop
 * @date 2017年10月23日 下午5:08:46
 * @param <T>
 */
public class Producer<T> implements Runnable{

	/**
	 * 生产资料目标资源池
	 */
	private BufferResource<T> resource;
	
	public Producer(){}
	
	public Producer(BufferResource<T> resource){
		this.resource = resource;
	}

	public void setResource(BufferResource<T> resource) {
		this.resource = resource;
	}

	@Override
	public void run() {
		resource.produce();
	}
}
