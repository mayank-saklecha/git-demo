import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;


public class TimeExample {
	public static void main(String args[]) throws Exception
	{
		//getMapByMonth(2);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String gmtStrDate = sdf.format(Calendar.getInstance().getTime());

        System.out.println("GMT 1"+gmtStrDate);

        Date gmtDate = sdf.parse(gmtStrDate);

        System.out.println("GMT 2"+gmtDate);
		/*SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
		Calendar cal = Calendar.getInstance();
		System.out.println(fmt.format(cal.getTime()));*/
        int i =8;
        System.out.println(i/3);
        System.out.println(i%3);
	}
	
	private static Map<String,List> getMapByMonth(int numberOfYears)
	{
		Map<String,List> monthMap = new TreeMap<String,List>();
		Calendar cal = Calendar.getInstance();
		int currentMonth = cal.get(Calendar.MONTH);
		SimpleDateFormat fmt = new SimpleDateFormat("MMMMM-yyyy");
		for(int j=0;j<=currentMonth;j++)
		{
			String currentMonthYear = fmt.format(cal.getTime());
			cal.roll(Calendar.MONTH, false);
			monthMap.put(currentMonthYear.toLowerCase(), new LinkedList());
		}
		while(numberOfYears>1)
		{
			cal.roll(Calendar.YEAR, false);
			for(int j=0;j<12;j++)
			{
				String currentMonthYear = fmt.format(cal.getTime());
				cal.roll(Calendar.MONTH, false);
				monthMap.put(currentMonthYear.toLowerCase(), new LinkedList());
			}
			numberOfYears--;
		}
		for(String key : monthMap.keySet())
		{
			System.out.println(key);
		}
		return monthMap;
	}
}
