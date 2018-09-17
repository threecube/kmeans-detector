/*
 * @(#)KmeansCallBack.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.template;

import java.util.List;
import java.util.Map;

import com.threecube.prod.kmeansDetector.kmeans.model.NormalizedPoint;
import com.threecube.prod.kmeansDetector.kmeans.model.PointModel;

/**
 * @author Mike
 */
public interface KmeansCallBack {
	
	/**
	 * normallize the properties by (float)value/max,
	 * 
	 * @param points
	 * @throws Exception 
	 */
	public void normalize(List<PointModel> points, List<NormalizedPoint> nPoints, PointModel maxFlag) throws Exception;
	/**
	 * get centroid for each mean
	 * 
	 * @param isFirst, get centroid firstly, false-get centroid not firstly
	 * @param centroids, output
	 * @param points
	 * @param centroids
	 * @throws Exception 
	 */
	public void findCentroids(boolean isFirst, List<NormalizedPoint> points, List<NormalizedPoint> centroids) throws Exception;
	
	/**
	 * calculate distance between a given point and each centroid
	 * 
	 * @param points
	 * @param centroids
	 * @throws Exception 
	 */
	public void calculateDis(Map<NormalizedPoint, Float> distanceMap , NormalizedPoint point, List<NormalizedPoint> centroids) throws Exception;
	
	/**
	 * choose point's type in distanceMap whose value is the smallest, and set the type for point
	 * 
	 * @param distances
	 * @param point
	 * @throws Exception 
	 */
	public void setPointType(Map<NormalizedPoint, Float> distanceMap, NormalizedPoint point) throws Exception;
	
	/**
	 * check if the clustering is finished by checking if centroids changing any more
	 * 
	 * @param oldCentroids
	 * @param newCentroids
	 * @return
	 * @throws Exception 
	 */
	public boolean isFinished(List<NormalizedPoint> oldCentroids, List<NormalizedPoint> newCentroids) throws Exception;
}
