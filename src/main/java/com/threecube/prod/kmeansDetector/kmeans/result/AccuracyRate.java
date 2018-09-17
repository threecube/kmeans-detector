/*
 * @(#)AccuracyRate.java $version 2015-1-28
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.result;

import java.util.Map;

/**
 * accuracy rate of each real type
 * 
 * @author Mike
 */
public class AccuracyRate {

	/** real type which is given by original training data*/
	private int realType;

	/** clustered type which is given by clustered data*/
	private int clusteredType;

	/** accuracy rate, calculate by accuDataNum / (accuDataNum + errDataNumMap)*/
	private float accuRate;

	/** the number of accuracy clustered data*/
	private long accuDataNum;

	/** the number of error clustered data*/
	private Map<Integer, Long> errDataNumMap;

	public int getRealType() {
		return realType;
	}

	public void setRealType(int realType) {
		this.realType = realType;
	}

	public int getClusteredType() {
		return clusteredType;
	}

	public void setClusteredType(int clusteredType) {
		this.clusteredType = clusteredType;
	}

	public float getAccuRate() {
		return accuRate;
	}

	public void setAccuRate(float accuRate) {
		this.accuRate = accuRate;
	}

	public long getAccuDataNum() {
		return accuDataNum;
	}

	public void setAccuDataNum(long accuDataNum) {
		this.accuDataNum = accuDataNum;
	}

	public Map<Integer, Long> getErrDataNumMap() {
		return errDataNumMap;
	}

	public void setErrDataNumMap(Map<Integer, Long> errDataNumMap) {
		this.errDataNumMap = errDataNumMap;
	}

}
