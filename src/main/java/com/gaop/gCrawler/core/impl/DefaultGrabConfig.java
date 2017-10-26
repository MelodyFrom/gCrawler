package com.gaop.gCrawler.core.impl;

import com.gaop.gCrawler.core.GrabConfig;

/**
 * @description 	
 * 	默认的爬取策略实现类,包含部分初始化信息
 * @author gaop
 * @date 2017年8月29日 上午12:17:00
 */
public class DefaultGrabConfig  implements GrabConfig{
	
	/**
	 * 是不是仅仅爬取一个页面,默认为仅爬取一个页面信息的数据
	 */
	private boolean isSinglePage;
	
	public DefaultGrabConfig() {
		this.iniConfig();
	}

	/**
	 * @param rootUrl 爬取目标的初始页面
	 */
	public DefaultGrabConfig(String rootUrl) {
		this.iniConfig();
	}

	@Override
	public void iniConfig() {
		isSinglePage = true;//默认值为true即仅爬取一个页面信息
	}

}
