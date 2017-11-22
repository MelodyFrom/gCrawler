package com.gaop.gCrawler;

import java.io.IOException;

import com.gaop.gCrawler.core.Crawler;

public class demoMain {

	public static void main(String[] args) throws IOException, InterruptedException {
		Crawler craler = new Crawler();
		craler.setRootUrl("http://www.shihuo.cn/tuangou/list");
		craler.fetch();
	}
}
 