package henu.util;

import java.util.List;

/**
 * @ClassName: ResultModel <br/> 
 * @Describtion: Ajax请求的返回值，包含状态、消息和数据. <br/> 
 * @date: 2018年4月17日 下午3:41:10 <br/> 
 * @author Beats <br/> 
 * @version v1.0
 */
public class ResultModel {

	/**
	 * build:(自定以返回值状态和信息). <br/> 
	 * @author Beats  
	 * @param status	状态码：200-500
	 * @param msg		返回信息
	 * @param data		返回数据
	 * @return ResultModel
	 */
	public static ResultModel build(int status, String msg, Object data) {
		return new ResultModel(status, msg, data);
	}
	public static ResultModel build(int status, String msg) {
		return new ResultModel(status, msg, null);
	}
	
	/**
	 * ok:http请求成功，例如导入学生名单成功，返回200. <br/> 
	 * @author Beats 
	 * @param data
	 * @return ResultModel
	 */
	public static ResultModel ok() {
		return new ResultModel();
	}
	
	/**
	 * ok:http请求获取某些信息，例如获取考生名单，返回200和考生名单的json数组. <br/> 
	 * @author Beats 
	 * @param data
	 * @return ResultModel
	 */
	public static ResultModel ok(Object data) {
		return new ResultModel(200, "ok", data);
	}
	
	private ResultModel() {
		this.status = 200;
		this.msg = "ok";
	}
	private ResultModel(int status, String msg, Object data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}
	
	/**
	 * 返回状态码
	 * 默认：正常：200
	 * 异常：500
	 */
	private int status;
	/**
	 * 返回消息
	 * 默认："ok"
	 */
	private String msg;
	/**
	 * 返回消息值
	 * 默认：null
	 */
	private Object data;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * @Description:(获取String值，如果Data是String格式，请用此方法获取值). <br/> 
	 * @return
	 */
	public String getStringData() {
		return (String) data;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getPojoData(Class<T> clazz) {
		return (T) this.data;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getListData(Class<T> clazz) {
		return (List<T>) this.data;
	}
	
}
