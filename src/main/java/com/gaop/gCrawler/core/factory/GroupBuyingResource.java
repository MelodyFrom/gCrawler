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

public class GroupBuyingResource<T> implements BufferResource<T>{

	private Logger logger = LoggerFactory.getLogger(GroupBuyingResource.class);
	
	/**
	 * default buffer capacity
	 */
	public static final int DEFAULT_SIZE = 100;
	
	/**
	 * initial size
	 */
	private int bufferSize;
	
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
	}
	
	public GroupBuyingResource(int size) {
		this.bufferSize = size;
	}
	
	public GroupBuyingResource(int size, List<String> urls) {
		this(size);
		this.setUrls(urls);//set target urls
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
			//条件满足
			try {
				if(CollectionUtils.isEmpty(urls))
				{
					return;
				}
				list.add(Jsoup.connect(urls.remove(0)).get());
				list.notifyAll();
			} catch (IOException ex) {
				logger.error("爬取页面信息异常", ex);
			}
		}
	}

	public void comsumer() {
		synchronized (list) 
		{
			if(list.size() <= 0)
			{
				logger.info("当前资源池为空,延缓执行消费任务...");
			}
			try {
				list.wait();
			} catch (InterruptedException ex) {
				logger.error("消费方法异常", ex);
			}
			//条件满足
			if(CollectionUtils.isEmpty(list))
			{
				return;
			}
			logger.info("消费对象：", list.poll().toString());
//			GroupBuyingEntity entity = list.remove(0);
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
