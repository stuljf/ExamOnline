package henu.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.junit.Test;

import henu.util.ExcelWriter;
import henu.util.FtpUtil;

public class TestWriteExcel {

	@Test
	public void testExcel() throws Exception {
		
		ExcelWriter writer = new ExcelWriter();
		
		writer.write(0, 0, "学号");
		writer.write(0, 1, "姓名");
		writer.write(0, 2, "科目");
		writer.write(0, 3, "班级");
		writer.write(0, 4, "成绩");
		writer.write(1, 0, "1001");
		writer.write(1, 1, "张三");
		writer.write(1, 2, "JAVA");
		writer.write(1, 3, "15-4");
		writer.write(1, 4, "90");
		
//		FileOutputStream fout = new FileOutputStream("d:/out.xlsx");
//		boolean flag = writer.saveToFile(fout);
		
		ByteArrayOutputStream baso = new ByteArrayOutputStream();
		boolean flag = writer.saveToFile(baso);
		System.out.println(flag);
		
		writer.close();
		
		byte[] array = baso.toByteArray();
		InputStream in = new ByteArrayInputStream(array);
		FtpUtil.uploadFile("47.100.101.31", 21, "free", "free", "./", "test", in);
	}
	
}
