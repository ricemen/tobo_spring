package com.springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public Integer calMultiply(String filepath) throws IOException {
		LineCallback<Integer> multiplyCallback = new LineCallback<Integer>() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value * Integer.valueOf(line);
			}
		};
		return lineReadTemplate(filepath, multiplyCallback, 1);
	}
	
	public Integer calcSum(String filepath) throws IOException {
		LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
			@Override
			public Integer doSomethingWithLine(String line, Integer value) {
				return value + Integer.valueOf(line);
			}
		};
		
		return lineReadTemplate(filepath, sumCallback, 0);
	}
	
	public String concatenate(String filepath) throws IOException {
		LineCallback<String> callback = new LineCallback<String>() {
			@Override
			public String doSomethingWithLine(String line, String value) {
				return value + line;
			}
		};
		return lineReadTemplate(filepath, callback, "");
	}
	
	public <T>T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
		BufferedReader br = null;
		T ret = initVal;
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
