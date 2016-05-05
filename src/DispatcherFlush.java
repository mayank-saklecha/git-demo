import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
public class DispatcherFlush {

	public static void main(String args[])
	{
		//PostMethod method=null;
		PostMethod method = null;
		HttpClient client = new HttpClient();
		String url="http://dvl7.delta.com/dlsearch/getBaynoteRelatedLinks";
		//String url = "http://localhost:4502/bin/delta/flushcache?path=/content/www/en_US";
		try{

			for(int i=1;i<=10;i++)
			{
				String ip = "stg1cdnw"+i+".delta.com";
				String pageUrl = "/content/dam/delta-applications/css/cart/1.0.0/cart-upsell-print.css";
				invalidatePage(ip,pageUrl);
			}
			System.out.println("Flushing successfull");
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error occured"+e);
		}
		finally
		{
			//method.releaseConnection();
		}

	}
	private static boolean invalidatePage(String server, String pageurl)

    {

        boolean retFlag=false;

        PostMethod method=null;
        HttpClient client = new HttpClient();
		

         if(server==null || pageurl==null)return false;

        String url="http://"+server+"/dispatcher/invalidate.cache";

  try{

        method = new PostMethod(url);

        method.addRequestHeader("CQ-Action", "Activate");

        method.addRequestHeader("CQ-Handle", pageurl);

        int statusCode = client.executeMethod(method);



        if (statusCode != HttpStatus.SC_OK) {
        	System.out.println("success!!");
            //log.error("ApacheCacheInvalidator Exception:"+statusCode+" "+method.getStatusLine());

            return false;

        }

        BufferedReader sbr = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));

        String curLine = null;

        StringBuffer retStr = new StringBuffer();

        while ((curLine = sbr.readLine()) != null) {

            retStr.append(curLine);

        }

        retFlag=true;

    }catch(Exception e){

       // log.error("ApacheCacheInvalidator Exception:"+e.getMessage());
    	e.printStackTrace();
        retFlag=false;

    } finally {

            method.releaseConnection();

        }  
  return retFlag;
}
}
