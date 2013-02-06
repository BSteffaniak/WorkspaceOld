package net.foxycorndog.arrowide.language.python;

import java.io.IOException;
import java.io.PrintStream;

import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.MethodProperties;
import net.foxycorndog.arrowide.language.python.PythonKeyword;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

public class PythonLanguage
{
	public  static final CommentProperties		COMMENT_PROPERTIES;
	public  static final MethodProperties		METHOD_PROPERTIES;
	public  static final IdentifierProperties	IDENTIFIER_PROPERTIES;
	
	public static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	static
	{
		COMMENT_PROPERTIES    = new CommentProperties(new String[] { "#" }, new String[] {  }, new String[] {  }, new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES     = new MethodProperties();
		IDENTIFIER_PROPERTIES = new IdentifierProperties(new char[][] { { 0 } }, new char[][] { { '=' } }, new String[][] {  }, new String[][] {  }, new String[][] { {  }, { "in" } }, new Color(Display.getDefault(), 4, 150, 120));
	}
	
	public static void init()
	{
		PythonKeyword.init();
	}
	
	public static void run(String fileLocation, PrintStream stream)
	{
		Command runner = new Command(Display.getDefault(), new String[] { CONFIG_DATA.get("python.location") + "/python", fileLocation }, stream, FileUtils.getParentFolder(fileLocation));
		
		try
		{
			runner.execute();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}