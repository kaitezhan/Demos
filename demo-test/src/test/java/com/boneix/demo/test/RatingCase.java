package com.boneix.demo.test;

import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boneix.demo.domain.Rating;
import com.google.common.base.Stopwatch;

public class RatingCase {
    private static Logger logger = LoggerFactory.getLogger(RatingCase.class);
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	/**
	 * 通过反射遍历枚举对象
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void testRatingByClazz(){
		Stopwatch sw=Stopwatch.createStarted();
		Class clz=Rating.class;
		for(Object obj:clz.getEnumConstants()){
			System.out.println(obj);
		}
		sw.stop();
		logger.info("this fuction cast time:,{}",sw.toString());
		System.out.println(sw.toString());
	}
	
	/**
	 * 通过枚举类的values()遍历枚举对象
	 */
	@Test
	public void testRatingByValues(){		
		Stopwatch sw=Stopwatch.createStarted();
		for(Rating rate:Rating.values()){
			System.out.println(rate);
		}
		sw.stop();
		System.out.println(sw.toString());
	}
	
	
	@Test
	public void testCompare(){		
		Stopwatch sw=Stopwatch.createStarted();
		doCompare();
		sw.stop();
		System.out.println(sw.toString());
	}

	private void doCompare() {
		BigDecimal bd1=new BigDecimal(0);
		BigDecimal bull=null;
		System.out.println(bd1.compareTo(bull));
		
	}
}
