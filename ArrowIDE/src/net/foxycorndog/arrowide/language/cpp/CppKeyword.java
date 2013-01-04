package net.foxycorndog.arrowide.language.cpp;

import static net.foxycorndog.arrowide.language.Language.CPP;
import static net.foxycorndog.arrowide.language.Language.GLSL;

import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.glsl.GLSLLanguage;
import net.foxycorndog.arrowide.language.java.JavaLanguage;

public class CppKeyword
{
	public static final Keyword
			ABSTRACT     = new Keyword(CPP, "abstract"),
			ASSERT       = new Keyword(CPP, "assert"),
			BOOLEAN      = new Keyword(CPP, "boolean"),
			BREAK        = new Keyword(CPP, "break"),
			BYTE         = new Keyword(CPP, "byte"),
			CASE         = new Keyword(CPP, "case"),
			CATCH        = new Keyword(CPP, "catch"),
			CHAR         = new Keyword(CPP, "char"),
			CLASS        = new Keyword(CPP, "class"),
			CONST        = new Keyword(CPP, "const"),
			CONTINUE     = new Keyword(CPP, "continue"),
			DEFAULT      = new Keyword(CPP, "default"),
			DO           = new Keyword(CPP, "do"),
			DOUBLE       = new Keyword(CPP, "double"),
			ELSE         = new Keyword(CPP, "else"),
			ENUM         = new Keyword(CPP, "enum"),
			EXTENDS      = new Keyword(CPP, "extends"),
			FINAL        = new Keyword(CPP, "final"),
			FINALLY      = new Keyword(CPP, "finally"),
			FLOAT        = new Keyword(CPP, "float"),
			FOR          = new Keyword(CPP, "for"),
			GOTO         = new Keyword(CPP, "goto"),
			IF           = new Keyword(CPP, "if"),
			IMPLEMENTS   = new Keyword(CPP, "implements"),
			IMPORT       = new Keyword(CPP, "import"),
			INCLUDE      = new Keyword(CPP, "#include"),
			INSTANCEOF   = new Keyword(CPP, "instanceof"),
			INT          = new Keyword(CPP, "int"),
			INTERFACE    = new Keyword(CPP, "interface"),
			LONG         = new Keyword(CPP, "long"),
			NAMESPACE    = new Keyword(CPP, "namespace"),
			NATIVE       = new Keyword(CPP, "native"),
			NEW          = new Keyword(CPP, "new"),
			PACKAGE      = new Keyword(CPP, "package"),
			PRIVATE      = new Keyword(CPP, "private"),
			PROTECTED    = new Keyword(CPP, "protected"),
			PUBLIC       = new Keyword(CPP, "public"),
			RETURN       = new Keyword(CPP, "return"),
			SHORT        = new Keyword(CPP, "short"),
			STATIC       = new Keyword(CPP, "static"),
			STRICTFP     = new Keyword(CPP, "strictfp"),
			SUPER        = new Keyword(CPP, "super"),
			SWITCH       = new Keyword(CPP, "switch"),
			SYNCHRONIZED = new Keyword(CPP, "synchronized"),
			THIS         = new Keyword(CPP, "this"),
			THROW        = new Keyword(CPP, "throw"),
			THROWS       = new Keyword(CPP, "throws"),
			TRANSIENT    = new Keyword(CPP, "transient"),
			TRY          = new Keyword(CPP, "try"),
			USING        = new Keyword(CPP, "using"),
			VOID         = new Keyword(CPP, "void"),
			VOLATILE     = new Keyword(CPP, "volatile"),
			WHILE        = new Keyword(CPP, "while");
	
	static
	{
		ABSTRACT.setColor(CppLanguage.KEYWORD_COLOR);
		ASSERT.setColor(CppLanguage.KEYWORD_COLOR);
		BOOLEAN.setColor(CppLanguage.KEYWORD_COLOR);
		BREAK.setColor(CppLanguage.KEYWORD_COLOR);
		BYTE.setColor(CppLanguage.KEYWORD_COLOR);
		CASE.setColor(CppLanguage.KEYWORD_COLOR);
		CATCH.setColor(CppLanguage.KEYWORD_COLOR);
		CHAR.setColor(CppLanguage.KEYWORD_COLOR);
		CLASS.setColor(CppLanguage.KEYWORD_COLOR);
		CONST.setColor(CppLanguage.KEYWORD_COLOR);
		CONTINUE.setColor(CppLanguage.KEYWORD_COLOR);
		DEFAULT.setColor(CppLanguage.KEYWORD_COLOR);
		DO.setColor(CppLanguage.KEYWORD_COLOR);
		DOUBLE.setColor(CppLanguage.KEYWORD_COLOR);
		ELSE.setColor(CppLanguage.KEYWORD_COLOR);
		ENUM.setColor(CppLanguage.KEYWORD_COLOR);
		EXTENDS.setColor(CppLanguage.KEYWORD_COLOR);
		FINAL.setColor(CppLanguage.KEYWORD_COLOR);
		FINALLY.setColor(CppLanguage.KEYWORD_COLOR);
		FLOAT.setColor(CppLanguage.KEYWORD_COLOR);
		FOR.setColor(CppLanguage.KEYWORD_COLOR);
		GOTO.setColor(CppLanguage.KEYWORD_COLOR);
		IF.setColor(CppLanguage.KEYWORD_COLOR);
		IMPLEMENTS.setColor(CppLanguage.KEYWORD_COLOR);
		IMPORT.setColor(CppLanguage.KEYWORD_COLOR);
		INCLUDE.setColor(CppLanguage.INCLUDE_COLOR);
		INSTANCEOF.setColor(CppLanguage.KEYWORD_COLOR);
		INT.setColor(CppLanguage.KEYWORD_COLOR);
		INTERFACE.setColor(CppLanguage.KEYWORD_COLOR);
		LONG.setColor(CppLanguage.KEYWORD_COLOR);
		NAMESPACE.setColor(CppLanguage.KEYWORD_COLOR);
		NATIVE.setColor(CppLanguage.KEYWORD_COLOR);
		NEW.setColor(CppLanguage.KEYWORD_COLOR);
		PACKAGE.setColor(CppLanguage.KEYWORD_COLOR);
		PRIVATE.setColor(CppLanguage.KEYWORD_COLOR);
		PROTECTED.setColor(CppLanguage.KEYWORD_COLOR);
		PUBLIC.setColor(CppLanguage.KEYWORD_COLOR);
		RETURN.setColor(CppLanguage.KEYWORD_COLOR);
		SHORT.setColor(CppLanguage.KEYWORD_COLOR);
		STATIC.setColor(CppLanguage.KEYWORD_COLOR);
		STRICTFP.setColor(CppLanguage.KEYWORD_COLOR);
		SUPER.setColor(CppLanguage.KEYWORD_COLOR);
		SWITCH.setColor(CppLanguage.KEYWORD_COLOR);
		SYNCHRONIZED.setColor(CppLanguage.KEYWORD_COLOR);
		THIS.setColor(CppLanguage.KEYWORD_COLOR);
		THROW.setColor(CppLanguage.KEYWORD_COLOR);
		THROWS.setColor(CppLanguage.KEYWORD_COLOR);
		TRANSIENT.setColor(CppLanguage.KEYWORD_COLOR);
		TRY.setColor(CppLanguage.KEYWORD_COLOR);
		USING.setColor(CppLanguage.KEYWORD_COLOR);
		VOID.setColor(CppLanguage.KEYWORD_COLOR);
		VOLATILE.setColor(CppLanguage.KEYWORD_COLOR);
		WHILE.setColor(CppLanguage.KEYWORD_COLOR);
	}
	
	public static void init()
	{
		
	}
}