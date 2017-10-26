package com.gaop.gCrawler.core.listener.impl;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private Logger logger = LoggerFactory.getLogger(DeafaultConsumerListener.class);
	
	/**
	 * 消费资源起点线
	 */
	private int consumerLine;
	
	/**
	 * 监听心跳,单位:毫秒
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
	
	public DeafaultConsumerListener()
	{
		this.consumerLine = CONSUMER_LINE;
		this.heartBeat = DEFAULT_HEARTBEAT;
		this.batchNumber = DEFAULT_BATCH_NUMBER;
	}
	
	public DeafaultConsumerListener(int startLine, int heartBeat)
	{
		this.consumerLine = startLine;
		this.heartBeat = heartBeat;
		this.batchNumber = DEFAULT_BATCH_NUMBER;
	}
	
	public DeafaultConsumerListener(int startLine, int heartBeat, int batchNumber)
	{
		this(startLine, heartBeat);
		this.batchNumber = batchNumber;
	}
	
	static ExecutorService service = Executors.newFixedThreadPool(GrabConfig.DEFAULT_THREAD_NUMBER);
	
	@Override
	public void consumer(GroupBuyingResource<GroupBuyingEntity> bufferResource) {
		this.groupBuyingResource = bufferResource;
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new ListenerTask(), 10, heartBeat);//资源数量大于一即消费
	}
	
	class ListenerTask extends TimerTask {

		@Override
		public void run() {
			if(groupBuyingResource.getList().size() >= consumerLine)
			{
				logger.info("消费者监听=>当前资源池容量{}", groupBuyingResource.getList().size());
				this.batchExecute(batchNumber);
			}
		}
		
		/**
		 * 批量执行方法
		 * @param batch
		 */
		private void batchExecute(int batch)
		{
			for(int i = 0; i < batch; i++)
			{
				service.execute(new Consumer<GroupBuyingEntity>(groupBuyingResource));
			}
		}
		
	}

}
