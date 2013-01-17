public class Driver
{
	public static void main(String args[])
	{
		LinkedList list = new LinkedList();
		
		list.addFront(5);
		list.addFront(7);
		list.addBack(3);
		list.addFront(1);
		list.addBack(6);
		
		System.out.println(list);
	}
}