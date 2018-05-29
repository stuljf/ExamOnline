package henu.util;

import java.util.List;
/**
 * Bootstrap分页插件服务端分页所需数据格式
 * @param <T>
 */
public class BootstrapPageResult<T> {

	public BootstrapPageResult() {}
	public BootstrapPageResult(int total, List<T> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	private int total;
	private List<T> rows;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	
}
