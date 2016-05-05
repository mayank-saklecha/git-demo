import org.apache.sling.commons.json.JSONObject;


public class JsonExample  {
public static void main(String[] args)
{
	JSONObject obj = new JSONObject();
	String[] strArr ={"dvl1","dvl2","dvl3"};
	try
	{
		obj.put("stringArr",strArr);
		Object obj1 = obj.get("stringArr");
		String[] arr1 = (String[])obj1;
		for(String arrStr:arr1)
		{
			System.out.println(arrStr);
		}
	}catch(Exception e)
	{
		System.out.println(e);
	}
	
}
}
