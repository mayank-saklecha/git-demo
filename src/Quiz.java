import java.util.ArrayList;
import java.util.List;


public class Quiz {

	public static void main(String args[])
	{
		List<String> lst = new ArrayList<String>();
		for(int i=1;i<=200;i++)
		{
			lst.add(""+i);
		}
		deleteNext(lst);
	}
	private static void deleteNext(List<String> lst)
	{
		if(lst.size()==1)
		{
			System.out.println(lst.get(0));
		}
		else
		{
			for(int i=0;i<lst.size();i++)
			{
				if(i==lst.size()-1)
				{
					lst.remove(0);
				}
				else
				{
					lst.remove(i+1);
				}
			}
			deleteNext(lst);
		}
	}
}
