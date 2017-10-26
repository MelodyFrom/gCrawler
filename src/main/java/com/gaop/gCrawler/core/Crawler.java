package com.gaop.gCrawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaop.gCrawler.core.entity.GroupBuyingEntity;
import com.gaop.gCrawler.core.factory.GroupBuyingResource;
import com.gaop.gCrawler.core.impl.DefaultGrabConfig;
import com.gaop.gCrawler.core.listener.impl.DeafaultConsumerListener;
import com.gaop.gCrawler.core.listener.impl.DefaultProducerListener;
import com.gaop.gCrawler.util.StopWatch;

/**
 * @description 	
 * 	实例化爬虫类
 * @author gaop
 * @date 2017年10月14日 下午9:29:16
 */
public class Crawler {
	
	private Logger logger = LoggerFactory.getLogger(Crawler.class);
	
	/**
	 * basic configuration class
	 */
	GrabConfig grabConfig;
	
	/**
	 * the root fetch page 
	 */
	private String rootUrl;

	public Crawler() {
		//each default construct method has a default configuration
		this.grabConfig = new DefaultGrabConfig();
	}
	
	static ExecutorService service = Executors.newFixedThreadPool(GrabConfig.DEFAULT_THREAD_NUMBER);
	
	public Crawler(GrabConfig config) {
		//get a instance by your own configuration
		this.grabConfig = config;
	}
	
	public List<Document> fetch() {
		List<Document> documents = new Vector<>();
		try {
			if(StringUtils.isBlank(rootUrl))
			{
				throw new IOException("Your rootUrl is blank, please set your rootUrl first.");
			}
			StopWatch watch = new StopWatch();
			Document rootDoc = Jsoup.connect(rootUrl).get();
			//find the list linked page from root page
			Elements elements = rootDoc.getElementsByClass("inner");
			List<String> urls = new ArrayList<>();
			for(Element element : elements) 
			{
				Elements linkElements = element.getElementsByTag("a");
				Elements href = linkElements.get(1).getElementsByAttribute("href");
				String hrefUrl = href.get(0).attr("href");
				urls.add(hrefUrl);
			}
			//fetch these pages via Producer-Consumer-Model
			GroupBuyingResource<GroupBuyingEntity> resource = new GroupBuyingResource<>(GroupBuyingResource.DEFAULT_SIZE, urls);
			//create a producer listener
			DefaultProducerListener listener = new DefaultProducerListener();
			listener.lisnten(resource);
			//create a consumer listener
			DeafaultConsumerListener consumer = new DeafaultConsumerListener();
			consumer.consumer(resource);
			logger.info("爬取页面耗时:{}秒,爬取页面数量:{}", watch.elapsedTime(), resource.getList().size());
			return resource.getList();
		} catch (IOException ex) {
			logger.error("爬取目标页面IO异常", ex);
			return documents;
		} catch (Exception ex) {
			logger.error("爬取页面异常", ex);
		}
		return documents;
	}

	public String getRootUrl() {
		return rootUrl;
	}


	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
}
