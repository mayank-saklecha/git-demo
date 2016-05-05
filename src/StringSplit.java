
public class StringSplit {
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
		String node="/content/www/en_US/jcr:content/header";
		String headerNode = node.replace("/jcr:content/header", "");
		System.out.println(headerNode);
	}
	static void test(StringBuffer help)
	{
		help.append("test");
	}
}
