package net.dgg.gspt.dqc.utils;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * Created by wu on 2017/7/26.
 */
public class PrimaryKeyUtils {

	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 99999;
	private static Object numberLock= new Object();

	/**
	 * 订单号生成
	 * @return
	 */
	public static String orderNumberGeneration() {
		synchronized(numberLock){
			if (seq > ROTATION)
				seq = 0;
			buf.delete(0, buf.length());
			date.setTime(System.currentTimeMillis());
			String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, new Random().nextInt(ROTATION), seq++);
			return str;
		}
	}
	public static String getId() {
		return UUID.randomUUID().toString();
	}
}
