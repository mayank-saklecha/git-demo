import java.text.SimpleDateFormat;
import java.util.Date;


public class DateComparison {
	public static void main(String[] args) throws Exception
	{
		String currDt = "2015-07-01";
		String startDt = "2015-07-01";
		String endDt = "2015-07-01";
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-dd-mm");
		Date currentNodeDate = dtFormat.parse(currDt);
        Date startDate = dtFormat.parse(startDt);
        Date endDate = dtFormat.parse(endDt);
        System.out.println((currentNodeDate.after(startDate) && currentNodeDate.before(endDate) || (currentNodeDate.equals(startDate) || currentNodeDate.equals(endDate))));
	}
}
