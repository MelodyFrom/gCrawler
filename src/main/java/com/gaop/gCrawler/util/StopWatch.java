package com.gaop.gCrawler.util;

/**
 * @description
 * 	计时器
 * @date 2017年8月3日 下午2:51:01
 * @author gaopeng@danlu.com
 */
public class StopWatch {

	private final long initMillis;
	
	public StopWatch() {
		this.initMillis = System.currentTimeMillis();
	}
	
	/**
	 * 返回自该计时器被创建起到使用该方法是所消耗的时间,单位:秒
	 * @return
	 */
	public double elapsedTime() {
		long currentMillis = System.currentTimeMillis();
		return (currentMillis - initMillis) / 1000.0;
	}
}
