package henu.auto;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ExamScore {
	private static Map<String, String> scores = new Hashtable<String, String>();
	
	public void set(String id, String score) {
		scores.put(id, score);
	}
	
	public String get(String id) {
		return scores.get(id);
	}
	
	public void rm(String id) {
		scores.remove(id);
	}
}
