/*
 * @(#)KmeansProcessorImpl.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.processor;

//import java.lang.annotation.Annotation;
import java.util.List;

import com.threecube.prod.kmeansDetector.kmeans.DefinedConstants;
import com.threecube.prod.kmeansDetector.kmeans.model.NormalizedPoint;

/**
 * @author Mike
 */
public class KmeansProcessorImpl implements KmeansProcessor {

	/**
	 * @param nPoint
	 * @param type
	 * @return
	 */
	@Override
	public NormalizedPoint findCentroid(List<NormalizedPoint> nPoints, int type) {

		NormalizedPoint centroid = new NormalizedPoint();
		long matchedNum = 0;

		float[] totals = new float[DefinedConstants.selectedFieldNames.length];

		for (NormalizedPoint nPoint : nPoints) {
			if (nPoint.getType() != type) {
				continue;
			}

			for (int i = 0; i < DefinedConstants.selectedFieldNames.length; i++) {
				totals[i] += nPoint.getField(DefinedConstants.selectedFieldNames[i]);
			}

			centroid.setService(nPoint.getService());
			matchedNum++;
		}

		if (matchedNum > 0) {
			for (int i = 0; i < DefinedConstants.selectedFieldNames.length; i++) {
				centroid.setField(DefinedConstants.selectedFieldNames[i], totals[i] / matchedNum);
			}

			centroid.setType(type);
		} else {
			return null;
		}
		return centroid;
	}

	/**
	 * @param centroid
	 * @param point
	 * @return
	 */
	@Override
	public float calculateDis(NormalizedPoint centroid, NormalizedPoint point) {

		float distance = 0l;

		for (String fieldName : DefinedConstants.selectedFieldNames) {
			distance += Math.pow(centroid.getField(fieldName) - point.getField(fieldName), 2);
		}

		return distance;
	}

	/**
	 * check if newCentroid is exist in centroids,this funtion is used to check 
	 * 
	 * @param newCentroid
	 * @param centroids
	 * @return
	 */
	@Override
	public boolean isExist(NormalizedPoint newCentroid, List<NormalizedPoint> centroids) {
		for (NormalizedPoint centroid : centroids) {
			if (centroid.getType() != newCentroid.getType()) {
				continue;
			}

			boolean equal = true;
			//check each field
			for (String fieldName : DefinedConstants.selectedFieldNames) {
				if (centroid.getField(fieldName) != newCentroid.getField(fieldName)) {
					equal = false;
					break;
				}
			}

			if (equal) {
				return true;
			}
		}

		return false;
	}

	//	private String ifPropertyUsed(Class clazz, String propName) {
	//		Annotation annotation = null;
	//		try {
	//			annotation = clazz.getDeclaredField(propName).getAnnotation(Property.class);
	//		} catch (NoSuchFieldException e) {
	//			System.out.println("not found the filed:" + propName + " in class " + clazz);
	//		} catch (SecurityException e) {
	//			System.out.println("SecurityException:" + propName + " in class " + clazz);
	//		}
	//		Property property = (Property)annotation;
	//
	//		return property.Valid();
	//	}
}
