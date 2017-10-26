package com.gaop.gCrawler.core.entity;

import java.io.Serializable;
import java.util.Date;

public class GroupBuyingEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4981058791477779793L;

	/**
	 * 商品全称
	 */
	private String goodsFullName;

	/**
	 * 品牌
	 */
	private String brand;
	
	/**
	 * 分类
	 */
	private String category;
	
	/**
	 * 团购价
	 */
	private int tuangouPrice;
	
	/**
	 * 市场价
	 */
	private int marketPeice;
	
	/**
	 * 折扣
	 */
	private float discount;
	
	/**
	 * 关注人数
	 */
	private int followNumber;
	
	/**
	 * 团购暗号
	 */
	private String secretSignal;
	
	/**
	 * 团购截止时间
	 */
	private Date endTime;
	
	private String endTimeStr;
	
	/**
	 * 店铺名
	 */
	private String shopName;
	
	/**
	 * 店铺描述
	 */
	private float shopDescription;
	
	/**
	 * 店铺服务
	 */
	private float service;
	
	/**
	 * 物流
	 */
	private float transportation;

	public String getGoodsFullName() {
		return goodsFullName;
	}

	public void setGoodsFullName(String goodsFullName) {
		this.goodsFullName = goodsFullName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getTuangouPrice() {
		return tuangouPrice;
	}

	public void setTuangouPrice(int tuangouPrice) {
		this.tuangouPrice = tuangouPrice;
	}

	public int getMarketPeice() {
		return marketPeice;
	}

	public void setMarketPeice(int marketPeice) {
		this.marketPeice = marketPeice;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getFollowNumber() {
		return followNumber;
	}

	public void setFollowNumber(int followNumber) {
		this.followNumber = followNumber;
	}

	public String getSecretSignal() {
		return secretSignal;
	}

	public void setSecretSignal(String secretSignal) {
		this.secretSignal = secretSignal;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public float getShopDescription() {
		return shopDescription;
	}

	public void setShopDescription(float shopDescription) {
		this.shopDescription = shopDescription;
	}

	public float getService() {
		return service;
	}

	public void setService(float service) {
		this.service = service;
	}

	public float getTransportation() {
		return transportation;
	}

	public void setTransportation(float transportation) {
		this.transportation = transportation;
	}

	@Override
	public String toString() {
		return "GroupBuyingEntity [goodsFullName=" + goodsFullName + ", brand=" + brand + ", category=" + category
				+ ", tuangouPrice=" + tuangouPrice + ", marketPeice=" + marketPeice + ", discount=" + discount
				+ ", followNumber=" + followNumber + ", secretSignal=" + secretSignal + ", endTime=" + endTime
				+ ", endTimeStr=" + endTimeStr + ", shopName=" + shopName + ", shopDescription=" + shopDescription
				+ ", service=" + service + ", transportation=" + transportation + "]";
	}
	
}
