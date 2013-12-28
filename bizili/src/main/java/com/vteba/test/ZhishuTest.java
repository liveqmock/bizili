package com.vteba.test;


public class ZhishuTest {
	public static void main(String[] args) {
		int count = 0;
		for (int i = 50; i <= 100; i++) {
			boolean z = true;//默认，认为该数是质数
			for (int j = 2; j < i; j++) {
				if (i % j ==0){//只要被2到i-1的数整除，i就不是质数
					z = false;
					break;
				}
			}
			if (z) {//如果是质数，求和
				System.out.println(i);
				count += i;
			}
		}
		System.out.println(count);
		
	}
}
