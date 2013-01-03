public class Asm
{
   public native String HelloDll(String s);

   static
   {
      System.loadLibrary("hjwdll");
   }

   public static void main(String[] args)
   {
	   Asm sm = new Asm();
      String returnMessage = sm.HelloDll("Hello, World of JNI");
      System.out.println(returnMessage);
   }
}