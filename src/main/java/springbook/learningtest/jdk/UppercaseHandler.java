package springbook.learningtest.jdk;

import java.lang.reflect.Method;

public class UppercaseHandler implements java.lang.reflect.InvocationHandler {
	
//	Hello target;
//	
//	public UppercaseHandler(Hello target) {
//		super();
//		this.target = target;
//	}
	
	Object target;

	public UppercaseHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object ret = (Object) method.invoke(target, args);
		if(ret instanceof String) return ((String)ret).toUpperCase();
		else return ret;
	}

}
