import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class HashMapSorting {
	public static Map<String,Date> sortedMap = new HashMap<String,Date>();
	public static void main(String args[])
	{
		HashMap<String,Date> m = new HashMap<String,Date>();
		m.put("a", new Date(2014,6,10));
		m.put("b", new Date(2014,6,12));
		m.put("c", new Date(2014,6,4));
		m.put("d", new Date(2014,6,1));
		m.put("e", new Date(2014,5,15));
		sortedMap = sortHashMapByValuesD(m);
		for(String key: sortedMap.keySet())
		{
			System.out.println(key+" -----"+sortedMap.get(key));
		}
		System.out.println(getNext("d"));
	}
	public static String getNext(String key)
	{
		List<String> mapList = new ArrayList<String>(sortedMap.keySet());
		return mapList.get(mapList.indexOf(key)+1);
	}
	public static LinkedHashMap<String,Date> sortHashMapByValuesD(HashMap<String, Date> passedMap) {
		   List<String> mapKeys = new ArrayList<String>(passedMap.keySet());
		   List<Date> mapValues = new ArrayList<Date>(passedMap.values());
		   Collections.sort(mapValues);
		   Collections.sort(mapKeys);

		   LinkedHashMap<String,Date> sortedMap = new LinkedHashMap<String,Date>();

		   Iterator<Date> valueIt = mapValues.iterator();
		   while (valueIt.hasNext()) {
		       Object val = valueIt.next();
		       Iterator<String> keyIt = mapKeys.iterator();

		       while (keyIt.hasNext()) {
		           Object key = keyIt.next();
		           String comp1 = passedMap.get(key).toString();
		           String comp2 = val.toString();

		           if (comp1.equals(comp2)){
		               passedMap.remove(key);
		               mapKeys.remove(key);
		               sortedMap.put((String)key, (Date)val);
		               break;
		           }

		       }

		   }
		   return sortedMap;
		}
}
