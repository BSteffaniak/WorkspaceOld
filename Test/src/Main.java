import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Main
{
	private static int num1, num2;
	
	private int num3, num4;
	
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		// #1
		num1 = 0;
		num2 = 34;
		
		System.out.println("Static before: " + num1 + ", " + num2);
		
		swapStatic();
		
		System.out.println("Static after: " + num1 + ", " + num2);
		
		// #2
		Point p  = new Point(1, 1);
		Point p2 = new Point(4, 2);
		
		Reference pp = new Reference(p);
		Reference pp2 = new Reference(p2);
		
		System.out.println("Reference before: " + (Point)pp.getObj() + ", " + (Point)pp2.getObj());
		
		swap(pp, pp2);
		
		System.out.println("Reference after: " + (Point)pp.getObj() + ", " + (Point)pp2.getObj());
		
		// #3
		num3 = 8;
		num4 = 12;
		
		System.out.println("Local method before: " + num3 + ", " + num4);
		
		swapLocals();
		
		System.out.println("Local method after: " + num3 + ", " + num4);
		
		// #4
		int array[] = { 1, 10 };
		
		System.out.println("Primitive array before: " + array[0] + ", " + array[1]);
		
		swapArray(array, 0, 1);

		System.out.println("Primitive array after: " + array[0] + ", " + array[1]);
		
		// #5
		ArrayList list = new ArrayList();
		
		list.add(4);
		list.add(10);
		
		System.out.println("List before: " + list.get(0) + ", " + list.get(1));
		
		swapList(list, 0, 1);
		
		System.out.println("List before: " + list.get(0) + ", " + list.get(1));
	}
	
	public void swapList(List a, int index1, int index2)
	{
		Object temp = a.get(index1);
		a.set(index1, a.get(index2));
		a.set(index2, temp);
	}
	
	public void swapArray(int a[], int index1, int index2)
	{
		int temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}
	
	private void swapStatic()
	{
		int temp = num1;
		num1 = num2;
		num2 = temp;
	}
	
	public void swapLocals()
	{
		int num3 = this.num3;
		int num4 = this.num4;
		
		Reference ref1 = new Reference(num3);
		Reference ref2 = new Reference(num4);
		
		swap(ref1, ref2);
		
		this.num3 = (Integer)ref1.getObj();
		this.num4 = (Integer)ref2.getObj();
	}
	
	private <T> void swap(Reference<T> a, Reference<T> b) {  
	    T tmp = a.getObj();  
	    a.setObj(b.getObj());  
	    b.setObj(tmp);  
	}  
}