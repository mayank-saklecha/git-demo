import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Servlet to expose a JSON output of all xml pages in a specific directory URI:
 * /bin/dallastnt/videoselector.json
 * <p>
 * @author  Mayank Saklecha
 * @version 1.0		25 Apr 2013
 */

public class XMLToJSONServlet {

    private static final Logger log = LoggerFactory.getLogger(XMLToJSONServlet.class);

    private PrintWriter out;

    //Change path as per configuration
	private static String pathtoXMLFiles = "C:\\test\\";

	//URL to append to each video path value
	private static String appendURL = "http://www.google.com";
	/**
     * {@inheritDoc}
	 * Set our response type to JSON, try using our FullSiteJSONService object to write out our JSON response, and log an error on any Exception.
     */
    public static  void main(String args[]) throws Exception {
        //response.setContentType("application/json");
		//out = response.getWriter();
		try {
			List<String> xmlValue = readXMLValue(pathtoXMLFiles);
	        System.out.println(getXMLFileAsJSON(xmlValue).toString());
		} catch (Exception e) {
			log.error("Exception sending JSON response", e);
		}

        //out.close();
    }

    private static List<String> readXMLValue(String pathToXML) throws ParserConfigurationException,IOException,SAXException
    {
    	List<String> xmlValues = new ArrayList<String>();
    	File dir = new File(pathToXML);
    	Document doc=null;
    	String videoName = null;
		if(dir!=null && dir.isDirectory()) //check if mentioned path is a directory
    	  {
	    	for (File child : dir.listFiles()) {
	    		InputStream is  = null;
				try
				{
					is = new FileInputStream(child);

				} catch (FileNotFoundException e) {
					System.out.println("Exception in creating the stream---> "+e.getMessage());
				}
				doc=readXml(is);
				if(doc!=null)
				{
					doc.getDocumentElement().normalize();
					NodeList nodeLst = doc.getElementsByTagName("METADATA");
					for (int temp = 0; temp < nodeLst.getLength(); temp++) {
						 
						Node nNode = nodeLst.item(temp);
				 
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				 
							Element eElement = (Element) nNode;
				 
							xmlValues.add(eElement.getElementsByTagName("MBREPOSITORYNAME").item(0).getTextContent());
							
						}
					}
				}
	    	  }
    	  }

    	return xmlValues;
    }
    /**
	 * Read XML as DOM.
	 */
	private  static Document readXml(InputStream is) throws SAXException, IOException,
	ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(false);
		dbf.setIgnoringComments(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setNamespaceAware(true);
		// dbf.setCoalescing(true);
		// dbf.setExpandEntityReferences(true);

		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();

		// db.setErrorHandler( new MyErrorHandler());

		return db.parse(is);
	}
    private static StringBuffer getXMLFileAsJSON (List<String> xmlValue)
		{
		StringBuffer jsonStrBuffer = new StringBuffer();
		jsonStrBuffer.append("[");
		int size = xmlValue.size();
			for(int i=0;i<size;i++) {
				String videoPath = xmlValue.get(i);
				String str = "{'value':'" + appendURL + videoPath+ "','name':'"+videoPath+"'}";
				if(i!=size-1)
				{
					str = str + ",";
				}
				jsonStrBuffer.append(str);
			}

		jsonStrBuffer.append("]");
		return jsonStrBuffer;
	}


}