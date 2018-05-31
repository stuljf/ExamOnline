package henu.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @Describtion: (Excel的读操作). <br/> 
 * @date: 2018年5月31日 下午8:42:53 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public class ExcelReader {
	//工作簿
	private Workbook workbook;
	//工作表
	private Sheet sheet;
	//行
	private Map<Integer, Row> rows = new HashMap<>();
	
	private ExcelReader() {};
	public static ExcelReader readFile(File file) throws Exception {
		ExcelReader reader = new ExcelReader();
		//创建Workbook实例
		reader.workbook = WorkbookFactory.create(file);
		reader.sheet = reader.workbook.getSheetAt(0);
		return reader;
	}
	public static ExcelReader readFile(InputStream in) throws Exception {
		ExcelReader reader = new ExcelReader();
		//创建Workbook实例
		reader.workbook = WorkbookFactory.create(in);
		reader.sheet = reader.workbook.getSheetAt(0);
		return reader;
	}
	
	public String read(int r, int c) {
		//判断rows中是否有row对象，如果有取出，如果没有创建并加入
		Row row = rows.get(r);
		if (row == null) {
			row = sheet.getRow(r);
			rows.put(r, row);
		}
	
		//获取当前区域的值
		Cell cell = row.getCell(c);
		cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}
	
	public int getTotalRows() {
		return sheet.getLastRowNum() + 1;
	}
	
	public void close() throws IOException {
		this.workbook.close();
	}
}
