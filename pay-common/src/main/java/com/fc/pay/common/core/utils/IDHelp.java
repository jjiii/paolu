package com.fc.pay.common.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 每毫秒最多产生4096个id。否则要等下一个毫秒
 * @author XDou 2015年11月27日下午19:52:14
 */
public class IDHelp {

	private static final Logger LOG = LoggerFactory.getLogger(IDHelp.class);

	private static long workerId = 1;
	private static long datacenterId = 1;
	
	private static long sequence = 0L;

	private static long twepoch = 1288834974657L;

	private static long workerIdBits = 5L;
	private static long datacenterIdBits = 5L;

	private static long sequenceBits = 12L;

	private static long workerIdShift = sequenceBits;
	private static long datacenterIdShift = sequenceBits + workerIdBits;
	private static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private static long sequenceMask = -1L ^ (-1L << sequenceBits);

	private static long lastTimestamp = -1L;



	public static synchronized Long nextId() {
		long timestamp = timeGen();


		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}
		
		if (timestamp < lastTimestamp) {
			LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
		}

		lastTimestamp = timestamp;

		return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId << workerIdShift) | sequence;
	}

	private static long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private static long timeGen() {
		return System.currentTimeMillis();
	}
//	public static void main(String[] args) {
//		
//		System.out.println(IDHelp.nextId());
//	}

}