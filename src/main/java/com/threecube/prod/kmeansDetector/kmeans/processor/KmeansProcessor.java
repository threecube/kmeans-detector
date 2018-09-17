/*
 * @(#)KmeansProcessor.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.processor;

import java.util.List;

import com.threecube.prod.kmeansDetector.kmeans.model.NormalizedPoint;

/**
 * interface for the k-means processor
 * 
 * @author Mike
 */
public interface KmeansProcessor {

	/**
	 * find centroid for mean having given type
	 * 
	 * @param nPoint
	 * @param type
	 * @return
	 */
	public NormalizedPoint findCentroid(List<NormalizedPoint> nPoints, int type);
	
	/**
	 * @param centroid
	 * @param point
	 * @return
	 */
	public float calculateDis(NormalizedPoint centroid, NormalizedPoint point);
	
	/**
	 * @param newCentroid
	 * @param centroids
	 * @return
	 */
	public boolean isExist(NormalizedPoint newCentroid, List<NormalizedPoint> centroids);
}
