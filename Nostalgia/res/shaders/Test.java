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
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println("Worked inside");
			}
		}.start();

		for (int i = 0; i < 10; i ++)
		{
			System.out.println("worked outside");
		}
	}
}