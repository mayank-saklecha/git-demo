import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.day.text.Text;


public class CalendarFormatting {
	public static void main(String[] args)
	{
		DateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");
	    Date dt = new Date();
	    System.out.println(outputFormat.format(Calendar.getInstance().getTime()));
	    Calendar cal1= Calendar.getInstance();
	    System.out.println(cal1.getTimeZone().getID());
	    Calendar cal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        for(int i=0;i<2;i++)
	    {
	    	cal.set(Calendar.DATE,1);
        	cal.set(Calendar.HOUR, 0);
        	cal.set(Calendar.MINUTE, 0);
        	cal.set(Calendar.SECOND, 0);
        	endCal.set(Calendar.HOUR, 23);
        	endCal.set(Calendar.MINUTE, 59);
        	endCal.set(Calendar.SECOND, 59);
        	endCal.set(Calendar.MONTH, endCal.get(Calendar.MONTH)+i);
        	endCal.set(Calendar.DATE, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
        	cal.set(Calendar.MONTH,cal.get(Calendar.MONTH)+i);
        	System.out.println(cal.getTime());
        	//System.out.println(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        	System.out.println(endCal.getTime());
	    }
	}
}
