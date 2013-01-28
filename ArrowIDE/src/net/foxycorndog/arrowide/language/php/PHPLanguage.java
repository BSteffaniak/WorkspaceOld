package net.foxycorndog.arrowide.language.php;

import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.MethodProperties;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class PHPLanguage
{
	public  static final CommentProperties	COMMENT_PROPERTIES;
	public  static final MethodProperties	METHOD_PROPERTIES;
	
	public static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	static
	{
		COMMENT_PROPERTIES = new CommentProperties(new String[] { "#", "//" }, new String[] { "/*" }, new String[] { "*/" }, new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES  = new MethodProperties();
	}
	
	public static void init()
	{
		PHPKeyword.init();
	}
}