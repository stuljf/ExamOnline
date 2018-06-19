package henu.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @Describtion: (Excel的写操作). <br/> 
 * @date: 2018年5月31日 下午8:42:53 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public class ExcelWriter {
	//工作簿
	private Workbook workbook;
	//工作表
	private Sheet sheet;
	//行
	private Map<Integer, Row> rows = new HashMap<>();
	
	public ExcelWriter() {
		this.workbook = new XSSFWorkbook();
		this.sheet = workbook.createSheet("学生成绩");
	}
	
	public void write(int r, int c, String value) {
		//获取行参数，如果为空就创建
		Row row = rows.get(r);
		if (row == null) {
			row = sheet.createRow(r);
			rows.put(r, row);
		}
		
		//写入数据
		Cell cell = row.createCell(c);
		cell.setCellType(CellType.STRING);
		cell.setCellValue(value);
	}
	
	/**
	 * @Description:(将Excel写入流中). <br/> 
	 * @param out
	 * @return
	 */
	public boolean saveToFile(OutputStream out) {
		try {
			workbook.write(out);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void close() throws IOException {
		this.workbook.close();
	}
}
