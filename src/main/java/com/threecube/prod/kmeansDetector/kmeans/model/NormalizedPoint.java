/*
 * @(#)NormilzedPoint.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

/**
 * normalized point model
 * 
 * @author Mike
 */
public class NormalizedPoint {

	private static final Gson gson = new Gson();

	/** id*/
	private long id;

	/** service name*/
	private String service;

	private float fReqNum;

	private float fIpNum;

	private float fTopUrlHits;

	//	/** property : total request number*/
	//	private float fTotalReqNum;

	/** property : the maximum request number*/
	private float fMaxReqNum;

	/** property : the maximum value of differences between two adjacent point*/
	private float fMaxDiffForReq;

	//	/** property : total IP number*/
	//	private float fTotalIpNum;

	/** property : the maximum IP number*/
	private float fMaxIPNum;

	/** property : the maximum value of differences between two adjacent point*/
	private float fMaxDiffForIp;

	private float fMaxTopUrlHits;

	private float fMaxDiffForTopUrl;

	//	/** property : the average value of request number*/
	//	private float fAveReqNumForIp;

	/** property : the time of request, microsecond*/
	private long gmtCreate;

	/** the type of point , initialized to -1*/
	private int type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	//	public float getfTotalReqNum() {
	//		return fTotalReqNum;
	//	}
	//
	//	public void setfTotalReqNum(float fTotalReqNum) {
	//		this.fTotalReqNum = fTotalReqNum;
	//	}

	public float getfMaxReqNum() {
		return fMaxReqNum;
	}

	public void setfMaxReqNum(float fMaxReqNum) {
		this.fMaxReqNum = fMaxReqNum;
	}

	public float getfMaxDiffForReq() {
		return fMaxDiffForReq;
	}

	public void setfMaxDiffForReq(float fMaxDiffForReq) {
		this.fMaxDiffForReq = fMaxDiffForReq;
	}

	//	public float getfTotalIpNum() {
	//		return fTotalIpNum;
	//	}
	//
	//	public void setfTotalIpNum(float fTotalIpNum) {
	//		this.fTotalIpNum = fTotalIpNum;
	//	}

	public float getfMaxIPNum() {
		return fMaxIPNum;
	}

	public void setfMaxIPNum(float fMaxIPNum) {
		this.fMaxIPNum = fMaxIPNum;
	}

	public float getfMaxDiffForIp() {
		return fMaxDiffForIp;
	}

	public void setfMaxDiffForIp(float fMaxDiffForIp) {
		this.fMaxDiffForIp = fMaxDiffForIp;
	}

	public float getfReqNum() {
		return fReqNum;
	}

	public void setfReqNum(float fReqNum) {
		this.fReqNum = fReqNum;
	}

	public float getfIpNum() {
		return fIpNum;
	}

	public void setfIpNum(float fIpNum) {
		this.fIpNum = fIpNum;
	}

	public float getfTopUrlHits() {
		return fTopUrlHits;
	}

	public void setfTopUrlHits(float fTopUrlHits) {
		this.fTopUrlHits = fTopUrlHits;
	}

	public float getfMaxTopUrlHits() {
		return fMaxTopUrlHits;
	}

	public void setfMaxTopUrlHits(float fMaxTopUrlHits) {
		this.fMaxTopUrlHits = fMaxTopUrlHits;
	}

	public float getfMaxDiffForTopUrl() {
		return fMaxDiffForTopUrl;
	}

	public void setfMaxDiffForTopUrl(float fMaxDiffForTopUrl) {
		this.fMaxDiffForTopUrl = fMaxDiffForTopUrl;
	}

	//	public float getfAveReqNumForIp() {
	//		return fAveReqNumForIp;
	//	}
	//
	//	public void setfAveReqNumForIp(float fAveReqNumForIp) {
	//		this.fAveReqNumForIp = fAveReqNumForIp;
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

	public float getField(String fieldName) {
		//now don't use reflect which will cost more time
		if (fieldName == null) {
			return 0f;
		}
		float value = 0f;
		if ("fReqNum".equals(fieldName)) {
			value = this.getfReqNum();
		} else if ("fIpNum".equals(fieldName)) {
			value = this.getfIpNum();
		} else if ("fTopUrlHits".equals(fieldName)) {
			value = this.getfTopUrlHits();
		} else if ("fMaxReqNum".equals(fieldName)) {
			value = this.getfMaxReqNum();
		} else if ("fMaxIPNum".equals(fieldName)) {
			value = this.getfMaxIPNum();
		} else if ("fMaxTopUrlHits".equals(fieldName)) {
			value = this.getfMaxTopUrlHits();
		} else if ("fMaxDiffForReq".equals(fieldName)) {
			value = this.getfMaxDiffForReq();
		} else if ("fMaxDiffForIp".equals(fieldName)) {
			value = this.getfMaxDiffForIp();
		} else if ("fMaxDiffForTopUrl".equals(fieldName)) {
			value = this.getfMaxDiffForTopUrl();
		}
		return value;
	}

	public void setField(String fieldName, float value) {
		//now don't use reflect which will cost more time
		if (fieldName == null) {
			return;
		}
		if ("fReqNum".equals(fieldName)) {
			this.setfReqNum(value);
		} else if ("fIpNum".equals(fieldName)) {
			this.setfIpNum(value);
		} else if ("fTopUrlHits".equals(fieldName)) {
			this.setfTopUrlHits(value);
		} else if ("fMaxReqNum".equals(fieldName)) {
			this.setfMaxReqNum(value);
		} else if ("fMaxIPNum".equals(fieldName)) {
			this.setfMaxIPNum(value);
		} else if ("fMaxTopUrlHits".equals(fieldName)) {
			this.setfMaxTopUrlHits(value);
		} else if ("fMaxDiffForReq".equals(fieldName)) {
			this.setfMaxDiffForReq(value);
		} else if ("fMaxDiffForIp".equals(fieldName)) {
			this.setfMaxDiffForIp(value);
		} else if ("fMaxDiffForTopUrl".equals(fieldName)) {
			this.setfMaxDiffForTopUrl(value);
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
