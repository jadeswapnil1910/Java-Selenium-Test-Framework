package com.orangehrm.test;

import org.testng.annotations.Test;

import com.orangehrm.base.BaseClass;

public class TestThreads extends BaseClass {
	
	
	
	@Test
	void test_01() {
		
		logger.info(Thread.currentThread().threadId() + " --> " + Thread.currentThread().getName());
		
	}
	
	@Test
	void test_02() {
		
		logger.info(Thread.currentThread().threadId() + " --> " + Thread.currentThread().getName());
	}

}
