import java.awt.Point;

public class Main
{
	
	
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		Point p  = new Point(5, 5);
		Point p2 = new Point(4, 1);
		
		swap(p, p2);
		
		System.out.println(p + ", " + p2);
	}
	
	private void swap(Point a, Point b)
	{
		Point temp = (Point) a.clone();
		a = (Point) b.clone();
		b = temp;
	}
}