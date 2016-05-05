

public class StringReplace {
	public static void main(String args[])
	{
		/*String str = "destinations.1658";
		//String copyright = finalJsonObject.getString("copyright")appleID.replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
		System.out.println(str.split("\\.")[0]);
		String str1="dfdsfsdf\"xvxcv\"";
		System.out.println(str1.replaceAll("\"", ""));
		String abc = "http://www.delta.com/asdsadas/sdas/asdasd/";
		System.out.println(abc.startsWith("http://"));
		String channel = "airports-dfdsfsdafsdfas";
		System.out.println(channel.split("-")[0]);
		
		StringBuffer help = new StringBuffer();
		test(help);
		System.out.println(help);
		String strTest = "<img src=\"\" alt=\"Open in new window\" class=\"openInNewWindow\"/>";
		System.out.println(strTest);
		String strMatch = "exception";
		System.out.println(strMatch.matches(".*[Ee]xception(.*)"));
		String price = "&yen;42,000";
		System.out.println(price.replaceAll("(.*?)([0-9,]+)", "$1" ));*/
		//String node="/content/www/en_US/jcr:content/header";
		//String headerNode = node.replace("/jcr:content/header", "").concat("/home.html");
		//System.out.println(headerNode);
		String str = "<div id=\"test\"> this is an interesting read®</div>";
		System.out.println(escapeHTML(str));
	}
	public static String escapeHTML(String s) {
	    StringBuilder out = new StringBuilder(Math.max(16, s.length()));
	    for (int i = 0; i < s.length(); i++) {
	        char c = s.charAt(i);
	        if (c > 127 || c == '&') {
	            out.append("&#");
	            out.append((int) c);
	            out.append(';');
	        } else {
	            out.append(c);
	        }
	    }
	    return out.toString();
	}
}

