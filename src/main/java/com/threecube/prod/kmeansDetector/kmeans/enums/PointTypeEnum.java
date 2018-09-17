/*
 * @(#)PointTypeEnum.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.enums;

/**
 * Enumeration types of training data 
 * 
 * @author Mike
 */
public enum PointTypeEnum {

	UNKOWN(0, "unkown type"),

	NORMAL(1, "normal type"),

	DOS(2, "Dos type"),

	DDos(3, "DDos type");

	private int code;

	private String desc;

	PointTypeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
