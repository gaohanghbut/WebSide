package cn.hang.mvc.type.impl;

import java.lang.reflect.Method;

import org.junit.Test;

public class IntegerConverterTest {

	@Test
	public void testConvert() {
		IntegerConverter converter = new IntegerConverter();
		Integer in = converter.convert("222");
		Class<?> c = TestSetter.class;
		try {
			Object obj = c.newInstance();
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				if (method.getName().equals("setIn")) {
					method.invoke(obj, in);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static class TestSetter {
		public void setIn(int in) {
			System.out.println(in);
		}
	}
}
