package henu.util;

import java.util.List;

/**
* @ClassName: PageBean 
* @Description: 需要分页显示的数据这个工具类来包装
* @author 姚亚强
* @date 2018年4月10日 下午6:11:34 
* 
* @param <T>
 */
public class PageBean<T> {
	
	/**
	 * 当前页码
	 */
	private int currentPage = 1;
	/**
	 * 每页的记录数
	 */
	private int pageCount = 10;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 页内数据
	 */
	private List<T> pageData;
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalCount % pageCount == 0 ? 
				totalCount / pageCount : totalCount / pageCount + 1;
	}
	public List<T> getPageData() {
		return pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
}
