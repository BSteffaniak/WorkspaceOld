public class Test2
{
	public static void main(String args[])
	{
		ListNode p1, p2;
		p1 = new ListNode(100);
		p2 = p1;
		
		for (int k = 101; k <= 105; k++)
		{
			p1 = new ListNode(new Integer(k));
			p1.setNext(p2);
			p2 = p1;
		}
		
		while (p2 != null)
		{
			System.out.println(p2.getData() + " ");
			p2 = p2.getNext();
		}
	}
}