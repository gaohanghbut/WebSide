package cn.hang.mvc.type.impl;

import org.junit.Test;

public class FloatConveterTest {

	@Test
	public void test() {
		try {
			System.out.println("int class : " + int.class);
			Class<?> c = Class.forName("int");
			System.out.println(c);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
