package net.foxycorndog.glshaderide.shaderlanguage;

import java.util.HashMap;

import net.foxycorndog.glshaderide.GLShaderIDE;

import org.eclipse.swt.graphics.Color;

public class Keyword
{
	private String word;
	
	private Color  color;
	
	private static HashMap<String, Keyword> keywords;
	
	static
	{
		keywords = new HashMap<String, Keyword>();
	}
	
	public static final Keyword INT = new Keyword("int"), FLOAT = new Keyword("float"), VEC2 = new Keyword("vec2"),
			VEC3 = new Keyword("vec3"), VEC4 = new Keyword("vec4"), MAT2 = new Keyword("mat2"), MAT3 = new Keyword("mat3"),
			MAT4 = new Keyword("mat4"), VOID = new Keyword("void"), VERSION = new Keyword("#version"),
			VARYING = new Keyword("varying"), UNIFORM = new Keyword("uniform"), SAMPLER2D = new Keyword("sampler2D"),
			GL_FRAGCOLOR = new Keyword("gl_FragColor"), GL_VERTEX = new Keyword("gl_Vertex"),
			GL_MODELVIEWMATRIX = new Keyword("gl_ModelViewMatrix"), GL_MODELVIEWPROJECTIONMATRIX = new Keyword("gl_ModelViewProjectionMatrix"),
			GL_POSITION = new Keyword("gl_Position"), GL_TEXCOORD = new Keyword("gl_TexCoord"),
			GL_LIGHTMODEL = new Keyword("gl_LightModel");
	
	static
	{
		INT.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		FLOAT.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		VEC2.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		VEC3.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		VEC4.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		MAT2.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		MAT3.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		MAT4.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		VOID.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		VARYING.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		UNIFORM.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		SAMPLER2D.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		GL_FRAGCOLOR.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		GL_VERTEX.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		GL_MODELVIEWMATRIX.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		GL_MODELVIEWPROJECTIONMATRIX.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		GL_POSITION.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		GL_TEXCOORD.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		GL_LIGHTMODEL.setColor(new Color(GLShaderIDE.display, 150, 0, 0));
		VERSION.setColor(new Color(GLShaderIDE.display, 180, 180, 0));
	}
	
	public Keyword(String word)
	{
		this.word = word;
		
		keywords.put(word, this);
	}
	
	public String getWord()
	{
		return word;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public static boolean isKeyword(String word)
	{
		return keywords.containsKey(word);
	}
	
	public static Keyword getKeyword(String word)
	{
		return keywords.get(word);
	}
}