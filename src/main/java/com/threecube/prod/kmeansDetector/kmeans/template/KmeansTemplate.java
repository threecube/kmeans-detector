/*
 * @(#)KmeansLearningTemplate.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.threecube.prod.kmeansDetector.kmeans.DefinedConstants;
import com.threecube.prod.kmeansDetector.kmeans.model.ModelConvertor;
import com.threecube.prod.kmeansDetector.kmeans.model.NormalizedPoint;
import com.threecube.prod.kmeansDetector.kmeans.model.PointModel;
import com.threecube.prod.kmeansDetector.kmeans.result.KmeansResult;

/**
 * template for k-means algorithm, which gives steps of k-means
 * 
 * @author Mike
 */
public class KmeansTemplate {

	/**
	 * @param points, data to be clustered
	 * @param maxFlag, maximum value for each property
	 * @param callback
	 * @return
	 */
	public KmeansResult execute(List<PointModel> points, PointModel maxFlag, KmeansCallBack callback) {

		KmeansResult result = new KmeansResult();

		// the number of loop for k-means
		int loopTimes = 0;
		// normalized points store
		List<NormalizedPoint> nPoints = new ArrayList<NormalizedPoint>();
		// centroid points store
		List<NormalizedPoint> centroids = new ArrayList<NormalizedPoint>();
		// old centroid point store
		List<NormalizedPoint> oldCentroids = null;

		try {
			long startTime = System.currentTimeMillis();

			//step1: normalized
			callback.normalize(points, nPoints, maxFlag);

			//step2: choose centroids for firstly
			callback.findCentroids(true, nPoints, centroids);

			while (!callback.isFinished(oldCentroids, centroids)) {
				Map<NormalizedPoint, Float> distanceMap = null;

				for (NormalizedPoint nPoint : nPoints) {

					distanceMap = new HashMap<NormalizedPoint, Float>();

					// step3: calculate distances between point and each centroid 
					callback.calculateDis(distanceMap, nPoint, centroids);

					// step4: judge and set type of point
					callback.setPointType(distanceMap, nPoint);
				}

				// remember the old centroids for checking if k-means learning is finished
				oldCentroids = ModelConvertor.norms2Norms(centroids);

				//step5: choose centroids again
				centroids = new ArrayList<NormalizedPoint>();
				callback.findCentroids(false, nPoints, centroids);

				loopTimes++;
			}

			long endTime = System.currentTimeMillis();

			// get result
			genResult(result, true, nPoints.size(), endTime - startTime, loopTimes, centroids, nPoints, maxFlag);

		} catch (Exception e) {
			System.out.print(e);
		}

		return result;
	}

	/**
	 * @param result
	 * @param isSuccess
	 * @param size
	 * @param time
	 * @param loopTimes
	 * @param centroids
	 * @param nPoints
	 * @param maxFlag
	 */
	@SuppressWarnings("null")
	private void genResult(KmeansResult result, boolean isSuccess, long size, long time, int loopTimes, List<NormalizedPoint> centroids, List<NormalizedPoint> nPoints, PointModel maxFlag) {

		if (result == null) {
			result = new KmeansResult();
		}

		result.setSuccess(isSuccess);
		result.setDataNum(size);
		result.setConsTime(time);
		result.setLoopTimes(loopTimes);

		if (centroids != null || centroids.isEmpty()) {
			List<NormalizedPoint> centroidOuts = new ArrayList<NormalizedPoint>();
			if (centroids != null && !centroids.isEmpty()) {
				for (NormalizedPoint centroid : centroids) {
					NormalizedPoint centroidOut = new NormalizedPoint();

					for (String fieldName : DefinedConstants.selectedFieldNames) {
						centroidOut.setField(fieldName, centroid.getField(fieldName) * maxFlag.getField(fieldName));
					}

					centroidOuts.add(centroidOut);
				}
			}
			result.setCentroids(centroidOuts);
		}

		Map<Long, Integer> pointTypeMap = new HashMap<Long, Integer>();
		for (NormalizedPoint nPoint : nPoints) {
			pointTypeMap.put(nPoint.getId(), nPoint.getType());
		}
		result.setPointTypeMap(pointTypeMap);
	}
}
