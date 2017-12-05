package com.gaop.gCrawler.core.listener.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.gaop.gCrawler.core.GrabConfig;
import com.gaop.gCrawler.core.entity.GroupBuyingEntity;
import com.gaop.gCrawler.core.factory.Producer;
import com.gaop.gCrawler.core.factory.impl.GroupBuyingResource;
import com.gaop.gCrawler.core.listener.ProduceListener;

/**
 * @description 	
 * 	默认实现的一个生产者监听器,这个监听器用来监听当前资源池规模,当可用资源达到下限就启动生产者线程向资源池注入
 * 新的资源
 * @author gaop
 * @date 2017年10月24日 下午9:54:04
 */
public class DefaultProducerListener implements ProduceListener<GroupBuyingEntity>{
	
	/**
	 * 监听心跳
	 */
	private int heartBeat;
	
	/**
	 * 目标资源池
	 */
	private GroupBuyingResource<GroupBuyingEntity> groupBuyingResource;
	
	public DefaultProducerListener() {
		this.heartBeat = DEFAULT_HEARTBEAT;
	}
	
	public DefaultProducerListener(int lowest, int heatBeat) 
	{
		this.heartBeat = heatBeat;
	}
	
	public DefaultProducerListener(int lowest, int heatBeat, int batchNumber) 
	{
		this(lowest, heatBeat);
	}
	
	static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(GrabConfig.DEFAULT_THREAD_NUMBER);  
	
	@Override
	public void lisnten(GroupBuyingResource<GroupBuyingEntity> bufferResource) {
		this.groupBuyingResource = bufferResource;
		scheduler.scheduleAtFixedRate(new Producer<GroupBuyingEntity>(groupBuyingResource), 0, heartBeat, TimeUnit.MILLISECONDS);
	}
	
}

