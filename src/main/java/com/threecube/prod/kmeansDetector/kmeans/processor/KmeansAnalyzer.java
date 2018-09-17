/*
 * @(#)KmeansAnalyzer.java $version 2015-1-28
 *
 * Copyright 2007 THREECUBE Corp. All rights Reserved.
 * THREECUBE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.threecube.prod.kmeansDetector.kmeans.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.threecube.prod.kmeansDetector.kmeans.model.PointModel;
import com.threecube.prod.kmeansDetector.kmeans.result.AccuracyRate;
import com.threecube.prod.kmeansDetector.kmeans.result.KmeansAnalyzResult;

/**
 * the analyzer of k-means, this bean will be used after running k-means algorithm.
 * 
 * @author Mike
 */
public class KmeansAnalyzer {

	/** key : training data's real type
	 *  value : a map type, key is training data's clustered type, value is the number of data
	 *  for example, <1,<2', 1000>> means: the number of data whose real type is 1 and clustered type is 2' is 1000.
	 * */
	private Map<Integer, Map<Integer, Long>> typesNumMap;

	/**
	 * input must be sort by property of type or key
	 * 
	 * @param originData : original data which give each data's right type
	 * @param newData: data clustered by k-means which give each data's clustered type
	 * @throws Exception 
	 */
	public KmeansAnalyzResult execute(List<PointModel> originData, Map<Long, Integer> newData) throws Exception {

		if (originData == null || originData.isEmpty() || newData == null || newData.isEmpty()) {
			throw new Exception("execute in KmeansAnalyzer is error, input is null");
		}

		KmeansAnalyzResult result = new KmeansAnalyzResult();
		typesNumMap = new HashMap<Integer, Map<Integer, Long>>();

		//step1: do a statistic.
		doStatistic(typesNumMap, originData, newData);

		//step2:calculate acuuracy rate of each real type of data
		long totalDataNum = 0l;
		long totalAccuDataNum = 0l;
		List<AccuracyRate> accuRates = new ArrayList<AccuracyRate>();
		for (Entry<Integer, Map<Integer, Long>> entry : typesNumMap.entrySet()) {

			AccuracyRate rate = new AccuracyRate();
			totalDataNum += calcAccuRate(rate, entry.getValue(), entry.getKey());
			totalAccuDataNum += rate.getAccuDataNum();

			accuRates.add(rate);
		}
		result.setTotalDataNum(totalDataNum);
		result.setAccuRateOfType(accuRates);
		result.setTotalAccuRate((float)totalAccuDataNum / totalDataNum);
		return result;
	}

	/**
	 * do a statistic by origin data and clustered data
	 * result will be like : {<1, <1', 200>>, <1, <2',1000>>, <2,<1',1200>, <2, <2', 200>>>}
	 * 
	 * @param typesNumMap, output
	 * @param originData, input
	 * @param newData, input
	 */
	private void doStatistic(Map<Integer, Map<Integer, Long>> typesNumMap, List<PointModel> originData, Map<Long, Integer> newData) {

		for (PointModel origin : originData) {

			// get data's clustered type by dataId from newData
			int clusteredType = newData.get(origin.getId());

			// get value for data's real type, which is a map structure
			Map<Integer, Long> map = typesNumMap.get(origin.getType());
			if (map == null) {
				map = new HashMap<Integer, Long>();
			}

			// get the number of mapping originType to clusteredType
			long number = (map.get(clusteredType) == null) ? 0l : map.get(clusteredType);

			map.put(clusteredType, ++number);

			typesNumMap.put(origin.getType(), map);
		}
	}

	/**
	 * calculate accuracy rate for each real type of data
	 * for example, {<1, <1', 200>>, <1, <2',1000>>}, because 200<1000, then 1(real type) will be mapped to 
	 * 2'(clustered type), and error rate will be 200/(200+1000)
	 * 
	 * @param result
	 * @param typeNumMap
	 * @param realType, the data's real type 
	 * @return, total number of data whose real type is given by realType
	 */
	private long calcAccuRate(AccuracyRate rate, Map<Integer, Long> typeNumMap, int realType) {

		// total data number
		long dataNum = 0l;
		// the maximum data number in map
		long maxDataNum = 0l;
		//
		Map<Integer, Long> errDataNum = new HashMap<Integer, Long>();

		for (Entry<Integer, Long> entry : typeNumMap.entrySet()) {

			if (entry.getValue() > maxDataNum) {
				rate.setClusteredType(entry.getKey());
				maxDataNum = entry.getValue();
			}
			dataNum += entry.getValue();
		}

		if (dataNum == 0l || maxDataNum == 0l) {
			return 0l;
		}
		rate.setAccuDataNum(maxDataNum);

		for (Entry<Integer, Long> entry : typeNumMap.entrySet()) {
			if (entry.getKey() != rate.getClusteredType()) {
				errDataNum.put(entry.getKey(), entry.getValue());
			}
		}
		rate.setErrDataNumMap(errDataNum);

		rate.setAccuRate((float)maxDataNum / dataNum);
		rate.setRealType(realType);
		return dataNum;
	}
}
