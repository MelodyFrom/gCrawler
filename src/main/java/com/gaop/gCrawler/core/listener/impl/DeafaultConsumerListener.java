package com.gaop.gCrawler.core.listener.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.gaop.gCrawler.core.GrabConfig;
import com.gaop.gCrawler.core.entity.GroupBuyingEntity;
import com.gaop.gCrawler.core.factory.Consumer;
import com.gaop.gCrawler.core.factory.GroupBuyingResource;
import com.gaop.gCrawler.core.listener.ConsumerListener;

/**
 * @description 	
 * 	消费者线程监听器,用于监听资源池规模并在一个合适的节点消费资源
 * @author gaop
 * @date 2017年10月25日 下午8:50:46
 */
public class DeafaultConsumerListener implements ConsumerListener<GroupBuyingEntity>{

	/**
	 * 监听心跳,单位:毫秒
	 */
	private int heartBeat;
	
	/**
	 * 目标资源池
	 */
	private GroupBuyingResource<GroupBuyingEntity> groupBuyingResource;
	
	public DeafaultConsumerListener()
	{
		this.heartBeat = DEFAULT_HEARTBEAT;
	}
	
	public DeafaultConsumerListener(int heartBeat)
	{
		this.heartBeat = heartBeat;
	}
	
	public DeafaultConsumerListener(int startLine, int heartBeat)
	{
		this(heartBeat);
	}
	
	static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(GrabConfig.DEFAULT_THREAD_NUMBER);  
	
	@Override
	public void consumer(GroupBuyingResource<GroupBuyingEntity> bufferResource) {
		this.groupBuyingResource = bufferResource;
		scheduler.scheduleAtFixedRate(new Consumer<GroupBuyingEntity>(groupBuyingResource), 1, heartBeat, TimeUnit.MILLISECONDS);
	}

}
