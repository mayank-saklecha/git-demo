


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class BaynoteTest {
                public static String BAYNOTE_URL_TERMINATOR = "v=1";
                public static final String SUCCESS_RELATED_LNK="successRelatedLnk";
                public static final String BAYNOTE_LNK="successRelatedLnk";
                public static final String RESULTS_PER_PAGE="resultsPerPage";
                public static final String GUIDE="guide";
                public static final String URLCONST="url";
                private String gdval;
                private String resval;
                private String requestURL;
                String baynoteRestXml = "";
                
                public String getRequestURL() {
                                return requestURL;
                }


                public void setRequestURL(String requestURL) {
                                this.requestURL = requestURL;
                }



                
                public String getGdval() {
                                return gdval;
                }


                public void setGdval(String gdval) {
                                this.gdval = gdval;
                }


                public String getResval() {
                                return resval;
                }


                public void setResval(String resval) {
                                this.resval = resval;
                }

                
                
                
                protected void setBaynoteRestXml(String str) {
                                this.baynoteRestXml = str;
                }


                public String getBaynoteRestXml() {
                                return baynoteRestXml;
                }


                public String getTemp() {
                                getBaynoteResultsXml();
                                return "temp";
                }

                public String getBaynoteResultsXml() {
                                StringBuilder sb = new StringBuilder();
                                InputStream is = getBaynoteResults();
                                if (is == null) 
                                                return "";
                                // Buffer the result into a string
                                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                                String line;

                                try {
                                                while ((line = rd.readLine()) != null) {
                                                                sb.append(line);
                                                }
                                } catch (IOException e) {
                                    e.printStackTrace() ;           
                                	//log.error("Error encountered while reading the Baynote results", e);
                                } finally {
                                                try { 
                                                                rd.close(); 
                                                                is.close();
                                                } catch (Exception e) { }
                                }
                                setBaynoteRestXml(sb.toString());
                                return SUCCESS_RELATED_LNK;
                }
                
                
                public InputStream getBaynoteResults() {                            

                                InputStream is = null;
                                String baynoteUrl= "";

                                try {
                                                baynoteUrl = "http://delta-www.baynote.net/baynote/guiderest?cn=delta&cc=www&outputFormat=JSON&guide=SubCatL1&url=http://www.delta.com/content/www/en_US/traveling-with-us/in-flight-services/business-elite.html&resultsPerPage=5&v=1" ;
                                                //String attrListVal="*";
                                                
                                                String guide=getGdval();
                                                String resultsPerPage= getResval();
                                                String reqURL =getRequestURL();
                                                if(guide==null || guide.equals("")){
                                                                guide = "";
                                                }
                                                if(resultsPerPage==null || resultsPerPage.equals("")){
                                                                resultsPerPage="10";
                                                }
                                                if(reqURL==null || reqURL.equals("")){
                                                                reqURL= "";
                                                }
                                                
         
                                                
                                                
                                                URL url = new URL(baynoteUrl);
                                                
                                                
                                                
                                                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                                                

                                                con.setConnectTimeout(1500);
                                                // long timeout
                                                // but not infinite
                                                con.setReadTimeout(1500);

                                                is = con.getInputStream();// This fires the request.
                                                
                                                
                                                
                                                //con.disconnect();

                                } catch (Exception e) {
                                                e.printStackTrace();
                                                is = null;
                                } 
                                return is;

                }
                public static void main(String args[])
                {
                	BaynoteTest test = new BaynoteTest();
                	test.getBaynoteResultsXml();
                	System.out.println(test.getBaynoteRestXml());
                }

}

