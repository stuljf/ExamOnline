package henu.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPUtil {

	private static Logger log = LoggerFactory.getLogger(IPUtil.class);

	public static String getRealIP(HttpServletRequest request) {
			String ip = request.getHeader("x-forwarded-for");  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("Proxy-Client-IP");  
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("WL-Proxy-Client-IP");  
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("HTTP_CLIENT_IP");  
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
			}  
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				ip = request.getRemoteAddr();  
			}  
			log.error("All the IP address string is: " + ip);  
			return ip;  
		}  
	}
