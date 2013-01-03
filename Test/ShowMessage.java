class ShowMessage
{
        public native String HelloDll(String s);
	static
	{
		System.loadLibrary("hjwdll");
	}
	public static void main(String[] args)
	{
                ShowMessage sm = new ShowMessage();
                String returnmessage = sm.HelloDll("Hello, World of JNI");
                System.out.println(returnmessage);
	}
}
