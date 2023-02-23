package com.fc.pay.bill.job;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fc.pay.bill.utils.DateUtil;

public class LogParter {

	public static void main(String[] args) throws ParseException {
		
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf("1490264553000"))));
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf("1490778805000"))));
		
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf("1490955692000"))));
		
		System.out.println(UUID.randomUUID().toString());
		
		System.out.println(new Date(1491494400000l));
		
		System.out.println(DateUtil.parseDefaultDateContent("20170410").getTime()/1000);
		System.out.println(DateUtil.parseDefaultDateContent("20170412").getTime()/1000);
		
		System.out.println("PHP-1461859200=> "+DateUtil.makeDefaultTimeFormat(new Date(1461859200000L)));
		
		System.out.println("PHP-1463414399=> "+DateUtil.makeDefaultTimeFormat(new Date(1463414399000L)));
		
		Date date = DateUtil.parseHyphenTimeContent("2017-04-15 00:00:00");
		System.out.println(DateUtil.makeDefaultTimeFormat(date)+" => "+date.getTime());
		
		Date dateStart = DateUtil.parseHyphenTimeContent("2017-02-10 00:00:00");
		System.out.println("start: \t"+DateUtil.makeDefaultTimeFormat(dateStart)+" => "+dateStart.getTime());
		
		Date dateEnd = DateUtil.parseHyphenTimeContent("2017-02-11 00:00:00");
		System.out.println("end:\t"+DateUtil.makeDefaultTimeFormat(dateEnd)+" => "+dateEnd.getTime());
		
		
		//System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf("1491235200000"))));
		
		/**
		ORDER:

		1490942227206=> order try make payment called.time seq:2017-03-31 14:37:07
		1490942228046=> order confirm make payment called. time seq:2017-03-31 14:37:08

		capital

		1490942227595=> capital try record called. time seq:2017-03-31 14:37:07
		1490942228123=> capital confirm record called. time seq:2017-03-31 14:37:08

		redpacket

		1490942227902=> red packet try record called. time seq:2017-03-31 14:37:07
		1490942228304=> red packet confirm record called. time seq:2017-03-31 14:37:08
		
	    */
			
		/**
		System.out.print("dubbo例子耗时分析：");
		long o1 = 1490942227206l;
		long o2 = 1490942228046l;
		long c1 = 1490942227595l;
		long c2 = 1490942228123l;
		long r1 = 1490942227902l;
		long r2 = 1490942228304l;		
		System.out.println(r2 - o1);
		*/
		
		/**
		 * System.out.print("spring例子耗时分析：");
		 */
		/**
		1490944885043 => order try make payment called.time seq:2017-03-31 15:21:25
		1490944885131 => capital try record called. time seq:2017-03-31 15:21:25
		1490944885191 => red packet try record called. time seq:2017-03-31 15:21:25
		1490944885298 => order confirm make payment called. time seq:2017-03-31 15:21:25
		1490944885317 => capital confirm record called. time seq:2017-03-31 15:21:25
		1490944885340 => red packet confirm record called. time seq:2017-03-31 15:21:25
		*/		
		/**
		 * System.out.println(1490944885340l-1490944885043l);
		 */
				
		/**
		
		String yyyyMMdd = DateUtil.makeDefaultDateFormat(DateUtil.addDay(new Date(System.currentTimeMillis()), -1));
		
		System.out.println("START=>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf("20170329000000"))));
		
		System.out.println("END=>"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf("1490264553000"))));
		*/

		/*
		try {
			// read file content from file
			//StringBuffer sb = new StringBuffer("");

			FileReader reader = new FileReader("F:/catalina.out/catalina.out.bak");
			BufferedReader br = new BufferedReader(reader);

			
			List<String> lineDataList = new ArrayList<String>();
			String str = null;
			int part = 1;
			while ((str = br.readLine()) != null) {
				//sb.append(str + "/n");
				//System.out.println(str);
				if(lineDataList.size()==1000){
					write(lineDataList, part);
					lineDataList.clear();
					part++;
				}
				lineDataList.add(str);				
			}

			br.close();
			reader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}
	
	private static void write(List<String> lineDataList, int i) throws IOException{
		System.out.println("文件=>"+i);
		// write string to file
		FileWriter writer = new FileWriter("F:/catalina.out/catalina.out."+i+".txt");
		BufferedWriter bw = new BufferedWriter(writer);
		for(String line : lineDataList){
			bw.write(line+"\n");
		}		
		bw.close();
		writer.close();
	}

}
