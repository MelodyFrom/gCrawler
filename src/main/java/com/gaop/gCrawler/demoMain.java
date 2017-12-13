package com.gaop.gCrawler;

import java.io.IOException;

import com.gaop.gCrawler.core.Crawler;
import com.gaop.gCrawler.core.impl.GroupBuyingCrawler;

/**
 * @description
 * 	爬取示例
 * @date 2017年12月13日 下午3:50:16
 * @author gaopeng@danlu.com
 */
public class demoMain {

	public static void main(String[] args) throws IOException, InterruptedException {
		//创建爬虫对象
		Crawler craler = new GroupBuyingCrawler();
		//设置爬取根页面
		craler.setRootUrl("http://www.shihuo.cn/tuangou/list");
		craler.fetch();
	}
}
 