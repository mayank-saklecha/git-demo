import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipFile {
	public static void main(String args[]) throws Exception
	{
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream( bout );
        //ZipOutputStream zos = new ZipOutputStream( new FileOutputStream(new File("c:\\test\\test.zip")) );
        File file = new File("C:\\test\\fares.json");
		Scanner s = new Scanner(file);
		StringBuffer buf = new StringBuffer();
		while (s.hasNext()) {
		    buf.append(s.nextLine());
		}
		ZipEntry zEntry = new ZipEntry("data.json");
        zos.putNextEntry(zEntry);
        zos.write(buf.toString().getBytes());
        zos.closeEntry();
        zos.flush();
        zos.close();
        bout.close();
	}
}
