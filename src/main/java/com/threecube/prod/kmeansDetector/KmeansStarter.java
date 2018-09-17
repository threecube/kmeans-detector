package com.threecube.prod.kmeansDetector;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.threecube.prod.kmeansDetector.kmeans.DefinedConstants;
import com.threecube.prod.kmeansDetector.kmeans.model.NormalizedPoint;
import com.threecube.prod.kmeansDetector.kmeans.model.PointModel;
import com.threecube.prod.kmeansDetector.kmeans.processor.KmeansAnalyzer;
import com.threecube.prod.kmeansDetector.kmeans.processor.KmeansLearner;
import com.threecube.prod.kmeansDetector.kmeans.result.AccuracyRate;
import com.threecube.prod.kmeansDetector.kmeans.result.KmeansAnalyzResult;
import com.threecube.prod.kmeansDetector.kmeans.result.KmeansResult;
import com.threecube.prod.kmeansDetector.sample.Samples;

/**
 * Hello world!
 *
 * @author Mike
 */
public class KmeansStarter {
	private static KmeansLearner kmeansLearner = new KmeansLearner();

	private static KmeansAnalyzer kmeansAnalyzer = new KmeansAnalyzer();

	private static Gson gson = new Gson();

	private static final String statisticFile = "data/out-statistic.txt";
	private static final String clusterFile = "data/out-clusterResult.txt";
	private static final String analysisFile = "data/out-analysis.txt";

	public static void main(String[] args) {
		Samples sample = Samples.getInstance();
		System.out.println("finished.");
		System.out.println(gson.toJson(Samples.getInstance().getMaxValue()));

		List<PointModel> data = sample.getDataList();
		// get each propery's maximum value
		PointModel maxFlag = sample.getMaxValue();

		System.out.println("Start to k-means learning.");
		KmeansResult result = kmeansLearner.execute(data, maxFlag);
		if (result == null) {
			System.out.println("kmeans error, result is null");
		}
		System.out.println("End to k-means learning.");

		System.out.println("Start to analysis k-means clustered result");
		KmeansAnalyzResult analyzeResult = null;
		try {
			analyzeResult = kmeansAnalyzer.execute(data, result.getPointTypeMap());
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("End to analysis k-means clustered result");

		writeDataIntoFile(result, analyzeResult);
	}

	private static void writeDataIntoFile(KmeansResult result, KmeansAnalyzResult analyzeResult) {
		try {
			//step1: write the result of k-means learning
			FileWriter fileWriter = new FileWriter(statisticFile);
			fileWriter.write("LoopTimes=" + result.getLoopTimes());
			fileWriter.write("\r\n");
			fileWriter.write("ConsumeTime=" + result.getConsTime());
			fileWriter.write("\r\n");
			fileWriter.write("DataNumber=" + result.getDataNum());
			fileWriter.write("\r\n");
			//			fileWriter.write("Centroids={" + gson.toJson(result.getCentroids()));
			fileWriter.write("Centroids={\r\n");
			for (NormalizedPoint point : result.getCentroids()) {
				fileWriter.write(point.getFieldsString(DefinedConstants.selectedFieldNames));
				fileWriter.write("\r\n");
			}
			fileWriter.write("}");
			fileWriter.close();

			// sort DataFile by key for analying 
			Object[] keyArray = result.getPointTypeMap().keySet().toArray();
			Arrays.sort(keyArray);

			fileWriter = new FileWriter(clusterFile);
			for (Object key : keyArray) {
				fileWriter.write("DataId=" + key + ", DataType=" + result.getPointTypeMap().get(key));
				fileWriter.write("\r\n");
			}
			fileWriter.close();

			//step2: write the result of analayzer about k-means learning
			fileWriter = new FileWriter(analysisFile);
			fileWriter.write("Total Date Number=" + analyzeResult.getTotalDataNum());
			fileWriter.write("\r\n");
			fileWriter.write("Total Accuracy rate=" + analyzeResult.getTotalAccuRate());
			fileWriter.write("\r\n");
			for (AccuracyRate rate : analyzeResult.getAccuRateOfType()) {
				fileWriter.write(gson.toJson(rate));
				fileWriter.write("\r\n");
			}
			fileWriter.close();
		} catch (IOException e) {
			System.out.print(e);
		}

	}
}
