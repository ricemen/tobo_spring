package com.springbook.learningtest.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CalcSumTest {
	Calculator calculator;
	String numFilepath;
	
	@Before
	public void setUp() {
		this.calculator = new Calculator();
		this.numFilepath = getClass().getResource("number.txt").getPath();
	}

	@Test
	public void sumOfNumber() throws IOException {
		assertThat(calculator.calcSum(numFilepath), is(10));
	}
	
	@Test
	public void multiplyOfNumber() throws IOException {
		assertThat(calculator.calMultiply(numFilepath), is(24));
	}
}
