/*
 * @(#)PointModel.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * property model which is used by k-means
 * 
 * @author Mike
 */
public class PointModel {

	private static final Gson gson = new Gson();

	/** id*/
	private long id;

	/** service name*/
	private String service;

	/** property : request number of current time unit*/
	private long reqNum;

	/** property : IP number of current time unit*/
	private long ipNum;

	/** property : top url hits of current time unit*/
	private long topUrlHits;

	//	/** property : total request number*/
	//	private long totalReqNum;

	/** property : the maximum request number*/
	private long maxReqNum;

	/** property : the maximum IP number*/
	private long maxIPNum;

	private long maxTopUrlHits;

	/** property : the maximum value of differences between two adjacent point*/
	private long maxDiffForReq;

	/** property : the maximum value of differences between two adjacent point*/
	private long maxDiffForIp;

	private long maxDiffForTopUrl;

	//	/** property : total IP number*/
	//	private long totalIpNum;	

	//	/** property : sum of all time unit*/
	//	private long totalTopUrlHits;

	//	/** property : the average value of request number for IP*/
	//	private long aveReqNumForIp;

	/** property : the time of request, microsecond*/
	private long gmtCreate;

	/** the type of point , initialized to -1*/
	private int type;

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	//	public long getTotalReqNum() {
	//		return totalReqNum;
	//	}
	//
	//	public void setTotalReqNum(long totalReqNum) {
	//		this.totalReqNum = totalReqNum;
	//	}

	public long getMaxReqNum() {
		return maxReqNum;
	}

	public void setMaxReqNum(long maxReqNum) {
		this.maxReqNum = maxReqNum;
	}

	public long getMaxDiffForReq() {
		return maxDiffForReq;
	}

	public void setMaxDiffForReq(long maxDiffForReq) {
		this.maxDiffForReq = maxDiffForReq;
	}

	//	public long getTotalIpNum() {
	//		return totalIpNum;
	//	}
	//
	//	public void setTotalIpNum(long totalIpNum) {
	//		this.totalIpNum = totalIpNum;
	//	}

	public long getMaxIPNum() {
		return maxIPNum;
	}

	public void setMaxIPNum(long maxIPNum) {
		this.maxIPNum = maxIPNum;
	}

	public long getMaxDiffForIp() {
		return maxDiffForIp;
	}

	public void setMaxDiffForIp(long maxDiffForIp) {
		this.maxDiffForIp = maxDiffForIp;
	}

	public long getReqNum() {
		return reqNum;
	}

	public void setReqNum(long reqNum) {
		this.reqNum = reqNum;
	}

	public long getIpNum() {
		return ipNum;
	}

	public void setIpNum(long ipNum) {
		this.ipNum = ipNum;
	}

	public long getTopUrlHits() {
		return topUrlHits;
	}

	public void setTopUrlHits(long topUrlHits) {
		this.topUrlHits = topUrlHits;
	}

	//	public long getTotalTopUrlHits() {
	//		return totalTopUrlHits;
	//	}
	//
	//	public void setTotalTopUrlHits(long totalTopUrlHits) {
	//		this.totalTopUrlHits = totalTopUrlHits;
	//	}

	public long getMaxTopUrlHits() {
		return maxTopUrlHits;
	}

	public void setMaxTopUrlHits(long maxTopUrlHits) {
		this.maxTopUrlHits = maxTopUrlHits;
	}

	public long getMaxDiffForTopUrl() {
		return maxDiffForTopUrl;
	}

	public void setMaxDiffForTopUrl(long maxDiffForTopUrl) {
		this.maxDiffForTopUrl = maxDiffForTopUrl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	//	public long getAveReqNumForIp() {
	//		return aveReqNumForIp;
	//	}
	//
	//	public void setAveReqNumForIp(long aveReqNumForIp) {
	//		this.aveReqNumForIp = aveReqNumForIp;
	//	}

	public long getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getField(String fieldName) {
		//now don't use reflect which will cost more time
		if (fieldName == null) {
			return 0L;
		}
		long value = 0L;
		if ("fReqNum".equals(fieldName)) {
			value = this.getReqNum();
		} else if ("fIpNum".equals(fieldName)) {
			value = this.getIpNum();
		} else if ("fTopUrlHits".equals(fieldName)) {
			value = this.getTopUrlHits();
		} else if ("fMaxReqNum".equals(fieldName)) {
			value = this.getMaxReqNum();
		} else if ("fMaxIPNum".equals(fieldName)) {
			value = this.getMaxIPNum();
		} else if ("fMaxTopUrlHits".equals(fieldName)) {
			value = this.getMaxTopUrlHits();
		} else if ("fMaxDiffForReq".equals(fieldName)) {
			value = this.getMaxDiffForReq();
		} else if ("fMaxDiffForIp".equals(fieldName)) {
			value = this.getMaxDiffForIp();
		} else if ("fMaxDiffForTopUrl".equals(fieldName)) {
			value = this.getMaxDiffForTopUrl();
		}
		return value;
	}

	public void setField(String fieldName, long value) {
		//now don't use reflect which will cost more time
		if (fieldName == null) {
			return;
		}
		if ("fReqNum".equals(fieldName)) {
			this.setReqNum(value);
		} else if ("fIpNum".equals(fieldName)) {
			this.setIpNum(value);
		} else if ("fTopUrlHits".equals(fieldName)) {
			this.setTopUrlHits(value);
		} else if ("fMaxReqNum".equals(fieldName)) {
			this.setMaxReqNum(value);
		} else if ("fMaxIPNum".equals(fieldName)) {
			this.setMaxIPNum(value);
		} else if ("fMaxTopUrlHits".equals(fieldName)) {
			this.setMaxTopUrlHits(value);
		} else if ("fMaxDiffForReq".equals(fieldName)) {
			this.setMaxDiffForReq(value);
		} else if ("fMaxDiffForIp".equals(fieldName)) {
			this.setMaxDiffForIp(value);
		} else if ("fMaxDiffForTopUrl".equals(fieldName)) {
			this.setMaxDiffForTopUrl(value);
		}
	}

	public String getFieldsString(String[] fieldNames) {
		Map<String, String> map = new HashMap<String, String>();
		for (String fieldName : fieldNames) {
			map.put(fieldName, String.valueOf(this.getField(fieldName)));
		}
		return gson.toJson(map);
	}

}
