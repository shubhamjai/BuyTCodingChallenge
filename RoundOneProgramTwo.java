import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class RoundOneProgramTwo
{
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		int M = in.nextInt();
		int arr[] = new int[N];
		int i=0;
		while(i!=N)
		{
			String str = in.next();
			arr[i++]= Integer.parseInt(str, 2);
		}
		int max = 0;
		Integer curr=0;
		Set<String> set = null;
		for(i=0;i<N-1;i++)
		{
			for(int j=i+1;j<N;j++)
			{
				curr = (arr[i] | arr[j]);
				curr = Integer.bitCount(curr);
				if(curr>max)
				{
					max = curr;
					if(set!=null)
					{
						set.clear();   // clear memory
					}
					set = new HashSet<String>();
					set.add(i+":"+j);     // add group to the set
				}
				else if(curr == max)
				{
					if(null==set)
					{
						set = new HashSet<String>();
					}
					set.add(i+":"+j);
				}
					
			}
		}
		
		// output to console
		System.out.println(max);
		System.out.println(set.size());
	}

}
