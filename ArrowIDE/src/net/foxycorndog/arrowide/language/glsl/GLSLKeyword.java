package net.foxycorndog.arrowide.language.glsl;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.language.Keyword;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.arrowide.language.Language.GLSL;

public class GLSLKeyword
{
	public static final Keyword
			INT                          = new Keyword(GLSL, "int"),
			FLOAT                        = new Keyword(GLSL, "float"),
			DOUBLE                       = new Keyword(GLSL, "double"),
			VEC2                         = new Keyword(GLSL, "vec2"),
			VEC3                         = new Keyword(GLSL, "vec3"),
			VEC4                         = new Keyword(GLSL, "vec4"),
			MAT2                         = new Keyword(GLSL, "mat2"),
			MAT3                         = new Keyword(GLSL, "mat3"),
			MAT4                         = new Keyword(GLSL, "mat4"),
			VOID                         = new Keyword(GLSL, "void"),
			VERSION                      = new Keyword(GLSL, "#version"),
			VARYING                      = new Keyword(GLSL, "varying"),
			ATTRIBUTE                    = new Keyword(GLSL, "attribute"),
			UNIFORM                      = new Keyword(GLSL, "uniform"),
			SAMPLER2D                    = new Keyword(GLSL, "sampler2D"),
			GL_FRAGCOLOR                 = new Keyword(GLSL, "gl_FragColor"),
			GL_VERTEX                    = new Keyword(GLSL, "gl_Vertex"),
			GL_NORMAL                    = new Keyword(GLSL, "gl_Normal"),
			GL_NORMALMATRIX              = new Keyword(GLSL, "gl_NormalMatrix"),
			GL_MODELVIEWMATRIX           = new Keyword(GLSL, "gl_ModelViewMatrix"),
			GL_MODELVIEWPROJECTIONMATRIX = new Keyword(GLSL, "gl_ModelViewProjectionMatrix"),
			GL_POSITION                  = new Keyword(GLSL, "gl_Position"),
			GL_TEXCOORD                  = new Keyword(GLSL, "gl_TexCoord"),
			GL_MULTITEXCOORD0            = new Keyword(GLSL, "gl_MultiTexCoord0"),
			GL_MULTITEXCOORD1            = new Keyword(GLSL, "gl_MultiTexCoord1"),
			GL_MULTITEXCOORD2            = new Keyword(GLSL, "gl_MultiTexCoord2"),
			GL_MULTITEXCOORD3            = new Keyword(GLSL, "gl_MultiTexCoord3"),
			GL_MULTITEXCOORD4            = new Keyword(GLSL, "gl_MultiTexCoord4"),
			GL_MULTITEXCOORD5            = new Keyword(GLSL, "gl_MultiTexCoord5"),
			GL_MULTITEXCOORD6            = new Keyword(GLSL, "gl_MultiTexCoord6"),
			GL_MULTITEXCOORD7            = new Keyword(GLSL, "gl_MultiTexCoord7"),
			IF                           = new Keyword(GLSL, "if"),
			ELSE                         = new Keyword(GLSL, "else"),
			FOR                          = new Keyword(GLSL, "for"),
			WHILE                        = new Keyword(GLSL, "while"),
			GL_LIGHTMODEL                = new Keyword(GLSL, "gl_LightModel");
	
	static
	{
		INT.setColor(GLSLLanguage.KEYWORD_COLOR);
		FLOAT.setColor(GLSLLanguage.KEYWORD_COLOR);
		DOUBLE.setColor(GLSLLanguage.KEYWORD_COLOR);
		VEC2.setColor(GLSLLanguage.KEYWORD_COLOR);
		VEC3.setColor(GLSLLanguage.KEYWORD_COLOR);
		VEC4.setColor(GLSLLanguage.KEYWORD_COLOR);
		MAT2.setColor(GLSLLanguage.KEYWORD_COLOR);
		MAT3.setColor(GLSLLanguage.KEYWORD_COLOR);
		MAT4.setColor(GLSLLanguage.KEYWORD_COLOR);
		VOID.setColor(GLSLLanguage.KEYWORD_COLOR);
		VERSION.setColor(GLSLLanguage.VERSION_COLOR);
		VARYING.setColor(GLSLLanguage.KEYWORD_COLOR);
		ATTRIBUTE.setColor(GLSLLanguage.KEYWORD_COLOR);
		UNIFORM.setColor(GLSLLanguage.KEYWORD_COLOR);
		SAMPLER2D.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_FRAGCOLOR.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_VERTEX.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_NORMAL.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_NORMALMATRIX.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MODELVIEWMATRIX.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MODELVIEWPROJECTIONMATRIX.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_POSITION.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_TEXCOORD.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD0.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD1.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD2.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD3.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD4.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD5.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD6.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_MULTITEXCOORD7.setColor(GLSLLanguage.KEYWORD_COLOR);
		IF.setColor(GLSLLanguage.KEYWORD_COLOR);
		ELSE.setColor(GLSLLanguage.KEYWORD_COLOR);
		FOR.setColor(GLSLLanguage.KEYWORD_COLOR);
		WHILE.setColor(GLSLLanguage.KEYWORD_COLOR);
		GL_LIGHTMODEL.setColor(GLSLLanguage.KEYWORD_COLOR);
	}
	
	public static void init()
	{
		
	}
}