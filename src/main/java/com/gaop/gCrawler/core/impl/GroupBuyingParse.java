package com.gaop.gCrawler.core.impl;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gaop.gCrawler.core.Parser;
import com.gaop.gCrawler.core.entity.GroupBuyingEntity;

/**
 * @description 	
 * 	团购信息解析实现
 * @author gaop
 * @date 2017年10月15日 下午10:06:06
 */
public class GroupBuyingParse<T> implements Parser<GroupBuyingEntity>{

	@Override
	public GroupBuyingEntity parse(Document document) {
		if(null == document)
		{
			return new GroupBuyingEntity();
		}
		Element body = this.getBody(document);
		//get target area
		Elements elements = body.getElementsByClass("area-sub");
		return this.getGoodsFullName(elements);
	}
	
	/**
	 * 获取页面body模块
	 * @param document 页面文档对象
	 * @return
	 */
	private Element getBody(Document document) {
		return document.body();
	}
	

	private GroupBuyingEntity getGoodsFullName(Elements elements) {
		GroupBuyingEntity entity = new GroupBuyingEntity();
		//商品全名
		Element goodsInfo = elements.get(0);
		Elements goodsFullNameList = goodsInfo.getElementsByTag("h1");
		String goodsFullName = goodsFullNameList.get(0).childNode(0).toString();
		entity.setGoodsFullName(goodsFullName);
		
		//分类与品牌
		Elements brandAndCategory = goodsInfo.getElementsByTag("a");
		String brand = brandAndCategory.get(0).childNode(0).toString();
		String category = brandAndCategory.get(1).childNode(0).toString();
		entity.setBrand(brand);
		entity.setCategory(category);
		
		//价格相关
		Elements priceBox = goodsInfo.getElementsByClass("ljtg-box");
		Elements priceList = priceBox.get(0).getElementsByClass("price-list");
		Elements sPriceTags = priceList.get(0).getElementsByTag("s");
		Integer tuangouPrice = Integer.parseInt(sPriceTags.get(0).childNode(1).toString());
		Integer normalPrice = Integer.parseInt(sPriceTags.get(1).childNode(0).toString().substring(1));
		Float discount = Float.parseFloat(sPriceTags.get(2).childNode(0).toString());
		entity.setTuangouPrice(tuangouPrice);
		entity.setMarketPeice(normalPrice);
		entity.setDiscount(discount);
		
		//截止日期与关注人数
		Elements timeAndCountNumber = goodsInfo.getElementsByClass("last-time");
		Elements time = timeAndCountNumber.get(0).getElementsByTag("span");
		Elements countNumber =timeAndCountNumber.get(0).getElementsByTag("s");
		String date = time.get(0).childNode(0).toString();
		Integer counter = Integer.parseInt(countNumber.get(1).childNode(0).toString());
		entity.setFollowNumber(counter);
		entity.setEndTimeStr(date);
		
		//暗号
//		Elements anhao = goodsInfo.getElementsByClass("anhao");
//		String anhaoStr = anhao.get(0).childNode(0).toString();
//		String[] anhaoStrs = anhaoStr.split(" ");
//		entity.setSecretSignal(anhaoStrs[1]);
		System.out.println(entity);
		return entity;
		//店铺信息
//		Element shopInfo = elements.get(1);
//		Elements goingShop = shopInfo.getElementsByClass("going-shop-area");
//		String shopName = goingShop.get(0).getElementsByTag("a").get(0).childNode(0).toString();
//		String description = goingShop.get(1).getElementsByTag("s").get(0).childNode(0).toString();
//		String service = goingShop.get(1).getElementsByTag("s").get(1).childNode(0).toString();
//		String transportation = goingShop.get(1).getElementsByTag("s").get(2).childNode(0).toString();
//		System.out.println(shopName + description + service + transportation);
	}
}
