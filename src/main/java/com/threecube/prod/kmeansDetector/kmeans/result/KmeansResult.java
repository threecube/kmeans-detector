/*
 * @(#)KmeansResult.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.result;

import java.util.List;
import java.util.Map;

import com.threecube.prod.kmeansDetector.kmeans.model.NormalizedPoint;

/**
 * @author Mike
 */
/**
 * @author Mike
 */
public class KmeansResult {

	/** if success*/
	private boolean isSuccess;

	/** total number of data*/
	private long dataNum;

	/** total time be consumed by k-means*/
	private long consTime;

	/** loop times */
	private int loopTimes;

	/** clustered points, key:ID, value: type*/
	private Map<Long, Integer> pointTypeMap;

	/** centroids be chosed*/
	private List<NormalizedPoint> centroids;

	public KmeansResult() {
		this.isSuccess = false;
		this.dataNum = 0l;
		this.consTime = 0l;
		this.loopTimes = 0;
		this.pointTypeMap = null;
		this.centroids = null;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public long getDataNum() {
		return dataNum;
	}

	public void setDataNum(long dataNum) {
		this.dataNum = dataNum;
	}

	public long getConsTime() {
		return consTime;
	}

	public void setConsTime(long consTime) {
		this.consTime = consTime;
	}

	public Map<Long, Integer> getPointTypeMap() {
		return pointTypeMap;
	}

	public void setPointTypeMap(Map<Long, Integer> pointTypeMap) {
		this.pointTypeMap = pointTypeMap;
	}

	public List<NormalizedPoint> getCentroids() {
		return centroids;
	}

	public void setCentroids(List<NormalizedPoint> centroids) {
		this.centroids = centroids;
	}

	public int getLoopTimes() {
		return loopTimes;
	}

	public void setLoopTimes(int loopTimes) {
		this.loopTimes = loopTimes;
	}

}
