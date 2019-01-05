package henu.auto;

import java.sql.SQLException;
import java.util.*;
import org.springframework.stereotype.Component;
import henu.dao.ExamDao;
import henu.entity.Exam;
import henu.util.SpringUtil;

@Component
public class ExamQueue {
	private static Map<Integer, Date> begin=new LinkedHashMap<Integer, Date>();
	private static Map<Integer, Date> close=new LinkedHashMap<Integer, Date>();
	
	public void initQueue() throws SQLException{
		ExamDao examDao=(ExamDao) SpringUtil.getBean("examDaoImpl");
		List<Exam>	examc = examDao.getExamsByState("created");
		List<Exam> examb = examDao.getExamsByState("begined");
		if(examc!=null && examb!=null) {
			Iterator<Exam> ic=examc.iterator();
			while(ic.hasNext()) {
				Exam e=ic.next();
				begin.put(e.getId(), e.getStarttime());
				close.put(e.getId(), e.getEndtime());
			}
			Iterator<Exam> ib=examb.iterator();
			while(ib.hasNext()) {
				Exam e=ib.next();
				close.put(e.getId(), e.getEndtime());
			}
		
			begin=sortByValue(begin);
			close=sortByValue(close);
		}
		
	}
	
	public synchronized void pushBegin(Integer id, Date date) {
		begin.put(id, date);
		begin=sortByValue(begin);
		print(begin);
	}
	
	public synchronized void pushClose(Integer id, Date date) {
		close.put(id, date);
		close=sortByValue(close);
		print(close);
	}
	
	public synchronized void popBegin(Integer id) {
		begin.remove(id);
	}
	
	public synchronized void popClose(Integer id) {
		close.remove(id);
	}
	
	public synchronized Map.Entry<Integer, Date> getRecentBegin() { 
		Iterator<Map.Entry<Integer, Date>> i=begin.entrySet().iterator();
		if(i.hasNext()) {
			return i.next();
		}
		return null;
	}
	
	public synchronized Map.Entry<Integer, Date> getRecentClose() { 
		Iterator<Map.Entry<Integer, Date>> i=close.entrySet().iterator();
		if(i.hasNext()) {
			return i.next();
		}
		return null;
	}
	
	private Map<Integer, Date> sortByValue(Map<Integer, Date> map) {
		List<Map.Entry<Integer, Date>> list = new ArrayList<Map.Entry<Integer, Date>>(map.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<Integer, Date>>() {
		    @Override
		    public int compare(Map.Entry<Integer, Date> mapping1, Map.Entry<Integer, Date> mapping2) {
		        return mapping1.getValue().compareTo(mapping2.getValue());
		    }
		});
		
		Iterator<Map.Entry<Integer, Date>> iterator = list.iterator();
		
		Map<Integer, Date> resMap = new LinkedHashMap<Integer, Date>();
		
		while(iterator.hasNext()) {
			Map.Entry<Integer, Date> entry = iterator.next();
			resMap.put(entry.getKey(), entry.getValue());
		}
		return resMap;
	}

	private void print(Map<Integer, Date> map) {
		Iterator<Map.Entry<Integer, Date>> it=map.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<Integer, Date> e=it.next();
			System.out.println(e.getKey().toString()+":"+e.getValue().toString());
		}
		System.out.println("--------------");
	}
	
}
