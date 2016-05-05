import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

import com.delta.cq.core.util.HttpUtil;


public class GettingHtmlContent {
	public static void main(String args[]) throws HttpException, IOException 
	{
		String baseURL="//st.delta.com";
		//System.setProperty("javax.net.debug", "all");
		//HttpPost httpPost = new HttpPost("https://pro-si.delta.com/dlk/dlrest/auth/usersession");

     // HttpResponse resp = httpclient.execute(httpPost);
		boolean isHomePage = false;
		//String loginURL = "http:" + baseURL + "/widgetnav/pages/custlogin/index.jsp?isHomePage=" + isHomePage; 
	    //String widgetURL = "http://stg1cdnw1.delta.com/content/dam/mobile/tablet/ipad/content/pub/status.json"; 
	    String testURL="https://pro-si.delta.com/dlk/dlrest/auth/usersession";
	    //String htmlString=;
	    String s = "";
	   // s =HttpUtil.getHttpContent(testURL);
	    HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(testURL);
        method.addRequestHeader("Accept","text/html,application/json;q=0.9,*/*;q=0.8");
    	method.addRequestHeader("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");
    	String cookieName="dls_session_id";
    	String cookieValue="BJfcWXSmpF5aFDmO1%2BThtMpBUpn8a5G3dLA5M6mk%2F0cc1%2Fw%2FWX5d24K3DnPrqFLNbE25VsiCNtoPHahpKwTlOwB7ADbLb%2FyrtuhfAQ0OJ4aR6Dd1qzO9Fty8Rs4JyPYdlDDqc6oNNqSl8xHa%2BE3Rcz%2F8px2w%2Bol3K5LpIQnpeZHnxdewURCRTTKoU9XHDxTn";
    	method.addRequestHeader("Cookie",cookieName+"="+cookieValue);
    	int statusCode = client.executeMethod(method);
    	InputStream responseBodyAsStream = method.getResponseBodyAsStream();
		s = IOUtils.toString(responseBodyAsStream);

	    //IOUtils.copy(input, output)
	    System.out.println(s);
	    
	    //boolean a = s.contains("faresStartAt:80");
	    //System.out.println(a);
	    //String s1[] = s.split("gbjAllowedSSID\":");
	    //List l = new ArrayList();
	    //System.out.println(s1[1].replace("\"", "").replace("}",""));
	    
	}

}
