package springbook.learningtest.jdk;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;

import org.junit.Test;

public class HelloTest {
	
	@Test
	public void simpleProxy() {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Toby"), is("Hello Toby"));
		assertThat(hello.sayHi("Toby"), is("Hi Toby"));
		assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));
		
		// 프록시 클래스
//		Hello proxiedHello = new HelloUppercase(new HelloTarget());
//		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
//		assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
//		assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
		
		// 다이나믹 프록시 사용
		Hello proxiedHello = (Hello) Proxy.newProxyInstance(
			getClass().getClassLoader(), 
			new Class[] {Hello.class}, 
			new UppercaseHandler(new HelloTarget())
			
		);
		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
		assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
		assertThat(proxiedHello.sayInt(1234), is(1234));
//		
//		System.out.println(getClass().getClassLoader());
//		
//		System.out.println(System.getProperty("sun.boot.class.path"));
//		System.out.println(System.getProperty("java.class.path"));
	}
}
