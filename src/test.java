

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.JSONTokener;

public class test {

	public static void main(String args[]) throws Exception
	{
		File file = new File("C:\\test\\fares.json");
		Scanner s = new Scanner(file);
		System.out.println("June 2016 Change");
		StringBuffer buf = new StringBuffer();
		while (s.hasNext()) {
		    buf.append(s.nextLine());
		}
		//JSONArray arr = CDL.toJSONArray(buf.toString());
		JSONObject j = new JSONObject(new JSONTokener(buf.toString()));
		//JSONObject[] jj = j.data
		JSONArray arr = j.getJSONArray("fares");
		for(int i=0;i<arr.length();i++)
		{
			JSONObject obj = arr.getJSONObject(i);
			Iterator<String> itr = obj.keys();
			while(itr.hasNext())
			{
				String key = itr.next();
				System.out.println("Key----->"+i+"-->"+key+"---value---->"+obj.getString(key));
			}
		}
		System.out.println(j.getJSONArray("fares"));
		//String a = arr.getString(1);
		//System.out.println(a);
		
		String imageName = "/content/dam/mobile/tablet/ipad/content/magazines/SkyJan2013Final.pdf";
		imageName = imageName.substring(imageName.lastIndexOf("/")+1,imageName.length());
		System.out.println(imageName);
		String testUrl = "https://www.delta.com/content/dam/mobile/tablet/ipad/content/magazines/SkyJune2012Final.pdf";
		
			System.out.println(testUrl.replaceAll("^(http|https|ftp)://www.delta.com",""));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			String dateStr = "2012-10-01T10:00:42+00:00";
			Date dt = dateFormat.parse(dateStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			//System.out.println(cal.toString());
			String[] str = {"abc","def"};
			JSONArray j1 = new JSONArray(Arrays.asList(str));
			//JSONObject obj = j1.getJSONObject(1);
			
			Date dt1 = new Date();
			dt1.setTime(1359770258);
			System.out.println(dt1);
	}
}
