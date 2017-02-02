package com.springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;

public class Calculator {

	public Integer calMultiply(String filepath) throws IOException {
		LineCallback multiplyCallback = new LineCallback() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filepath, multiplyCallback, 1);
	}
	
	public Integer calcSum(String filepath) throws IOException {
		LineCallback sumCallback = new LineCallback() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		};
		
		return lineReadTemplate(filepath, sumCallback, 0);
	}
	
	public Integer lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException {
		BufferedReader br = null;
		Integer ret = initVal;
		try {
			br = new BufferedReader(new FileReader(filepath));
			String line = null;
			while((line = br.readLine()) != null) {
				ret = callback.doSomethingWithLine(line, ret);						
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(br != null) try { br.close(); } catch(Exception e) {System.out.println(e.getMessage());}
		}
		
		return ret;
	}
	
	public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
		BufferedReader br = null;
		int ret = 0;
		try {
			br = new BufferedReader(new FileReader(filepath));
			ret = callback.doSomethingWithReader(br);			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if(br != null) try { br.close(); } catch(Exception e) {System.out.println(e.getMessage());}
		}
		
		return ret;
	}
}
