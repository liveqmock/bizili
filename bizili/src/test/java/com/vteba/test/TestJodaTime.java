package com.vteba.test;

import java.util.Date;

import org.joda.time.DateTime;

public class TestJodaTime {
	public static void main(String[] args) {
		DateTime date = new DateTime(new Date());
		DateTime time = date.withDayOfMonth(1);
		int day = date.getDayOfMonth();
		System.out.println(time);
		System.out.println(day);
		
		DateTime dateTime = new DateTime("2012-07");
		dateTime = dateTime.minusMonths(1);//取上一个月
		
		System.out.println(dateTime);
	}
}
