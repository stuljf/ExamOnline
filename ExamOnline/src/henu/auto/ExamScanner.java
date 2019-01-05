package henu.auto;

import java.util.Timer;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExamScanner {
	
	@Autowired
	private ServletContext servletContext;
	
	public void run() {
		String interval = (String) servletContext.getAttribute("interval");
		int time =Integer.parseInt(interval);
		time*=1000;
		Timer timer=new Timer();
		timer.schedule(new ExamAutoJob(time), 20000, time);
	}
}
