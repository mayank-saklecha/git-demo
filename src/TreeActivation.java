import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
public class TreeActivation {

	public static void main(String args[]) throws Exception
	{
		DefaultHttpClient client = new DefaultHttpClient(); 
		//HttpHost targetHost = new HttpHost("localhost", 4502, "http"); 
		//client.getCredentialsProvider().setCredentials( 
		//new AuthScope(targetHost.getHostName(), targetHost.getPort(),AuthScope.ANY_REALM), 
		//new UsernamePasswordCredentials("admin", "admin")); 

		//String authToken = "?auth_token=abcdefgh"; 
		//Base64 b = new Base64();
		byte[] encode = Base64.encodeBase64(("admin:admin").getBytes()); //update username:password
		HttpPost httpPost = new HttpPost("https://localhost:4502/etc/replication/treeactivation.html");  //update url
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("ignoredeactivated", "true"));
		postParams.add(new BasicNameValuePair("onlyactivated", "true"));
		postParams.add(new BasicNameValuePair("onlymodified", "false"));
		postParams.add(new BasicNameValuePair("path", "/content/www/en_US/shop/hotels")); //update path
		HttpEntity entity = new UrlEncodedFormEntity(postParams);

		httpPost.setEntity(entity);  
		httpPost.setHeader("Authorization", "Basic " + new String(encode));
		System.out.println("executing request " + httpPost.getRequestLine()); 
		HttpResponse httpResponse = client.execute(httpPost); 
		HttpEntity entity1 = httpResponse.getEntity(); 
		System.out.println(httpResponse.getStatusLine()); 
		if(entity != null ){ 
		System.out.println(EntityUtils.toString(entity1)); 
		} 
	}
}
