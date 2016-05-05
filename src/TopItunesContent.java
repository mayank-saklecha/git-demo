import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.JSONTokener;


public class TopItunesContent {
	public static void main(String args[]) throws Exception
	{
		  
		System.out.println(java.nio.charset.Charset.defaultCharset());
		String s =getHttpContent("http://itunes.apple.com/us/rss/topalbums/limit=3/json");
		JSONObject obj = new JSONObject(new JSONTokener(s));
		
		JSONArray arr = obj.getJSONObject("feed").getJSONArray("entry");
		JSONArray finalArr = new JSONArray();
	    for(int i=0;i<1;i++)
	    {
	    	String appleID = arr.getJSONObject(i).getJSONObject("id").getJSONObject("attributes").getString("im:id");
	    	String response = fetchFromItunes(appleID); 
	    	//System.out.println(appleID);
	    	JSONObject obj1 = new JSONObject(new JSONTokener(response));
	    	JSONObject obj2 = obj1.getJSONArray("results").getJSONObject(0);
	    	String artWorkUrl = obj2.getString("artworkUrl100").replaceAll("100x100", "600x600");
	    	obj2.put("artworkName", artWorkUrl);
	    	/*if(obj2.has("copyright"))
	    	{
	    		//String copyright = obj2.getString("copyright").replaceAll("\\u2117", "&#8471;");
	    		String copyright = obj2.getString("copyright");
	    		char[] ch = copyright.toCharArray();
	    		StringBuilder build = new StringBuilder();
	    		for(char c:ch)
	    		{
	    			if(Character.UnicodeBlock.of(c) != Character.UnicodeBlock.BASIC_LATIN)
	    			{
	    				int d = (int)c;
	    				build.append("&#"+d);
	    			}
	    			else
	    			{
	    				build.append(c);
	    			}
	    		}
	    		System.out.println(build);
	    		//obj2.remove("copyright");
	    		obj2.put("copyright",copyright);
	    	}*/
	    	//System.out.println(artWorkUrl);
	    	JSONArray arr1 = obj1.getJSONArray("results");
	    	JSONArray finalArray = new JSONArray();
	    	obj2.put("tracks", finalArray);
	    	finalArr.put(obj2);
	    }
		JSONObject objFinal = new JSONObject();
		objFinal.put("albums", finalArr);
		Writer out = new BufferedWriter(new OutputStreamWriter(
			    new ByteArrayOutputStream(), "UTF-8"));
		PrintStream out1 = new PrintStream(System.out, true, "UTF8");
        System.setOut(out1);
        out.write(objFinal.toString());
        out.close();
        out = null;
        System.out.println(objFinal.toString());
	    
	}
	private static String fetchFromItunes(String itemID) throws Exception
	{
		String ITUNES_API = "https://itunes.apple.com/lookup?id=";
		//String ITUNES_API="http://a1.mzstatic.com/us/r1000/035/Music2/v4/05/2e/6d/052e6db6-5b71-a070-d171-b3fb6ed3ab60/886443938427.60x60-50.jpg";
		//String ITUNES_API="http://itunes.apple.com/lookup?id=649921567&entity=song";
		String s = getHttpContent(ITUNES_API+itemID+"&entity=song");
		//String s = getHttpContent(ITUNES_API);
		StringBuffer resultString = new StringBuffer();
		Pattern regex = Pattern.compile("(https://itunes.apple.com[^\"]+)");
		Matcher regexMatcher = regex.matcher(s);
		while (regexMatcher.find()) {
		  // You can vary the replacement text for each match on-the-fly
			String match = regexMatcher.group();
			String replaceString = match;
		  if(match.contains("?"))
		  {
			  replaceString = replaceString.concat("&");
		  }
		  else
		  {
			  replaceString = replaceString.concat("?");
		  }
		  replaceString = replaceString.concat("AT=11lo8j");
		  regexMatcher.appendReplacement(resultString, replaceString);
		  
		}
		regexMatcher.appendTail(resultString);
		System.out.println(resultString);
		return resultString.toString();
	}
public static String getHttpContent(String url) throws Exception{
        
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        StringBuffer responseString = new StringBuffer();
        final String htmlErrorString = "<!-- End of HttpUtil Call, no data returned, please check error logs! -->";
        
        /*
         * Setting timeout for both socket ( time allowed for data transfer ) and 
         * connection timeout (time allowed to establish connection).
         */
        client.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
    	client.getHttpConnectionManager().getParams().setSoTimeout(60000);
    	
    	
        /*
         * Going to attempt to connect to the provided URL and get content back.
         * HttpMethod needs to call releaseConnection.  Finally block will run unless
         * an exception occurs, thus releaseConnection method is handled in finally 
         * block for both successful and unsuccessful HttpStatuses while method must
         * be called within each exception.  
         */
        try{
        	
        	// Adding this header to solve the gzip_ issue.
        	method.addRequestHeader("Accept-Charset","utf-8");
        	
        	
        	String serverName=InetAddress.getLocalHost().getHostName();
        	String cqInstancePort = System.getProperty("crx.quickstart.info.server.port");
        	
        	//method.addRequestHeader("User-Agent","CQHttpClientAgent" + "_" + serverName + "_" + cqInstancePort);
        	
            int statusCode = client.executeMethod(method);
            
            if (statusCode != HttpStatus.SC_OK) {
                responseString.append("<!-- ");
                responseString.append(url);
                responseString.append(" returned a status of ");
                responseString.append(statusCode);
                responseString.append(" -->");
            } else {
//				byte[] responseBody = method.getResponseBody();
//                responseString.append(new String(responseBody, "utf-8"));
                
            	String responseBody = "";
            	
            	//Determine if the response is gzip encoded
				boolean isGzip = false;
				boolean isImage=false;
				Header[] responseHeaders = method.getResponseHeaders("Content-encoding");
				Header[] contentHeaders = method.getResponseHeaders("Content-Type");
				for (Header header : responseHeaders) {
					HeaderElement[] elements = header.getElements();
					for (HeaderElement headerElement : elements) {
						if (headerElement.getName().equalsIgnoreCase("gzip")) {
							isGzip = true;
						}
					}
				}
				for (Header header : contentHeaders) {
					HeaderElement[] elements = header.getElements();
					for (HeaderElement headerElement : elements) {
						if (headerElement.getName().equalsIgnoreCase("image/jpeg")) {
							isImage = true;
						}
					}
				}
				// Reading responseBodyAsStream is preferred method when the response body size is unknown. Otherwise, you will see
				// this warnings in the logs.
				// org.apache.commons.httpclient.HttpMethodBase Going to buffer response body of large or unknown size. 
				// Using getResponseBodyAsStream instead is recommended.
				if (isGzip) {
					// If the response is gzipped, ungzipp it
					InputStream responseBodyAsStream = method.getResponseBodyAsStream();
					InputStream unGzippedResponseBodyAsStream = new GZIPInputStream(responseBodyAsStream);
					responseBody = IOUtils.toString(unGzippedResponseBodyAsStream);
					
					// Close the InputSTreams
					IOUtils.closeQuietly(unGzippedResponseBodyAsStream);
					IOUtils.closeQuietly(responseBodyAsStream);
					
					responseString.append(responseBody);
				} else {
						InputStream responseBodyAsStream = method.getResponseBodyAsStream();
						char[] ch =	IOUtils.toCharArray(responseBodyAsStream, method.getResponseCharSet());
						responseBody = String.valueOf(ch);
						IOUtils.closeQuietly(responseBodyAsStream);
						
						responseString.append(responseBody);
					
				}
            } 
            
        } catch (HttpException he){
        	method.releaseConnection();
            responseString.append(htmlErrorString); 
            throw he;

        } catch (ConnectTimeoutException cte){
        	method.releaseConnection();
            responseString.append(htmlErrorString);
            throw cte;
            
        }catch (IOException ioe){
            method.releaseConnection();
            responseString.append(htmlErrorString);
            throw ioe;
            
        } finally {
            method.releaseConnection();
        }
        
        return responseString.toString();
    }

		private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return new String(sb.toString().getBytes("ISO-8859-1"),"UTF-8");
		    
		  }
		public static String deAccent(String str) {
		    String nfdNormalizedString = str; 
		    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		    return pattern.matcher(nfdNormalizedString).replaceAll("");
		}
	/*    */ }

