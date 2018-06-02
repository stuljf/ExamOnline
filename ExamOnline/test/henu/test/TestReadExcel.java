package henu.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import henu.entity.Student;
import henu.util.ExcelReader;

public class TestReadExcel {

	@Test
	public void testExcel() throws Exception {

		File file = new File("d:/1.xlsx");

		//获取ExcelReader对象
		ExcelReader reader = ExcelReader.readFile(file);

		//获取行列总数
		int totalRows = reader.getTotalRows();
		//总列数
		int totalCols = reader.getTotalColumns();

		//获取列名
		String[] colNames = new String[totalCols];
		for (int i = 0; i < colNames.length; i++) {
			colNames[i] = reader.read(0, i);
		}

		//根据配置文件放置列
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < colNames.length; i++) {
			if (colNames[i].equals("学号"))
				map.put("学号", i);
			else if (colNames[i].equals("姓名"))
				map.put("姓名", i);
			else if (colNames[i].equals("班级"))
				map.put("班级", i);
		}

		//开始读取
		for (int i = 1; i < totalRows; i++) {
			if (reader.read(i, map.get("学号")).isEmpty())
				break;
			Student s = new Student();
			s.setId(reader.read(i, map.get("学号")));
			s.setName(reader.read(i, map.get("姓名")));
			s.setClazz(reader.read(i, map.get("班级")));
			System.out.println(s.getId() + "-" + s.getName() + "-" + s.getClazz());
		}
		
		reader.close();
	}

}
