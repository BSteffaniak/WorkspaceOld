import javax.swing.*;

public class Test
{
	public static void main(String args[])
	{
		new Thread()
		{
			int times = 0;
			
			int d = 4;
			
			while (d < 4)
			{
				d++;
			}
			
			public void run()
			{
				while (true)
				{
					try
					{
						Thread.sleep(1000);
						
						System.out.println(times++);
					}
					catch (InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}
		}.start();
		
		JFrame f = new JFrame("ASDF2");
		f.setSize(500, 500);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
		System.out.println("outside thread");
		
		newfile f2 = new newfile();
		
		f2.saySomething();
	}
}