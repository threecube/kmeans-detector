/*
 * @(#)KmeansLearning.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.processor;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.threecube.prod.kmeansDetector.kmeans.DefinedConstants;
import com.threecube.prod.kmeansDetector.kmeans.model.NormalizedPoint;
import com.threecube.prod.kmeansDetector.kmeans.model.PointModel;
import com.threecube.prod.kmeansDetector.kmeans.result.KmeansResult;
import com.threecube.prod.kmeansDetector.kmeans.template.KmeansCallBack;
import com.threecube.prod.kmeansDetector.kmeans.template.KmeansTemplate;

/**
 * k-means learning function, 
 * 
 * @author Mike
 */
public class KmeansLearner {

	private KmeansTemplate kmeansTemplate = new KmeansTemplate();

	private KmeansProcessor kmeansProcessor = new KmeansProcessorImpl();

	private final static float MAX_FLOAT = 999999l;

	/**
	 * @param points
	 * @param maxFlag, maximum value for each property
	 * @return
	 */
	public KmeansResult execute(List<PointModel> points, PointModel maxFlag) {

		KmeansResult result = kmeansTemplate.execute(points, maxFlag, new KmeansCallBack() {

			/**
			 * @param points
			 * @param nPoints
			 * @throws Exception 
			 */
			@Override
			public void normalize(List<PointModel> points, List<NormalizedPoint> nPoints, PointModel maxFlag) throws Exception {

				if (points == null || points.isEmpty() || nPoints == null) {
					throw new Exception("normalize error in KmeansLearning: input parameter is null");
				}

				for (PointModel point : points) {
					NormalizedPoint nPoint = new NormalizedPoint();
					nPoint.setId(point.getId());
					nPoint.setService(point.getService());
					nPoint.setType(-1);
					nPoint.setGmtCreate(point.getGmtCreate());

					for (String fieldName : DefinedConstants.selectedFieldNames) {
						nPoint.setField(fieldName, ((float)point.getField(fieldName)) / maxFlag.getField(fieldName));
					}

					nPoints.add(nPoint);
				}
			}

			/**
			 * @param isFirst
			 * @param points
			 * @param centroids
			 * @throws Exception 
			 */
			@Override
			public void findCentroids(boolean isFirst, List<NormalizedPoint> nPoints, List<NormalizedPoint> centroids) throws Exception {

				if (nPoints == null || nPoints.isEmpty() || centroids == null) {
					throw new Exception("findCentroids error in KmeansLearning: input parameter is null");
				}

				// if firstly find centroids, choose the first k from nPoints
				if (isFirst) {
					for (int i = 0; i < DefinedConstants.K; i++) {
						//make sure every centroid is different with each other
						float value = (i + 0.01f) / DefinedConstants.K;
						NormalizedPoint centroid = new NormalizedPoint();
						NormalizedPoint point = nPoints.get(i);
						centroid.setService(point.getService());
						// give a type to this centroid
						centroid.setType(i + 1);

						for (String fieldName : DefinedConstants.selectedFieldNames) {
							centroid.setField(fieldName, value);
						}
						centroids.add(centroid);
					}
				} else {
					for (int i = 0; i < DefinedConstants.K; i++) {
						NormalizedPoint centroid = kmeansProcessor.findCentroid(nPoints, i + 1);
						if (centroid != null) {
							centroids.add(centroid);
						}
					}
				}
			}

			/**
			 * @param distances
			 * @param point
			 * @param centroids
			 * @throws Exception 
			 */
			@Override
			public void calculateDis(Map<NormalizedPoint, Float> distanceMap, NormalizedPoint point, List<NormalizedPoint> centroids) throws Exception {
				if (point == null || centroids == null || centroids.isEmpty()) {
					throw new Exception("calculateDis error in KmeansLearning : input parameter is null");
				}

				// calculate distance from point to each centroid
				for (NormalizedPoint centroid : centroids) {
					distanceMap.put(centroid, kmeansProcessor.calculateDis(centroid, point));
				}
			}

			/**
			 * @param distances
			 * @param point
			 * @throws Exception 
			 */
			@Override
			public void setPointType(Map<NormalizedPoint, Float> distanceMap, NormalizedPoint point) throws Exception {
				if (distanceMap == null || distanceMap.isEmpty() || point == null) {
					throw new Exception("setPointType error in KmeansLearning : input parameter is null");
				}

				float minDis = MAX_FLOAT;
				int type = -1;

				for (Entry<NormalizedPoint, Float> entry : distanceMap.entrySet()) {
					if (entry.getValue() < minDis) {
						minDis = entry.getValue();
						type = entry.getKey().getType();
					}
				}

				if (type == -1) {
					throw new Exception("setPointType error in KmeansLearning : can't make sure point's type");
				}

				point.setType(type);
			}

			/**
			 * @param points
			 * @param centroids
			 * @return
			 * @throws Exception 
			 */
			@Override
			public boolean isFinished(List<NormalizedPoint> oldCentroids, List<NormalizedPoint> newCentroids) throws Exception {
				if (oldCentroids == null || oldCentroids.isEmpty()) {
					return false;
				}

				if (newCentroids == null || newCentroids.isEmpty()) {
					throw new Exception("isFinished error in KmeansLearning : input parameter is null");
				}

				for (NormalizedPoint newCentroid : newCentroids) {
					if (!kmeansProcessor.isExist(newCentroid, oldCentroids)) {
						return false;
					}
				}

				return true;
			}

		});

		return result;
	}
}
