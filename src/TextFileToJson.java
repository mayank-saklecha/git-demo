import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Scanner;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;


public class TextFileToJson  {
	public static void main(String[] args)throws Exception
	{
		File f = new File("c:\\test\\timezone.txt");
		//FileInputStream fin = new FileInputStream(f);
		Scanner s = new Scanner(f);
		JSONArray timezoneArray = new JSONArray();
		while(s.hasNext())
		{
			String timezone = s.next();
			JSONObject timezoneObject = new JSONObject();
			timezoneObject.put("text", timezone);
			timezoneObject.put("value", timezone);
			timezoneArray.put(timezoneObject);
		}
		Writer writer = new FileWriter(new File("c:\\test\\timezone.json"));
		timezoneArray.write(writer);
		writer.close();
		s.close();
		f = null;
	}
}
