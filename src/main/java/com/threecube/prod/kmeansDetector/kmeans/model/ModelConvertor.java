/*
 * @(#)ModelConvertor.java $version 2015-1-27
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.model;

import java.util.ArrayList;
import java.util.List;

import com.threecube.prod.kmeansDetector.kmeans.DefinedConstants;

/**
 * model convertor
 * 
 * @author Mike
 */
public class ModelConvertor {

	/**
	 * just as a copier
	 * 
	 * @param old
	 * @return
	 */
	public static NormalizedPoint norm2Norm(NormalizedPoint oldObject) {

		if (oldObject == null) {
			return null;
		}

		NormalizedPoint newObject = new NormalizedPoint();
		newObject.setGmtCreate(oldObject.getGmtCreate());
		newObject.setId(oldObject.getId());
		newObject.setService(oldObject.getService());
		newObject.setType(oldObject.getType());

		for (String fieldName : DefinedConstants.selectedFieldNames) {
			newObject.setField(fieldName, oldObject.getField(fieldName));
		}

		return newObject;
	}

	/**
	 * just as a copier
	 * 
	 * @param oldObjects
	 * @return
	 */
	public static List<NormalizedPoint> norms2Norms(List<NormalizedPoint> oldObjects) {

		if (oldObjects == null || oldObjects.isEmpty()) {
			return null;
		}

		List<NormalizedPoint> newObjects = new ArrayList<NormalizedPoint>();
		for (NormalizedPoint oldObject : oldObjects) {
			newObjects.add(norm2Norm(oldObject));
		}

		return newObjects;
	}
}
