import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.InetAddress;
import java.util.Date;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HeaderElement;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.json.JSONTokener;


public class Destinations {
	static String MSP_API_URL = "http://deltaskymag.delta.com/DeltaEmbark/";
	public static void main(String args[]) throws Exception
	{
		
		JSONArray destinationArray = new JSONArray();
		
		JSONArray arr = getAllDestinations();
		for(int i=0;i<1;i++)
		{
			JSONObject obj = arr.getJSONObject(i);
			//String mspId = obj.getString("id");
			String mspId = "1235";
			Destination dest = new Destination();
			dest.setMspId(mspId);
			JSONObject cityObject = getCityDetails(mspId);
			dest.setCityDetails(cityObject.toString());
			JSONArray airportArray = cityObject.getJSONArray("airports");
			JSONArray finalAirportArray = new JSONArray();
			for(int j=0;j<airportArray.length();j++)
			{
				JSONObject airportObject = airportArray.getJSONObject(j);
				finalAirportArray.put(getAirportDetails(airportObject.getString("id")));
			}
			dest.setAirportDetails(finalAirportArray.toString());
			JSONObject destinationObject = getDestinationDetails(mspId);
			obj.put("details", destinationObject.toString());
			dest.setDestinationDetails(obj.toString());
			System.out.println(mspId);
			JSONArray locationArray = destinationObject.getJSONArray("categories");
			JSONObject finalLocationObject = new JSONObject();
			for(int k=0;k<locationArray.length();k++)
			{
				JSONObject locationObject = locationArray.getJSONObject(k);
				JSONArray cardArray = locationObject.getJSONArray("cards");
				JSONObject finalCardObject = new JSONObject();
				for(int m=0;m<cardArray.length();m++)
				{
					JSONObject cardObject = cardArray.getJSONObject(m);
					JSONObject newLocationDetails = getLocationDetails(cardObject.getString("id"));
					finalCardObject.put(cardObject.getString("id"), new JSONArray().put(newLocationDetails));
					
				}
				finalLocationObject.put(locationObject.getString("name"), finalCardObject);
				System.out.println(locationObject.getString("name").toCharArray());
			}
			dest.setLocationDetails(finalLocationObject.toString());
			System.out.println(dest.getLocationDetails());
			JSONObject locationObject = new JSONObject(dest.getLocationDetails());
			JSONObject destLocationObject = new JSONObject(dest.getDestinationDetails()).getJSONObject("location");
			Iterator<String> keys = locationObject.keys();
			JSONArray categoryArray = new JSONArray();
			
			while(keys.hasNext())
			{
				String key = keys.next();
				//System.out.println(key);
				JSONObject cardObject = locationObject.getJSONObject(key);
				Iterator<String> cardIterator = cardObject.keys();
				JSONArray cardArray = new JSONArray();
				while(cardIterator.hasNext())
				{
					String cardKey = cardIterator.next();
					JSONArray cardDetails = cardObject.getJSONArray(cardKey);
					JSONObject cardDetail = cardDetails.getJSONObject(0);
					String imageSource = cardDetail.getJSONObject("image").getString("src");
					//System.out.println(imageSource);
					String[] image = imageSource.split("/");
					System.out.println(image[image.length-2]);
					JSONObject cardLocationObject = new JSONObject();
					if(cardDetail.getJSONObject("location").getString("lat").length()==0 || cardDetail.getJSONObject("location").getString("long").length()==0 )
					{
						cardLocationObject = destLocationObject;
					}
					else
					{
						cardLocationObject = cardDetail.getJSONObject("location");
					}
					JSONObject modifiedCardObject = new JSONObject();
					modifiedCardObject.put("id", cardDetail.getString("id"));
					modifiedCardObject.put("title", cardDetail.get("title"));
					modifiedCardObject.put("description", cardDetail.getString("description"));
					modifiedCardObject.put("address", cardDetail.getJSONObject("address"));
					modifiedCardObject.put("location", cardLocationObject);
					modifiedCardObject.put("category", cardDetail.getJSONObject("category"));
					modifiedCardObject.put("hours", (cardDetail.has("hours")?cardDetail.getString("hours"):""));
					modifiedCardObject.put("price", (cardDetail.has("price")?cardDetail.getString("price"):""));
					modifiedCardObject.put("type", (cardDetail.has("type")?cardDetail.getString("type"):""));
					modifiedCardObject.put("openTableUrl", (cardDetail.has("openTableUrl")?cardDetail.getString("openTableUrl"):""));
					modifiedCardObject.put("paymentOptions", (cardDetail.has("paymentOptions")?cardDetail.getString("paymentOptions"):""));
					cardArray.put(modifiedCardObject);
					
				}
				JSONObject categoryObject = new JSONObject();
				categoryObject.put("name", key);
				categoryObject.put("cards", cardArray);
				categoryArray.put(categoryObject);
			}
			//System.out.println(categoryArray);
		}
		
	}
	private static JSONObject getCityDetails(String mspId) throws Exception
	{
		String cityDetail = getHttpContent(MSP_API_URL+"CityDetail.ashx?id="+mspId);
		JSONObject obj = new JSONObject(new JSONTokener(cityDetail));
		return obj;
	}
	private static JSONObject getAirportDetails(String mspId) throws Exception
	{
		String airportDetail = getHttpContent(MSP_API_URL+"Airport.ashx?id="+mspId);
		JSONObject obj = new JSONObject(new JSONTokener(airportDetail));
		return obj;
	}
	private static JSONObject getLocationDetails(String mspId) throws Exception
	{
		String locationDetail = getHttpContent(MSP_API_URL+"Shopping.ashx?id="+mspId);
		JSONObject obj = new JSONObject(new JSONTokener(locationDetail));
		return obj;
	}
	private static JSONObject getDestinationDetails(String mspId) throws Exception
	{
		String destinationDetail = getHttpContent(MSP_API_URL+"Destination.ashx?id="+mspId);
		JSONObject obj = new JSONObject(new JSONTokener(destinationDetail));
		return obj;
	}
	private static JSONArray getAllDestinations() throws Exception
	{
		JSONArray arr = new JSONArray();
		
		String allDestinations = getHttpContent(MSP_API_URL+"Globe.ashx");
		JSONObject obj = new JSONObject(new JSONTokener(allDestinations));
		arr = obj.getJSONArray("destinations");
		return arr;
	}
private static String getHttpContent(String url) throws Exception{
        
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
						//InputStream responseBodyAsStream = method.getResponseBodyAsStream();
						//BufferedImage image = ImageIO.read(responseBodyAsStream);
						//BufferedOutputStream outStream = new BufferedOutputStream(new ByteArrayOutputStream());
						//ImageIO.write(image, "jpg",outStream);
						//System.out.println(new String(method.getResponseBody(),"utf-8"));
						//responseBody = IOUtils.toString(new InputStreamReader(responseBodyAsStream,method.getResponseCharSet()));
						InputStream responseBodyAsStream = method.getResponseBodyAsStream();
						//fout.write(method.getResponseBody());
						//responseBody = IOUtils.toString(responseBodyAsStream);
						char[] ch =	IOUtils.toCharArray(responseBodyAsStream, method.getResponseCharSet());
						/*StringBuilder build = new StringBuilder();
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
						responseBody = build.toString();*/
						responseBody = String.valueOf(ch);
						//responseBody = IOUtils.toString(responseBodyAsStream);
						// Close the InputStream
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
}