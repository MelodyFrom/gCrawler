package com.gaop.gCrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;

import com.gaop.gCrawler.core.Crawler;
import com.gaop.gCrawler.core.Parser;
import com.gaop.gCrawler.core.entity.GroupBuyingEntity;
import com.gaop.gCrawler.core.impl.GroupBuyingParse;

public class demoMain {

	public static void main(String[] args) throws IOException, InterruptedException {
		Crawler craler = new Crawler();
		craler.setRootUrl("http://www.shihuo.cn/tuangou/list");
		List<Document> docs = craler.fetch();
//		Thread.sleep(10000);
//		System.out.println("开始解析...");
		Parser<GroupBuyingEntity> parser = new GroupBuyingParse<GroupBuyingEntity>();
//		List<GroupBuyingEntity> entitys = new ArrayList<>();
//		for(Document document : docs)
//		{
//			entitys.add(parser.parse(document));
//		}
	}
}
