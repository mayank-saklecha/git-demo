import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;


public class FileParsing {
	public static void main(String args[]) throws Exception
	{
		File file = new File("c:\\abc.txt");
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		    String line = null;
		    Pattern p = Pattern.compile("s[0-9]*.countryName=\"[A-Za-z]*\"",Pattern.MULTILINE);
		    Pattern p1 = Pattern.compile("s[0-9]*.isoCode=\"[a-z]*\"",Pattern.MULTILINE);
		    Matcher m = null;
		    Matcher m1 = null;
		    JSONArray jArr = new JSONArray();
		    JSONObject jObj = new JSONObject();
		    jObj.put("text", "United States");
		    jObj.put("value","us");
		    jArr.put(jObj);
		    while ((line = br.readLine()) != null) {
		       // process the line.
		    	//System.out.println(line);
		    	m = p.matcher(line);
		    	//System.out.println(line);
		    	//System.out.println("0000");
		    	if(m.find())
		    	{
		    		String[] countryNames = m.group(0).split("=");
		    		JSONObject jObject = new JSONObject();
		    		jObject.put("text", countryNames[1].replaceAll("\"",""));
		    		m1 = p1.matcher(line);
		    		if(m1.find())
		    		{
		    			jObject.put("value",m1.group(0).split("=")[1].replaceAll("\"",""));
		    		}
		    		jArr.put(jObject);
		    		//System.out.println(m.group(0));
		    		
		    		
		    	}
		    	
		    }
		    File file1 = new File("c:\\countryList.json");
    		BufferedWriter writer = new BufferedWriter(new FileWriter(file1));
    		writer.write(jArr.toString());
    		writer.close();
    		file = null;
    		file1 = null;
		    System.out.println(jArr.toString());
		    br.close();
	}
}
