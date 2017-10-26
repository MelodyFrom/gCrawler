package com.gaop.gCrawler.core;

/**
 * @description 	
 * 	数据爬取配置类,爬取的配置策略生成于该接口的实现类,接口本身只指定一个基础的爬取策略,不同的实现可以实现自己的定制信息
 * @author gaop
 * @date 2017年8月29日 上午12:10:59
 */
public interface GrabConfig {
	
	/**
	 * 默认多线程运行线程数
	 */
	public static int DEFAULT_THREAD_NUMBER = 10;

	/**
	 * 初始化一个爬取策略配置类
	 * @return
	 */
	void iniConfig();
}
