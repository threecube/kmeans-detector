/*
 * @(#)KmeansAnalyzResult.java $version 2015-1-28
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.result;

import java.util.List;

/**
 * result of the k-means analyzer, which give accuracy rate of k-means 
 * 
 * @author Mike
 */
public class KmeansAnalyzResult {

	/** total number of all training data*/
	private long totalDataNum;

	/** accuracy rate of all training data*/
	private float totalAccuRate;

	/** each type's accuracy rate information*/
	private List<AccuracyRate> accuRateOfType;

	public long getTotalDataNum() {
		return totalDataNum;
	}

	public void setTotalDataNum(long totalDataNum) {
		this.totalDataNum = totalDataNum;
	}

	public float getTotalAccuRate() {
		return totalAccuRate;
	}

	public void setTotalAccuRate(float totalAccuRate) {
		this.totalAccuRate = totalAccuRate;
	}

	public List<AccuracyRate> getAccuRateOfType() {
		return accuRateOfType;
	}

	public void setAccuRateOfType(List<AccuracyRate> accuRateOfType) {
		this.accuRateOfType = accuRateOfType;
	}

}
