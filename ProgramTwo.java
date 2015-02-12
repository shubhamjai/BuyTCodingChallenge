package Temp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProgramTwo
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		List<Integer> list = new ArrayList<Integer>();
		String str = in.nextLine();
		for(String str1: str.split(" "))
		{
			list.add(Integer.valueOf(str1.trim()));
		}
		int size = list.size();
		int lastMax =list.get(size-1);
		int sum = 0;
		for(int i= size-2;i>=0;i--)
		{
			if(lastMax < list.get(i))
			{
				lastMax  = list.get(i);
			}
			else
			{
				sum += lastMax - list.get(i);
			}
		}
		System.out.println(sum);   // final output
	}
}
