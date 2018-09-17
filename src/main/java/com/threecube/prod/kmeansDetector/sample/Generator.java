package com.threecube.prod.kmeansDetector.sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.threecube.prod.kmeansDetector.kmeans.enums.PointTypeEnum;
import com.threecube.prod.kmeansDetector.kmeans.model.PointModel;

public class Generator {

	private static Gson gson = new Gson();

	private static long hourMill = 60 * 60 * 1000L;
	private static long min5Mill = 5 * 60 * 1000;

	private Queue<PointModel> queue = new LinkedBlockingQueue<PointModel>();
	private CountDownLatch latch = null;
	private volatile boolean producerFinished = false;

	private Random rand = new Random(13 + System.currentTimeMillis() % 97);

	private List<TimeArea> timeList = new ArrayList<TimeArea>();

	public void generate() {
		Calendar dar = Calendar.getInstance();
		//set to yesterday
		dar.set(Calendar.DAY_OF_MONTH, dar.get(Calendar.DAY_OF_MONTH) - 1);
		long start0 = dar.getTimeInMillis();
		start0 = start0 - start0 % hourMill;
		//generate 5 hour data
		for (int i = 0; i < 60; i++) {
			long start = start0 + min5Mill * i;
			long end = start + min5Mill;
			timeList.add(new TimeArea(start, end, randomPointType()));
		}
		latch = new CountDownLatch(1);

		new Thread(new DataProducer()).start();
		new Thread(new DataComsumer()).start();

		//wait
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private PointTypeEnum randomPointType() {
		PointTypeEnum type = null;
		float r = rand.nextFloat();
		if (r < SampleConfig.ddosRatio) {
			type = PointTypeEnum.DDos;
		} else if (r < SampleConfig.ddosRatio + SampleConfig.dosRatio) {
			type = PointTypeEnum.DOS;
		} else {
			type = PointTypeEnum.NORMAL;
		}
		return type;
	}

	private class DataProducer implements Runnable {

		public void run() {
			// TODO Auto-generated method stub
			for (TimeArea ta : timeList) {
				long start = ta.getStart();
				long end = ta.getEnd();
				long time = start;
				while (time < end) {
					//generate data
					queue.offer(ta.nextPoint(time));
					time = time + 1000L; //next second
				}
			}
			//set finished
			producerFinished = true;
		}

	}

	private class DataComsumer implements Runnable {
		List<PointModel> minuteDatas = new LinkedList<PointModel>();

		public void run() {
			// TODO Auto-generated method stub

			//open file
			OutputStreamWriter writer = null;
			try {
				writer = new OutputStreamWriter(new FileOutputStream(new File(SampleConfig.fileName)));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}

			//process data
			while (true) {
				if (producerFinished && queue.isEmpty()) {
					break;
				}

				PointModel point = queue.poll();
				if (point == null) {
					try {
						Thread.sleep(20L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					continue;
				}

				//get a data
				if (minuteDatas.size() >= 60) {
					PointModel pm = minuteDatas.remove(0);
					writeData(pm);
				}
				minuteDatas.add(point);
				PointModel data = fillValues(point);

				//write data

				try {
					writer.write(gson.toJson(data));
					writer.write("\r\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			//close file
			try {
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//set finished.
			latch.countDown();

		}

		private PointModel fillValues(PointModel point) {
			//			long totalReqNum = 0;
			//			long totalIpNum = 0;

			long maxReqNum = 0;
			long maxIPNum = 0;
			long maxTopUrlHits = 0;

			long maxDiffForReq = 0;
			long maxDiffForIp = 0;
			long maxDiffForTopUrl = 0;

			//			long aveReqNumForIp = 0;

			//			int ddosCount = 0;
			PointModel pre = null;
			for (PointModel pm : minuteDatas) {
				//				totalReqNum += pm.getReqNum();
				maxReqNum = Math.max(maxReqNum, pm.getReqNum());
				maxIPNum = Math.max(maxIPNum, pm.getIpNum());
				maxTopUrlHits = Math.max(maxTopUrlHits, pm.getTopUrlHits());
				if (pre != null) {
					maxDiffForReq = Math.max(maxDiffForReq, Math.abs(pre.getReqNum() - pm.getReqNum()));
					maxDiffForIp = Math.max(maxDiffForIp, Math.abs(pre.getIpNum() - pm.getIpNum()));
					maxDiffForTopUrl = Math.max(maxDiffForTopUrl, Math.abs(pre.getTopUrlHits() - pm.getTopUrlHits()));
				}
				//				if(PointTypeEnum.DDos.getCode() == pm.getType()){
				////					ddosCount++;
				//				}else{
				//					totalIpNum += pm.getIpNum();
				//				}
				pre = pm;
			}

			//mock
			//			totalIpNum = maxIPNum + Float.valueOf(totalIpNum*(1-SampleConfig.ipSimilarRatio)).longValue();

			//			if(totalIpNum!=0){
			//				aveReqNumForIp = totalReqNum/totalIpNum;
			//			}

			PointModel data = new PointModel();
			data.setId(point.getId());
			data.setService(point.getService());
			data.setType(point.getType());
			data.setGmtCreate(point.getGmtCreate());
			//			data.setTotalReqNum(totalReqNum);
			//			data.setTotalIpNum(totalIpNum);
			data.setMaxReqNum(maxReqNum);
			data.setMaxIPNum(maxIPNum);
			data.setMaxDiffForReq(maxDiffForReq);
			data.setMaxDiffForIp(maxDiffForIp);
			//			data.setAveReqNumForIp(aveReqNumForIp);

			data.setReqNum(point.getReqNum());
			data.setIpNum(point.getIpNum());
			data.setTopUrlHits(point.getTopUrlHits());
			data.setMaxTopUrlHits(maxTopUrlHits);
			data.setMaxDiffForTopUrl(maxDiffForTopUrl);
			return data;
		}

		private void writeData(PointModel point) {

		}

	}

}

class TimeArea {
	//	private static int normalAvgVistors = 50;	//per second
	//	private static int normalAvgHits = 100; 	//per second

	private static long genId = 1;
	private static Random random = new Random(13 + System.currentTimeMillis() % 79);

	private final long start;
	private final long end;
	private PointTypeEnum type;
	private int avgVisitors;
	//	private int avgHits;
	private float avgHitsTimes;
	private float topUrlHitsRatio;

	public TimeArea(long start, long end, PointTypeEnum type) {
		this.start = start;
		this.end = end;
		this.type = type;
		switch (this.type) {
			case NORMAL:
				avgVisitors = SampleConfig.normalIpNumPerSecond;
				//			avgHits = SampleConfig.normalIpNumPerSecond * SampleConfig.normalReqNumPersSecondTimesIp;
				avgHitsTimes = SampleConfig.normalReqNumPersSecondTimesIp;
				topUrlHitsRatio = SampleConfig.normalTopUrlHitsRatio;
				break;
			case DDos:
				avgVisitors = SampleConfig.normalIpNumPerSecond * SampleConfig.ddosIpNumTimes;
				avgHitsTimes = SampleConfig.normalReqNumPersSecondTimesIp * SampleConfig.ddosReqNumTimes;
				topUrlHitsRatio = SampleConfig.ddosTopUrlHitsRatio;
				break;
			case DOS:
				avgVisitors = SampleConfig.normalIpNumPerSecond * SampleConfig.dosIpNumTimes;
				avgHitsTimes = SampleConfig.normalReqNumPersSecondTimesIp * SampleConfig.dosReqNumTimes;
				topUrlHitsRatio = SampleConfig.dosTopUrlHitsRatio;
				break;
			case UNKOWN:
				break;
		}
	}

	public PointModel nextPoint(long time) {
		PointModel point = new PointModel();
		point.setId(genId++);
		point.setService("demo");
		point.setType(type.getCode());
		point.setGmtCreate(time);
		point.setIpNum(randValue(avgVisitors));
		//reqNum>=ipNum
		point.setReqNum(Math.max(randValue((long)(avgHitsTimes * point.getIpNum())), point.getIpNum()));
		point.setTopUrlHits(randValue((long)(point.getReqNum() * topUrlHitsRatio)));
		return point;
	}

	public long randValue(long avg) {
		float f = random.nextFloat();
		float r = 1 + (2 * f - 1) * SampleConfig.diviateRatio; //0.7-1.3
		return Float.valueOf(avg * r).longValue();
	}

	public PointTypeEnum getType() {
		return type;
	}

	public void setType(PointTypeEnum type) {
		this.type = type;
	}

	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

}
