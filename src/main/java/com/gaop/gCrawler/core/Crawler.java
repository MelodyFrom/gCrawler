package com.gaop.gCrawler.core;

import java.util.Set;

import org.jsoup.nodes.Document;

/**
 * @description 	
 * 	爬虫接口,定义crawler统一执行的方法与部分常量
 * @author gaop
 * @date 2017年10月14日 下午9:29:16
 */
public interface Crawler {
	
	/**
	 * 定义爬取动作的方法,包含具体的爬取逻辑
	 */
	void fetch();
	
	/**
	 * 分页信息的获取与处理,在根页面拿到基础信息的同时应该将对应的分页信息抽取出来方便后续的爬取处理
	 * @param rootDoc
	 * @return
	 */
	Set<String> pageInfo(Document rootDoc);
	
	/**
	 * 为每一个初始化出来的crawler对象指定一个爬取动作的根页面
	 * @param rootUrl
	 */
	void setRootUrl(String rootUrl);
}

