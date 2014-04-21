package com.vteba.test;

import java.util.concurrent.ForkJoinPool;

public class ForkJoinTest {
	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		PrintTask printTask = new PrintTask();
		forkJoinPool.submit(printTask);
		
	}
}
