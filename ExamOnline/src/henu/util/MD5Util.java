package henu.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName: MD5Util 
 * @Description:MD5加密
 * @author 姚亚强
 * @date 2018年4月10日 下午6:13:28 
 *
 */
public class MD5Util {

	/**
	 * @param plainText 要加密的密文
	 * @return md5code 32位十六进制字符串
	 */
	public static String md5(String plainText) {

		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("MD5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("没有md5这个算法！");
		}

		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}

		return md5code;
	}

}