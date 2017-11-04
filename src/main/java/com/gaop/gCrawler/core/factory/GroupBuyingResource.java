package com.gaop.gCrawler.core.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 	
 * 	团购生产-消费者模型;默认消费下限1,生产下线10
 * @author gaop
 * @date 2017年11月2日 下午9:17:07
 * @param <T>
 */
public class GroupBuyingResource<T> implements BufferResource<T>{

	private Logger logger = LoggerFactory.getLogger(GroupBuyingResource.class);
	
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
	 * initial size
	 */
	private int bufferSize;
	
	/**
	 * 消费数量统计
	 */
	private int consumerCount;
	
	/**
	 * 生产数量统计
	 */
	private int produceCount;
	
	/**
	 * 消费起点线
	 */
	private int consumerLine;
	
	/**
	 * 生产起点线
	 */
	private int lowestLine;
	
	/**
	 * 消费总目标数量
	 */
	private int targetNumber;
	
	/**
	 * 生产批量
	 */
	private int produceBatch = 10;
	
	/**
	 * 消费批量
	 */
	private int consumerBatch = 10;
	
	private List<String> urls = new ArrayList<>();
	
	private LinkedList<Document> list = new LinkedList<>();
	
	/**
	 * get resource initial capacity
	 * @return size
	 */
	public int getBufferSize() {
		return this.bufferSize;
	}
	
	public GroupBuyingResource() {
		this.bufferSize = DEFAULT_SIZE;
		this.consumerLine = CONSUMER_LINE;
		this.lowestLine = DEFAULT_LOWEST;
	}
	
	/**
	 * @param size 资源池容量
	 */
	public GroupBuyingResource(int size) {
		this.bufferSize = size;
	}
	
	/**
	 * @description 
	 * 	默认生产下限 10; 消费起点线 1
	 * @param size 资源池容量
	 * @param urls 爬取目标url
	 */
	public GroupBuyingResource(int size, List<String> urls, int batch) {
		this(size);
		this.setUrls(urls);//set target urls
		this.targetNumber = urls.size();
		this.lowestLine = DEFAULT_LOWEST;
		this.consumerLine = CONSUMER_LINE;
		this.produceBatch = batch;
		this.consumerBatch = batch;
	}
	
	/**
	 * @param size 资源池容量
	 * @param urls 爬取目标url列表
	 * @param lowest 生产下限
	 * @param consumerLine 消费起点线
	 */
	public GroupBuyingResource(int size, List<String> urls, int lowest, int consumerLine, int batch) {
		this(size);
		this.setUrls(urls);//set target urls
		this.targetNumber = urls.size();
		this.lowestLine = lowest;
		this.consumerLine = consumerLine;
		this.produceBatch = batch;
		this.consumerBatch = batch;
	}

	public void produce() {
		synchronized (list) 
		{
			if(bufferSize <= list.size() + 1)
			{
				logger.info("资源池容量已达上限, 延缓执行生产任务...");
				try {
					list.wait();
				} catch (InterruptedException ex) {
					logger.error("生产方法异常", ex);
				}
			}
			if(targetNumber == produceCount)
			{
				//生产目标已完成
				logger.info("完成生产任务...");
				return;
			}
			//条件满足
			try {
				if(CollectionUtils.isEmpty(urls))
				{
					return;
				}
				//判断资源池生产起点下限
				if(list.size() < lowestLine)
				{
					for(int batch = 0; batch < this.produceBatch; batch++) {
						list.add(Jsoup.connect(urls.remove(0)).get());
						++produceCount;
					}
					logger.info("生产者监听=>当前资源池容量{}, 生产总量{}", list.size(), produceCount);
					list.notifyAll();
				}
			} catch (IOException ex) {
				logger.error("爬取页面信息异常", ex);
			}
		}
	}

	public void comsumer() {
		synchronized (list) 
		{
			try {
				if(list.size() <= 0)
				{
					logger.info("当前资源池为空,延缓执行消费任务...");
					list.wait();
				}
			} catch (InterruptedException ex) {
				logger.error("消费方法异常", ex);
			}
			//条件满足
			//判断资源池消费下限
			if(list.size() >= consumerLine)
			{
				for(int batch = 0; batch < this.consumerBatch; batch++)
				{
					list.poll();
					consumerCount++;
				}
			}
			logger.info("消费对象：,消费数量统计{},当前资源池容量{}", consumerCount, list.size());
		}
	}
	
	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public LinkedList<Document> getList() {
		return list;
	}

	public void setList(LinkedList<Document> list) {
		this.list = list;
	}

}
