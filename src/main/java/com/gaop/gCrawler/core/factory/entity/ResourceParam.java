package com.gaop.gCrawler.core.factory.entity;

/**
 * @description 
 * 	资源池共用参数封装	
 * @author gaop
 * @date 2017年11月21日 下午11:10:56
 */
public class ResourceParam {
	
	/**
	 * default batch execute size
	 */
	public final static int BATCH_SIZE = 10;

	/**
	 * initial size
	 */
	private int bufferSize;
	
	/**
	 * 消费数量统计
	 */
	private int consumerCount;
	
	/**
	 * 生产数量统计
	 */
	private int produceCount;
	
	/**
	 * 消费起点线
	 */
	private int consumerLine;
	
	/**
	 * 生产起点线
	 */
	private int lowestLine;
	
	/**
	 * 消费总目标数量
	 */
	private int targetNumber;
	
	/**
	 * 生产批量-默认值为10
	 */
	private int produceBatch = BATCH_SIZE;
	
	/**
	 * 消费批量-默认值为10
	 */
	private int consumerBatch = BATCH_SIZE;

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public int getConsumerCount() {
		return consumerCount;
	}

	public void setConsumerCount(int consumerCount) {
		this.consumerCount = consumerCount;
	}

	public int getProduceCount() {
		return produceCount;
	}

	public void setProduceCount(int produceCount) {
		this.produceCount = produceCount;
	}

	public int getConsumerLine() {
		return consumerLine;
	}

	public void setConsumerLine(int consumerLine) {
		this.consumerLine = consumerLine;
	}

	public int getLowestLine() {
		return lowestLine;
	}

	public void setLowestLine(int lowestLine) {
		this.lowestLine = lowestLine;
	}

	public int getTargetNumber() {
		return targetNumber;
	}

	public void setTargetNumber(int targetNumber) {
		this.targetNumber = targetNumber;
	}

	public int getProduceBatch() {
		return produceBatch;
	}

	public void setProduceBatch(int produceBatch) {
		this.produceBatch = produceBatch;
	}

	public int getConsumerBatch() {
		return consumerBatch;
	}

	public void setConsumerBatch(int consumerBatch) {
		this.consumerBatch = consumerBatch;
	}

	@Override
	public String toString() {
		return "ResourceParam [bufferSize=" + bufferSize + ", consumerCount=" + consumerCount + ", produceCount="
				+ produceCount + ", consumerLine=" + consumerLine + ", lowestLine=" + lowestLine + ", targetNumber="
				+ targetNumber + ", produceBatch=" + produceBatch + ", consumerBatch=" + consumerBatch + "]";
	}
	
}
