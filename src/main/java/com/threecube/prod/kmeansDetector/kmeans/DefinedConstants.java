/*
 * @(#)DefinedParam.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans;

/**
 * parameter defined.
 * this part will be modified,then each parameter can be endlessly tweaked.
 * 
 * @author Mike
 */
public class DefinedConstants {

	/** the k value of k-means*/
	public static final int K = 3;

	/** the number of properties to be used*/
	public static final int PROP_NUM = 7;
	
	/** candidate fields are:fReqNum,fIpNum,fTopUrlHits,fMaxReqNum,fMaxDiffForReq,fMaxIPNum,fMaxDiffForIp,fMaxTopUrlHits,fMaxDiffForTopUrl */
	public static final String[] selectedFieldNames = new String[]{
//		"fReqNum",
//		"fIpNum",
//		"fTopUrlHits"
		"fMaxReqNum",
		"fMaxIPNum",
//		"fMaxTopUrlHits",
		"fMaxDiffForReq",
		"fMaxDiffForIp"
//		"fMaxDiffForTopUrl"
	}; 
	
}
