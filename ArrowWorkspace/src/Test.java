import javax.swing.*;

public class Test
{
	public static void main(String args[])
	{
		new Thread()
		{
			public void run()
			{
				try
				{
					Thread.sleep(1000);
					
					System.out.println("inside thread");
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
		
		JFrame f = new JFrame("ASDF");
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		System.out.println("outside thread");
		
		newfile f2 = new newfile();
		
		f2.saySomething();
	}
}