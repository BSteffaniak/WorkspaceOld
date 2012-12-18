package net.foxycorndog.glshaderide.language.glsl;

import net.foxycorndog.glshaderide.GLShaderIDE;
import net.foxycorndog.glshaderide.language.Keyword;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.glshaderide.language.Language.GLSL;

public class GLSLKeyword
{
	public static final Keyword
			INT                          = new Keyword(GLSL, "int"),
			FLOAT                        = new Keyword(GLSL, "float"),
			VEC2                         = new Keyword(GLSL, "vec2"),
			VEC3                         = new Keyword(GLSL, "vec3"),
			VEC4                         = new Keyword(GLSL, "vec4"),
			MAT2                         = new Keyword(GLSL, "mat2"),
			MAT3                         = new Keyword(GLSL, "mat3"),
			MAT4                         = new Keyword(GLSL, "mat4"),
			VOID                         = new Keyword(GLSL, "void"),
			VERSION                      = new Keyword(GLSL, "#version"),
			VARYING                      = new Keyword(GLSL, "varying"),
			UNIFORM                      = new Keyword(GLSL, "uniform"),
			SAMPLER2D                    = new Keyword(GLSL, "sampler2D"),
			GL_FRAGCOLOR                 = new Keyword(GLSL, "gl_FragColor"),
			GL_VERTEX                    = new Keyword(GLSL, "gl_Vertex"),
			GL_MODELVIEWMATRIX           = new Keyword(GLSL, "gl_ModelViewMatrix"),
			GL_MODELVIEWPROJECTIONMATRIX = new Keyword(GLSL, "gl_ModelViewProjectionMatrix"),
			GL_POSITION                  = new Keyword(GLSL, "gl_Position"),
			GL_TEXCOORD                  = new Keyword(GLSL, "gl_TexCoord"),
			GL_LIGHTMODEL                = new Keyword(GLSL, "gl_LightModel");
	
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
		VERSION.setColor(new Color(GLShaderIDE.display, 180, 180, 0));
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
	}
	
	public static void init()
	{
		
	}
}