import java.util.Arrays;


public class MatrixTest {
	public static void main(String args[])
	{
		int matrix1[][] = new int[][]{{1,1,1},{2,2,2},{3,3,3}};
		int matrix2[][] = new int[][]{{4,4,4},{5,5,5},{6,6,6}};
		int matrix3[][] = new int[3][3];
		int sum=0;
		for (int i=0;i<3;i++)
		{
			sum = 0;
			for(int j=0;j<3;j++)
			{
				sum =0;
				for (int k=0;k<3;k++)
				{
					sum = sum + matrix1[i][k]*matrix2[k][j];
				}
				matrix3[i][j] = sum;
			}
			
		}
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				System.out.print(matrix3[i][j]+"\t");
			}
			System.out.print("\n");
		}
	}
}
