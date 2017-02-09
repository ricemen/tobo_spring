package springbook.learningtest.jdk;

public class HelloUppercase implements Hello {
	
	Hello hello;

	public HelloUppercase(Hello hello) {
		super();
		this.hello = hello;
	}

	@Override
	public String sayHello(String name) {
		return hello.sayHello(name).toUpperCase();
	}

	@Override
	public String sayHi(String name) {
		return hello.sayHi(name).toUpperCase();
	}

	@Override
	public String sayThankYou(String name) {
		return hello.sayThankYou(name).toUpperCase();
	}

	@Override
	public int sayInt(int number) {
		// TODO Auto-generated method stub
		return number;
	}

}
