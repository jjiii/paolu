package jj.tech.paolu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Twitter的SnowFlake算法
 * 1111111111111111111111111100000000000000000000000 42位时间
 * 1111111111111111111111111111111100000000000000000 5位数据中心ID
 * 1111111111111111111111111111111111111000000000000 5位机器ID
 * 0000000000000000000000000000000000000111111111111 12位sequenceBits
 * 4个值或运算组成一个新的Long
 * @DOU
 */
public class IDHelp {

	private static final Logger LOG = LoggerFactory.getLogger(IDHelp.class);
	private static IDHelp instance;

	private long workerId = 1;					//只有五位,0<=workerId<32
	private long datacenterId = 1;				//只有5位, 0<=datacenterId<32
	

							 
	private long startTime = 1661756943044L;	//42位bit,第1位固定正数0，开始后最大能使用69年
	private long workerIdBits = 5L;				//5bit机器ID
	private long datacenterIdBits = 5L;			//5bit数据中心ID
	private long sequenceBits = 12L;			//12位bit,同毫秒内产生的不同id

	private long workerIdLeft = sequenceBits;	//左移量
	private long datacenterIdLeft = sequenceBits + workerIdBits;
	private long timestampLeft = sequenceBits + workerIdBits + datacenterIdBits;	//22位，64-22=42位
	
	
	private long sequence = 0L;									//每毫秒初始值
	private long lastTimestamp = -1L;							//上一次当前毫秒
	private long sequenceMax = -1L ^ (-1L << sequenceBits); 	//sequenceMax 最大值 4095
	
	
	private IDHelp() {}


	public synchronized Long nextId() {
		long timestamp = timeGen();
		
		if (timestamp < lastTimestamp) {
//			LOG.error(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
		}

		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMax;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}
		
		lastTimestamp = timestamp;
		
		return ((timestamp - startTime) << timestampLeft) | (datacenterId << datacenterIdLeft) | (workerId << workerIdLeft) | sequence;
	}

	private long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}
	
	
	/**
	 * @param workerId	机器ID
	 * @param datacenterId	数据中心ID
	 * @return
	 */
	public static IDHelp getInstance(Integer workerId, Integer datacenterId) {
		if(workerId<0 || datacenterId<0) {
			throw new RuntimeException("workerId || datacenterId < 0");
		}
		
		if(workerId>31 || datacenterId>31) {
			throw new RuntimeException("workerId || datacenterId > 31");
		}
		if(instance==null){
			IDHelp bean= new IDHelp();
			bean.workerId = workerId;
			bean.datacenterId = datacenterId;
			instance = bean;
        }
        return instance;
	}
	
	
	
    
    public static void main(String[] args) {
		IDHelp id = IDHelp.getInstance(0, 0);
		for(int i=0; i<100000; i++) {
			System.out.println(id.nextId());
		}
	}
    
}