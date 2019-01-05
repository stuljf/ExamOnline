package henu.test;

import java.util.*;

public class TestMap {

	public static void main(String[] args) {
		
		Map<Integer, Date> map = new Hashtable<Integer, Date>();
		
		map.put(1, new Date(23454000));
		map.put(2, new Date(23545000));
		map.put(3, new Date(2454000));
		map.put(4, new Date(2654000));
		map.put(6, new Date(23454000));
		map.put(5, new Date(123000));
		
		map.put(6, new Date(2345400));
		Date d=map.get(7);
		if(d==null) {
			System.out.println("null");
		}else{
			System.out.println("not");
		}
		map=sortByValue(map);
		
		Iterator<Map.Entry<Integer, Date>> iterator = map.entrySet().iterator();
		
		while (iterator.hasNext()) {
		    Map.Entry<Integer, Date> entry = iterator.next();
		    System.out.println(entry.getKey().toString() + "　：" + entry.getValue().toString());
		}
	}
	
	public static Map<Integer, Date> sortByValue(Map<Integer, Date> map) {
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
			System.out.println(entry.getKey().toString() + "　：" + entry.getValue().toString());
			resMap.put(entry.getKey(), entry.getValue());
		}
		System.out.println("------");
		return resMap;
		
	}

}
