package henu.test;

import java.io.File;

import org.junit.Test;

import henu.util.ExcelReader;

public class TestReadExcel {

	@Test
	public void testExcel() throws Exception {
		
		File file = new File("d:/1.xlsx");
		
		ExcelReader reader = ExcelReader.readFile(file);
		
		String id = reader.read(1, 0);
		String name = reader.read(1, 1);
		String clazz = reader.read(1, 2);
		
		System.out.println(id + "--" + name + "--" + clazz);
	}
	
}
