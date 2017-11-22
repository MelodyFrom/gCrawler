package com.gaop.gCrawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
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
	
	public Crawler(GrabConfig config) {
		//get a instance by your own configuration
		this.grabConfig = config;
	}
	
	public String getRootUrl() {
		return rootUrl;
	}


	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	/**
	 * 爬取方法
	 * @throws IOException 
	 */
	public void fetch() throws IOException {
		try {
			if(StringUtils.isBlank(rootUrl))
			{
				throw new IOException("Your rootUrl is blank, please set your rootUrl first.");
			}
			Document rootDoc = Jsoup.connect(rootUrl).get();
			//get page info
			Set<String> pageUrls = this.pageUtils(rootDoc);
			List<String> urls = new ArrayList<>();
			if(CollectionUtils.isNotEmpty(pageUrls))
			{
				for(String pageUrl : pageUrls)
				{
					Document pageDoc = Jsoup.connect(pageUrl).get();
					//find the list linked page from root page
					Elements elements = pageDoc.getElementsByClass("inner");
					for(Element element : elements) 
					{
						Elements linkElements = element.getElementsByTag("a");
						Elements href = linkElements.get(1).getElementsByAttribute("href");
						String hrefUrl = href.get(0).attr("href");
						urls.add(hrefUrl);
					}				
				}
			}
			//fetch these pages via Producer-Consumer-Model
			GroupBuyingResource<GroupBuyingEntity> resource = new GroupBuyingResource<>(GroupBuyingResource.DEFAULT_SIZE, urls, 10);
			//create a producer listener
			DefaultProducerListener listener = new DefaultProducerListener();
			listener.lisnten(resource);
			//create a consumer listener
			DeafaultConsumerListener consumer = new DeafaultConsumerListener();
			consumer.consumer(resource);
		} catch (IOException ex) {
			String errorMsg = "爬取目标页面IO异常";
			logger.error(errorMsg, ex);
			throw new RuntimeException(errorMsg);
		} catch (Exception ex) {
			String errorMsg = "爬取页面异常";
			logger.error(errorMsg, ex);
			throw new RuntimeException(errorMsg);
			
		}
	}
	
	
	/**
	 * 解析根页面分页信息
	 * @param rootUrl
	 */
	private Set<String> pageUtils(Document rootDoc) {
		Set<String> pageUrl = new HashSet<>();
		Elements aTagElements = rootDoc.getElementsByClass("pages-list").get(0).getElementsByTag("a");
		for(Element element : aTagElements) 
		{
			element.text();
			pageUrl.add(element.attr("href"));
		}
		return pageUrl;
	}
	
}

