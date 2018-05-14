package henu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @ClassName: XMLUtil 
 * @Description: 针对XML的读写操作
 * @author 姚亚强
 * @date 2018年4月10日 下午9:20:37 
 * @version V1.0   
 */
public class XMLUtil {

	/**
	 * 加载DOM树
	 * @param xmlPath	加载xml文件的路径
	 * @return
	 */
	public static Document loadXML(String xmlPath) {
		//创建Document对象
		Document doc = null;

		try {
			SAXReader reader = new SAXReader();
			doc = reader.read(new File(xmlPath));
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return doc;
	}

	/**
	 * 修改后的XML重新写入磁盘
	 * @param doc		DOM树
	 * @param xmlPath	要写入的文件的路径
	 */
	public static void storeXML(Document doc, String xmlPath) {
		try {
			//输出流
			OutputStream out = new FileOutputStream(xmlPath);
			//输出格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("UTF-8");
			
			//创建XMLWriter对象
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(doc);
			
			//关闭流
			out.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Description:(查询XML数据). <br/> 
	 * @param doc
	 * @param regex
	 * @return
	 */
	public static String getByXPath(Document doc, String regex) {
		Element userNode = (Element) doc.selectSingleNode(regex);
		return userNode.getTextTrim();
	}
	
	/**
	 * @Description:(修改节点文本). <br/> 
	 * @param e
	 * @param text
	 * @return
	 */
	public static boolean setElementText(Document doc, String regex, String text) {
		try {
			Element e = (Element) doc.selectSingleNode(regex);
			e.setText(text);
			return true;
		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
	}
}
