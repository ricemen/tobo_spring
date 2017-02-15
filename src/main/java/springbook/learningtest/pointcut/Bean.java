package springbook.learningtest.pointcut;

import org.junit.Test;

public class Bean {
	public void method() throws RuntimeException {
		
	}
	
	@Test
	public void testReflect() throws NoSuchMethodException, SecurityException {
		System.out.println(Target.class.getMethod("minus", int.class, int.class));
	}
}
