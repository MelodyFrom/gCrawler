package com.gaop.gCrawler.core.impl;

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

import com.gaop.gCrawler.core.Crawler;
import com.gaop.gCrawler.core.GrabConfig;
import com.gaop.gCrawler.core.entity.GroupBuyingEntity;
import com.gaop.gCrawler.core.factory.impl.GroupBuyingResource;
import com.gaop.gCrawler.core.listener.impl.DeafaultConsumerListener;
import com.gaop.gCrawler.core.listener.impl.DefaultProducerListener;

/**
 * @description
 * 	团购页面crawler具体实现
 * @date 2017年12月13日 下午3:35:58
 * @author gaopeng@danlu.com
 */
public class GroupBuyingCrawler implements Crawler{

	private Logger logger = LoggerFactory.getLogger(Crawler.class);
	
	/**
	 * basic configuration class
	 */
	GrabConfig grabConfig;
	
	/**
	 * the root fetch page 
	 */
	private String rootUrl;

	public GroupBuyingCrawler() {
		//each default construct method has a default configuration
		this.grabConfig = new DefaultGrabConfig();
	}
	
	public GroupBuyingCrawler(GrabConfig config) {
		//get a instance by your own configuration
		this.grabConfig = config;
	}
	
	public String getRootUrl() {
		return rootUrl;
	}

	@Override
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	/**
	 * 爬取方法
	 * @throws IOException 
	 */
	@Override
	public void fetch(){
		try {
			if(StringUtils.isBlank(rootUrl))
			{
				throw new IOException("Your rootUrl is blank, please set your rootUrl first.");
			}
			Document rootDoc = Jsoup.connect(rootUrl).get();
			//get page info
			Set<String> pageUrls = this.pageInfo(rootDoc);
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
	@Override
	public Set<String> pageInfo(Document rootDoc) {
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
