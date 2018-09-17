package com.threecube.prod.kmeansDetector.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.threecube.prod.kmeansDetector.kmeans.DefinedConstants;
import com.threecube.prod.kmeansDetector.kmeans.model.PointModel;

public class Samples {
	private static Gson gson = new Gson();
	private static Samples instance = new Samples();

	private List<PointModel> dataList = new ArrayList<PointModel>();
	private PointModel maxValue = null;

	private Samples() {
		generateData();
		loadData();
	}

	public static Samples getInstance() {
		return instance;
	}

	public List<PointModel> getDataList() {

		return dataList;

	}

	public PointModel getMaxValue() {
		return maxValue;
	}

	//	public void resetData(){
	//		generateData();
	//		loadData();
	//	}

	private void generateData() {
		new Generator().generate();
	}

	private void loadData() {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(new File(SampleConfig.fileName)));

			//record max value

			long[] maxes = new long[DefinedConstants.selectedFieldNames.length];

			String line = null;
			while ((line = br.readLine()) != null) {
				PointModel point = gson.fromJson(line, PointModel.class);
				dataList.add(point);

				for (int i = 0; i < DefinedConstants.selectedFieldNames.length; i++) {
					String fieldName = DefinedConstants.selectedFieldNames[i];
					maxes[i] = Math.max(maxes[i], point.getField(fieldName));
				}

			}

			//save max values
			maxValue = new PointModel();
			for (int i = 0; i < DefinedConstants.selectedFieldNames.length; i++) {
				String fieldName = DefinedConstants.selectedFieldNames[i];
				maxValue.setField(fieldName, maxes[i]);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		List<PointModel> list = Samples.getInstance().getDataList();
		System.out.println("finished.");
		System.out.println("data size=" + list.size());
		System.out.println(gson.toJson(Samples.getInstance().getMaxValue()));
	}

}
