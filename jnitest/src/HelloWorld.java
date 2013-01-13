public class HelloWorld
{
	private native int getArch();
	
	public static void main(String args[])
	{
		System.out.println("before");
		
		System.out.println(new HelloWorld().getArch());
	}
	
	static
	{
		System.loadLibrary("HelloWorld");
	}
}