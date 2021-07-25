package test;

import java.sql.Timestamp;

public class TimeStamp {

	public static void main(String[] args) {
		long dateTime = System.currentTimeMillis();
		Timestamp ts = new Timestamp(dateTime);	
		System.out.println(ts);
	}
}