package com.gaop.gCrawler.core;

import org.jsoup.nodes.Document;

/**
 * @description 	
 * 	页面内容解析接口
 * @author gaop
 * @date 2017年10月15日 下午9:54:15
 */
public interface Parser<T> {
	
	/**
	 * 解析目标页面并获得一个内容填充完毕的特定对象,所需数据可以封装在对象中
	 * @param document
	 * @return
	 */
	T parse(Document document);
}
