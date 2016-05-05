import java.security.MessageDigest;
import java.util.Formatter;


public class Sha1Test {
	public static void main(String args[]) throws Exception
	{
		byte[] a={'[','B','@','8','a','3','b','c','b'};
		byte[] a1={'[','B','@','8','a','3','b','c','c'};
		MessageDigest md= MessageDigest.getInstance("SHA1");
		System.out.println(byteToHex(md.digest(a)));
		System.out.println(byteToHex(md.digest(a1)));
	}
	private static String byteToHex(final byte[] hash)
	{
		System.out.println(new String(hash));
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	        System.out.println(b+"----"+formatter.toString());
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
}
