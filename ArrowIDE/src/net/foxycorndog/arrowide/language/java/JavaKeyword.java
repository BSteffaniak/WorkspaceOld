package net.foxycorndog.arrowide.language.java;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.language.Keyword;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.arrowide.language.Language.JAVA;

public class JavaKeyword
{
	public static final Keyword
			INT                          = new Keyword(JAVA, "int"),
			FLOAT                        = new Keyword(JAVA, "float"),
			VOID                         = new Keyword(JAVA, "void"),
			INSTANCEOF                   = new Keyword(JAVA, "instanceof"),
			PUBLIC                       = new Keyword(JAVA, "public"),
			PRIVATE                      = new Keyword(JAVA, "private"),
			PROTECTED                    = new Keyword(JAVA, "protected"),
			TRANSIENT                    = new Keyword(JAVA, "transient"),
			VOLATILE                     = new Keyword(JAVA, "volatile"),
			STATIC                       = new Keyword(JAVA, "static"),
			IMPORT                       = new Keyword(JAVA, "import"),
			PACKAGE                      = new Keyword(JAVA, "package"),
			CLASS                        = new Keyword(JAVA, "class"),
			IMPLEMENTS                   = new Keyword(JAVA, "implements"),
			EXTENDS                      = new Keyword(JAVA, "extends");
	
	static
	{
		INT.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		FLOAT.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		VOID.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		INSTANCEOF.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		PUBLIC.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		PRIVATE.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		PROTECTED.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		TRANSIENT.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		VOLATILE.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		STATIC.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		IMPORT.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		PACKAGE.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		CLASS.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		IMPLEMENTS.setColor(new Color(ArrowIDE.display, 150, 0, 0));
		EXTENDS.setColor(new Color(ArrowIDE.display, 150, 0, 0));
	}
	
	public static void init()
	{
		
	}
}