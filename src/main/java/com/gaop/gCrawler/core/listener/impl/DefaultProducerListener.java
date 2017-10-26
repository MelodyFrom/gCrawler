package com.gaop.gCrawler.core.listener.impl;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaop.gCrawler.core.GrabConfig;
import com.gaop.gCrawler.core.entity.GroupBuyingEntity;
import com.gaop.gCrawler.core.factory.GroupBuyingResource;
import com.gaop.gCrawler.core.factory.Producer;
import com.gaop.gCrawler.core.listener.ProduceListener;

/**
 * @description 	
 * 	默认实现的一个生产者监听器,这个监听器用来监听当前资源池规模,当可用资源达到下限就启动生产者线程向资源池注入
 * 新的资源
 * @author gaop
 * @date 2017年10月24日 下午9:54:04
 */
public class DefaultProducerListener implements ProduceListener<GroupBuyingEntity>{
	
	private Logger logger = LoggerFactory.getLogger(DefaultProducerListener.class);
	
	/**
	 * 监听资源池下限
	 */
	private int lowestLine;
	
	/**
	 * 监听心跳
	 */
	private int heartBeat;
	
	/**
	 * 批量执行
	 */
	private int batchNumber;
	
	/**
	 * 目标资源池
	 */
	private GroupBuyingResource<GroupBuyingEntity> groupBuyingResource;
	
	public DefaultProducerListener() {
		this.lowestLine = DEFAULT_LOWEST;
		this.heartBeat = DEFAULT_HEARTBEAT;
		this.batchNumber = DEFAULT_BATCH_NUMBER;
	}
	
	public DefaultProducerListener(int lowest, int heatBeat) 
	{
		this.lowestLine = lowest;
		this.heartBeat = heatBeat;
		this.batchNumber = DEFAULT_BATCH_NUMBER;
	}
	
	public DefaultProducerListener(int lowest, int heatBeat, int batchNumber) 
	{
		this(lowest, heatBeat);
		this.batchNumber = batchNumber;
	}
	
	static ExecutorService service = Executors.newFixedThreadPool(GrabConfig.DEFAULT_THREAD_NUMBER);

	@Override
	public void lisnten(GroupBuyingResource<GroupBuyingEntity> bufferResource) {
		this.groupBuyingResource = bufferResource;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new ListenerTask(), 10, heartBeat);
	}
	
	class ListenerTask extends TimerTask {

		@Override
		public void run() {
			if(groupBuyingResource.getList().size() <= lowestLine)
			{
				logger.info("生产者监听=>当前资源池容量{}", groupBuyingResource.getList().size());
				this.batchExecute(batchNumber);
			}
		}
		
		/**
		 * 批量插入
		 * @param batch
		 */
		private void batchExecute(int batch)
		{
			for(int i = 0; i < batch; i++)
			{
				service.execute(new Producer<GroupBuyingEntity>(groupBuyingResource));
			}
		}
		
	}

}
