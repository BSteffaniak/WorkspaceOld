package net.foxycorndog.arrowide.language;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.dialog.FileBrowseDialog;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.assembly.AssemblyLanguage;
import net.foxycorndog.arrowide.language.cpp.CppLanguage;
import net.foxycorndog.arrowide.language.foxy.FoxyLanguage;
import net.foxycorndog.arrowide.language.glsl.GLSLLanguage;
import net.foxycorndog.arrowide.language.java.JavaLanguage;
import net.foxycorndog.arrowide.language.php.PHPLanguage;

public class Language
{
	public  static final int JAVA = FileUtils.JAVA, GLSL = FileUtils.GLSL, ASSEMBLY = FileUtils.ASSEMBLY, FOXY = FileUtils.FOXY, CPP = FileUtils.CPP, C = FileUtils.C, PHP = FileUtils.PHP;
	
	private static ArrayList<CompilerListener>				listeners;
	
	private static HashMap<Integer, CommentProperties>		commentProperties;
	private static HashMap<Integer, MethodProperties>		methodProperties;
	private static HashMap<Integer, IdentifierProperties>	identifierProperties;
	
	public static void init()
	{
		listeners = new ArrayList<CompilerListener>();
		
		commentProperties    = new HashMap<Integer, CommentProperties>();
		methodProperties     = new HashMap<Integer, MethodProperties>();
		identifierProperties = new HashMap<Integer, IdentifierProperties>();
		
		Keyword.addLanguage(JAVA);
		Keyword.addLanguage(GLSL);
		Keyword.addLanguage(ASSEMBLY);
		Keyword.addLanguage(FOXY);
		Keyword.addLanguage(CPP);
		Keyword.addLanguage(PHP);
		
		JavaLanguage.init();
		GLSLLanguage.init();
		AssemblyLanguage.init();
		FoxyLanguage.init();
		CppLanguage.init();
		PHPLanguage.init();
		
		commentProperties.put(JAVA, JavaLanguage.COMMENT_PROPERTIES);
		commentProperties.put(GLSL, GLSLLanguage.COMMENT_PROPERTIES);
		commentProperties.put(ASSEMBLY, AssemblyLanguage.COMMENT_PROPERTIES);
		commentProperties.put(FOXY, FoxyLanguage.COMMENT_PROPERTIES);
		commentProperties.put(CPP, CppLanguage.COMMENT_PROPERTIES);
		commentProperties.put(PHP, PHPLanguage.COMMENT_PROPERTIES);
		
		methodProperties.put(JAVA, JavaLanguage.METHOD_PROPERTIES);
		methodProperties.put(GLSL, GLSLLanguage.METHOD_PROPERTIES);
		methodProperties.put(ASSEMBLY, AssemblyLanguage.METHOD_PROPERTIES);
		methodProperties.put(FOXY, FoxyLanguage.METHOD_PROPERTIES);
		methodProperties.put(CPP, CppLanguage.METHOD_PROPERTIES);
		methodProperties.put(PHP, PHPLanguage.METHOD_PROPERTIES);
		
		identifierProperties.put(JAVA, JavaLanguage.IDENTIFIER_PROPERTIES);
		identifierProperties.put(GLSL, GLSLLanguage.IDENTIFIER_PROPERTIES);
		identifierProperties.put(ASSEMBLY, AssemblyLanguage.IDENTIFIER_PROPERTIES);
		identifierProperties.put(FOXY, FoxyLanguage.IDENTIFIER_PROPERTIES);
		identifierProperties.put(CPP, CppLanguage.IDENTIFIER_PROPERTIES);
		identifierProperties.put(PHP, PHPLanguage.IDENTIFIER_PROPERTIES);
	}
	
	public static int getLanguage(String name)
	{
		return FileUtils.getLanguage(name);
	}
	
	public static void run(int language, String fileLocation, ConsoleStream stream)
	{
		fileLocation = FileUtils.removeEndingSlashes(fileLocation.replace('\\', '/'));
		
		if (language == JAVA)
		{
			JavaLanguage.run(fileLocation, stream);
		}
		else if (language == GLSL)
		{
			
		}
		else if (language == ASSEMBLY)
		{
			AssemblyLanguage.run(fileLocation, stream);
		}
		else if (language == CPP)
		{
			CppLanguage.run(fileLocation, stream);
		}
	}
	
	public static boolean canCompile(int fileType)
	{
		return fileType == JAVA || fileType == GLSL || fileType == ASSEMBLY || fileType == CPP;
	}
	
	public static CommentProperties getCommentProperties(int language)
	{
		CommentProperties properties = null;
		
		if (commentProperties.containsKey(language))
		{
			properties = commentProperties.get(language);
		}
		
		return properties;
	}
	
	public static MethodProperties getMethodProperties(int language)
	{
		MethodProperties properties = null;
		
		if (methodProperties.containsKey(language))
		{
			properties = methodProperties.get(language);
		}
		
		return properties;
	}
	
	public static IdentifierProperties getIdentifierProperties(int language)
	{
		IdentifierProperties properties = null;
		
		if (identifierProperties.containsKey(language))
		{
			properties = identifierProperties.get(language);
		}
		
		return properties;
	}
	
	public static void compile(String fileLocation, String code, String outputLocation, ConsoleStream stream)
	{
		if (fileLocation != null)
		{
			int language = FileUtils.getLanguage(fileLocation);
			
			if (language == Language.CPP)
			{
				if (!CONFIG_DATA.containsKey("g++.location") || !new File(CONFIG_DATA.get("g++.location")).exists())
				{
					FileBrowseDialog gppSearch = new FileBrowseDialog("Specify your c++ compiler. (EX: C:\\MinGW\\bin)", "Location:", FileBrowseDialog.EITHER);
					
					String gppLoc = gppSearch.open();
					
					if (gppLoc != null)
					{
						ArrowIDE.setConfigDataValue("g++.location", gppLoc);
					}
					else
					{
						stream.println("You must specify a valid c++ compiler to compile this program.");
						
						return;
					}
				}
			}
			
			if (language == Language.JAVA)
			{
				JavaLanguage.compile(fileLocation, code, outputLocation, stream, listeners);
			}
			else if (language == Language.GLSL)
			{
				GLSLLanguage.loadVertexShader(fileLocation, code, stream, listeners);
			}
			else if (language == Language.ASSEMBLY)
			{
				AssemblyLanguage.compile(fileLocation, outputLocation, stream, listeners);
			}
			else if (language == Language.CPP)
			{
				CppLanguage.compile(fileLocation, FileUtils.getParentFolder(fileLocation), stream, listeners);
			}
		}
	}
	
	public static void addCompilerListener(CompilerListener listener)
	{
		listeners.add(listener);
	}
}