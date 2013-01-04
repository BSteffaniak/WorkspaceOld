package net.foxycorndog.arrowide.language.java;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.language.CompilerListener;

//import org.apache.commons.jci.compilers.CompilationResult;
//import org.apache.commons.jci.compilers.JavaCompilerFactory;
//import org.apache.commons.jci.compilers.JavacJavaCompiler;
//import org.apache.commons.jci.compilers.JavacJavaCompilerSettings;
//import org.apache.commons.jci.readers.FileResourceReader;
//import org.apache.commons.jci.readers.MemoryResourceReader;
//import org.apache.commons.jci.stores.FileResourceStore;
//import org.apache.commons.jci.stores.MemoryResourceStore;

public class JavaCompiler
{
	public static void compile(String fileName, String code, String outputLocation, ConsoleStream stream, ArrayList<CompilerListener> compilerListeners)
	{
//		fileName = fileName == null ? "" : fileName;
//		
//		JavacJavaCompilerSettings settings = new JavacJavaCompilerSettings();
//		
//		org.apache.commons.jci.compilers.JavaCompiler compiler = new JavacJavaCompiler(settings);//new JavaCompilerFactory().createCompiler("javac");//new JavacJavaCompiler(settings);
//		
////		org.apache.commons.jci.compilers.JavaCompiler compiler = new JavaCompilerFactory().createCompiler("eclipse");
//
//		MemoryResourceReader mrr = new MemoryResourceReader();
//		mrr.add("Test", code.getBytes());
//
//		MemoryResourceStore mrs = new MemoryResourceStore();
//		
//		
//		CompilationResult result = compiler.compile(new String[] { fileName }, mrr, mrs);
//		
//		return result.getErrors().length + " ";
		
		fileName = fileName.substring(0, fileName.lastIndexOf('.'));
		
		/*Creating dynamic java source code file object*/
		
		SimpleJavaFileObject fileObject  = new DynamicJavaSourceCodeObject("src/" + fileName, code);
		
		JavaFileObject javaFileObjects[] = new JavaFileObject[] { fileObject };
 
		/*Instantiating the java compiler*/
		javax.tools.JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
 
		/**
		 * Retrieving the standard file manager from compiler object, which is used to provide
		 * basic building block for customizing how a compiler reads and writes to files.
		 *
		 * The same file manager can be reopened for another compiler task.
		 * Thus we reduce the overhead of scanning through file system and jar files each time
		 */
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
 
		/* Prepare a list of compilation units (java source code file objects) to input to compilation task*/
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);
 
		/*Prepare any compilation options to be used during compilation*/
		//In this example, we are asking the compiler to place the output files under bin folder.
		String[] compileOptions = new String[]{"-d", outputLocation} ;
		Iterable<String> compilationOptions = Arrays.asList(compileOptions);
 
		/*Create a diagnostic controller, which holds the compilation problems*/
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
 
		/*Create a compilation task from compiler by passing in the required input objects prepared above*/
		CompilationTask compilerTask = compiler.getTask(null, stdFileManager, diagnostics, compilationOptions, null, compilationUnits);
 
		//Perform the compilation by calling the call method on compilerTask object.
		boolean status = compilerTask.call();
		
		StringBuilder error = new StringBuilder();
 
		for (int i = compilerListeners.size() - 1; i >= 0; i--)
		{
			compilerListeners.get(i).compiled(status ? 0 : 1);
		}
		
		//If compilation error occurs
		if (!status)
		{
			HashSet<String> errors = new HashSet<String>();
			
			/*Iterate through each compilation problem and print it*/
			for (Diagnostic diagnostic : diagnostics.getDiagnostics())
			{
				String err = diagnostic.getMessage(Locale.getDefault());
				
				errors.add(String.format("Error on line %d: %s", diagnostic.getLineNumber(), err));
			}
			
			String strs[] = errors.toArray(new String[0]);
			
			for (String str : strs)
			{
				error.append(str);
			}
			
			stream.println(error.toString());
		}
		
		try
		{
			stdFileManager.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

/**
 * Creates a dynamic source code file object
 *
 * This is an example of how we can prepare a dynamic java source code for compilation.
 * This class reads the java code from a string and prepares a JavaFileObject
 *
 */
class DynamicJavaSourceCodeObject extends SimpleJavaFileObject
{
	private String qualifiedName;
	private String sourceCode;
 
	/**
	 * Converts the name to an URI, as that is the format expected by JavaFileObject
	 *
	 *
	 * @param fully qualified name given to the class file
	 * @param code the source code string
	 * @throws UnsupportedEncodingException 
	 */
	protected DynamicJavaSourceCodeObject(String name, String code)
	{
		super(URI.create("string:///" + name.replace(' ', '+').replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
		this.qualifiedName = name;
		this.sourceCode = code;
	}
 
	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException
	{
		return sourceCode ;
	}
 
	public String getQualifiedName()
	{
		return qualifiedName;
	}
 
	public void setQualifiedName(String qualifiedName)
	{
		this.qualifiedName = qualifiedName;
	}
 
	public String getSourceCode()
	{
		return sourceCode;
	}
 
	public void setSourceCode(String sourceCode)
	{
		this.sourceCode = sourceCode;
	}
}