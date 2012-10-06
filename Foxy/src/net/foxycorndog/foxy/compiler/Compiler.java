package net.foxycorndog.foxy.compiler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;



public class Compiler {
    final Logger logger = Logger.getLogger(Compiler.class.getName()) ;
 
    /**Java source code to be compiled dynamically*/
    static String sourceCode =
    		"package net.foxycorndog.foxy.compiler;\n" +
    		"class DynamicCompilationHelloWorld\n" +
    		"{\n" +
    			"public static void main (String args[])\n" +
    			"{\n" +
                	"System.out.println (\"Hello, dynamic compilation world!\");\n" +
                "}\n" +
            "}\n" ;
 
    /**
     * Does the required object initialization and compilation.
     */
    public void doCompilation (){
        /*Creating dynamic java source code file object*/
        SimpleJavaFileObject fileObject = new DynamicJavaSourceCodeObject ("net.foxycorndog.foxy.compiler.DynamicCompilationHelloWorld", sourceCode) ;
        //super(URI.create(“string:///” +name.replaceAll(“\\.”, “/”) + Kind.SOURCE.extension), Kind.SOURCE);
        
        JavaFileObject javaFileObjects[] = new JavaFileObject[]{fileObject} ;
 
        /*Instantiating the java compiler*/
        JavaCompiler compiler = new EclipseCompiler();
        
        /**
         * Retrieving the standard file manager from compiler object, which is used to provide
         * basic building block for customizing how a compiler reads and writes to files.
         *
         * The same file manager can be reopened for another compiler task.
         * Thus we reduce the overhead of scanning through file system and jar files each time
         */
//        StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
        
        StandardJavaFileManager sjfm = compiler.getStandardFileManager(null, Locale.getDefault(), null);
		SpecialClassLoader cl = new SpecialClassLoader();           
		SpecialJavaFileManager stdFileManager = new SpecialJavaFileManager(sjfm, cl); 
 
        /* Prepare a list of compilation units (java source code file objects) to input to compilation task*/
//        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);
 
        /*Prepare any compilation options to be used during compilation*/
        //In this example, we are asking the compiler to place the output files under bin folder.
        String[] compileOptions = new String[] { "-d", "bin" } ;
        Iterable<String> compilationOptionss = Arrays.asList(compileOptions);
 
        /*Create a diagnostic controller, which holds the compilation problems*/
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
 
        List compilationUnits = Arrays.asList(new MemorySource("net.foxycorndog.foxy.compiler.DynamicCompilationHelloWorld", sourceCode));
//        List compilationOptionss = Collections.emptyList();
//        DiagnosticListener diagnostics = null;
        Iterable classes = null;
        Writer out = new PrintWriter(System.err); 
        
        /*Create a compilation task from compiler by passing in the required input objects prepared above*/
        CompilationTask compilerTask = compiler.getTask(out, stdFileManager, diagnostics, compilationOptionss, classes, compilationUnits) ;
//        JavaCompiler.CompilationTask compile = javac.getTask(out, fileManager, dianosticListener, options, classes, compilationUnits);
        //Perform the compilation by calling the call method on compilerTask object.
        
        boolean status = compilerTask.call();

        if (!status)
        {
        	//If compilation error occurs
            /*Iterate through each compilation problem and print it*/
            for (Diagnostic diagnostic : diagnostics.getDiagnostics())
            {
                System.out.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic);
            }
        	System.out.println("Unsuccessful.");
        }
        else
        {
        	try
			{
				System.out.println("Successful! " + cl.findClass("net.foxycorndog.foxy.compiler.DynamicCompilationHelloWorld").getSimpleName());
			}
			catch (ClassNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        try {
            stdFileManager.close() ;//Close the file manager
        }
        catch (IOException e)
        {
        	System.out.println("Exception.");
        	
            e.printStackTrace();
        }
    }
 
    public static void main(String args[]){
        new Compiler ().doCompilation() ;
    }
 
}
 
/**
 * Creates a dynamic source code file object
 *
 * This is an example of how we can prepare a dynamic java source code for compilation.
 * This class reads the java code from a string and prepares a JavaFileObject
 *
 */
class DynamicJavaSourceCodeObject extends SimpleJavaFileObject{
    private String qualifiedName ;
    private String sourceCode ;
 
    /**
     * Converts the name to an URI, as that is the format expected by JavaFileObject
     *
     *
     * @param fully qualified name given to the class file
     * @param code the source code string
     */
    protected DynamicJavaSourceCodeObject(String name, String code) {
    	super(URI.create("string:///" +name.replaceAll(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.qualifiedName = name ;
        this.sourceCode = code ;
    }
 
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors)
            throws IOException {
        return sourceCode ;
    }
 
    public String getQualifiedName() {
        return qualifiedName;
    }
 
    public void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
 
    public String getSourceCode() {
        return sourceCode;
    }
 
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
}



//public class Compiler
//{
//	private final static String HALLO_WORLD_CLASS_NAME = "HalloWorldTest";   
//	private final static String HALLO_WORLD_SOURCE =
//		"public class "+HALLO_WORLD_CLASS_NAME+"{\n" +           
//		"    public static void main(String[] args) {\n" +           
//		"        System.out.println(\"Hallo worlddafasdfasdfasdf\");\n" +           
//		"    }\n" + 
//		"    public void test(String tset)\n" +
//		"    {\n" +
//		"        System.out.println(tset);\n" +
//		"    }\n" +
//		"}";
//	
//	public Compiler()
//	{
//		
//	}
//	
//	public static void compile(String fileName)
//	{
//		File javaHome = new File(System.getProperty("java.home")).getParentFile();
//		
//		String version = System.getProperty("java.version");
//		
//		if (javaHome.isDirectory())
//		{
//			File files[] = javaHome.listFiles();
//			
//			File jdk = null;
//			
//			for (File file : files)
//			{
//				if (file.getName().equals("jdk" + version))
//				{
//					jdk = file;
//				}
//			}
//			
//			if (jdk == null)
//			{
//				System.out.println("no jdk");
//			}
//			else
//			{
//				System.out.println("Has jdk");
//				
//				Class c = compileClass(HALLO_WORLD_SOURCE, HALLO_WORLD_CLASS_NAME);
//				
////				File f = new File("test.class");
////				
////				f.
//				
//				//System.out.println(c.);
//			}
//		}
//		else
//		{
//			System.out.println("An error has occurred: 10144853");
//		}
//	}
//	
//	private static Class compileClass(String halloWorldProgram, String className) {       
//		try{           
//			JavaCompiler javac = new EclipseCompiler();
//
//			StandardJavaFileManager sjfm = javac.getStandardFileManager(null, Locale.getDefault(), null);
//			SpecialClassLoader cl = new SpecialClassLoader();           
//			SpecialJavaFileManager fileManager = new SpecialJavaFileManager(sjfm, cl);           
////			List options = Collections.emptyList();
//			String[] compileOptions = new String[]{"-d", "bin"} ;
//	        Iterable<String> options = Arrays.asList(compileOptions);
//			
//			List compilationUnits = Arrays.asList(new MemorySource(className, halloWorldProgram));           
//			DiagnosticListener dianosticListener = null;           
//			Iterable classes = null;           
//			Writer out = new PrintWriter(System.err);           
//			JavaCompiler.CompilationTask compile = javac.getTask(out, fileManager, dianosticListener, options, classes, compilationUnits);
//			
//			boolean res = compile.call();
//			
//			if (res)
//			{
//				
//				
//				return cl.findClass(className);           
//			}       
//		} catch (Exception e){           
//			e.printStackTrace();       
//		}       
//		return null;   
//	}
//}

class MemorySource extends SimpleJavaFileObject {   
	private String src;   
	public MemorySource(String name, String src) {       
		super(URI.create("file:///" + name + ".java"), Kind.SOURCE);       
		this.src = src;   
	}   
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {       
		return src;   
	}   
	public OutputStream openOutputStream() {       
		throw new IllegalStateException();   
	}   
	public InputStream openInputStream() {       
		return new ByteArrayInputStream(src.getBytes());   
	}
}

class SpecialJavaFileManager extends ForwardingJavaFileManager {   
	private SpecialClassLoader xcl;   
	public SpecialJavaFileManager(StandardJavaFileManager sjfm, SpecialClassLoader xcl) {       
		super(sjfm);       
		this.xcl = xcl;   
	}   
	public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {       
		MemoryByteCode mbc = new MemoryByteCode(name);       
		xcl.addClass(name, mbc);       
		return mbc;   
	}

	public ClassLoader getClassLoader(Location location) {       
		return xcl;   
	}
}

class MemoryByteCode extends SimpleJavaFileObject {   
	private ByteArrayOutputStream baos;   
	public MemoryByteCode(String name) {       
		super(URI.create("byte:///" + name + ".class"), Kind.CLASS);   
	}   
	public CharSequence getCharContent(boolean ignoreEncodingErrors) {       
		throw new IllegalStateException();   
	}   
	public OutputStream openOutputStream() {       
		baos = new ByteArrayOutputStream();       
		return baos;   
	}   
	public InputStream openInputStream() {       
		throw new IllegalStateException();   
	}   
	public byte[] getBytes() {       
		return baos.toByteArray();   
	}
}

class SpecialClassLoader extends ClassLoader
{   
	private Map<String,MemoryByteCode> m = new HashMap<String, MemoryByteCode>();

	protected Class<?> findClass(String name) throws ClassNotFoundException
	{       
		MemoryByteCode mbc = m.get(name);       
		if (mbc == null){           
			mbc = m.get(name.replace(".","/"));           
			if (mbc == null){               
				return super.findClass(name);           
			}       
		}       
		return defineClass(name, mbc.getBytes(), 0, mbc.getBytes().length);   
	}

	public void addClass(String name, MemoryByteCode mbc)
{       
		m.put(name, mbc);   
	}
}