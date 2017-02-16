package springbook.learningtest.jdk;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectionTest {

	@Test
	public void invokeMethod() throws Exception {
		
		String name = "Spring";
		
		assertThat(name.length(), is(6));
		
		Method lengthMothod = String.class.getMethod("length");
		
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		
		assertThat((Character)charAtMethod.invoke(name, 0), is('S'));
		
	}	 
}
