uniform mat4 u_modelViewProjMatrix;
uniform mat4 u_normalMatrix;
uniform vec3 lightDir;
 
attribute vec3 vNormal;
attribute vec4 vTexCoord;
attribute vec4 vPosition;

varying float v_Dot;
varying vec2 v_texCoord;

void main()
{
	gl_Position = u_modelViewProjMatrix * vPosition;
	v_texCoord = vTexCoord.st;
	vec4 transNormal = u_normalMatrix * vec4(vNormal, 1);
	v_Dot = max(dot(transNormal.xyz, lightDir), 0.0);
}

//varying vec4	fragScreenPos;
//varying vec4	fragWorldPos;
//varying vec3	fragNormal;
//
//attribute vec2 vPosition;
//
//void main()
//{
////	fragScreenPos  = gl_ModelViewMatrix * gl_Vertex;
////	fragWorldPos   = gl_Vertex;
//	
////	gl_TexCoord[0] = gl_MultiTexCoord0;
//	
//	/* Pass the vertex normal attribute to the fragment shader.
//	* This value will be interpolated automatically by OpenGL
//	* if GL_SHADE_MODEL is GL_SMOOTH. (that's the default)
//	*/
////	fragNormal  = (gl_NormalMatrix * gl_Normal).xyz;
//	
//	/* Send the vertex position, modified by glTranslate/glRotate/glScale
//	* and glOrtho/glFrustum/gluPerspective to primitive assembly.
//	*/
//	//gl_Position = gl_ModelViewProjectionMatrix * gl_Position;;//fragWorldPos;//gl_ModelViewProjectionMatrix * gl_Vertex;
//	
//	const float right  = 1280.0;
//	const float bottom = 0.0;
//	const float left   = 0.0;
//	const float top    = 800.0;
//	const float far    = 1.0;
//	const float near   = -1.0;
//	
//	 mat4 orthoMat = mat4(
//	    vec4(2.0 / (right - left),              0,                                0,                            0),
//	    vec4(0,                                 2.0 / (top - bottom),             0,                            0),
//	    vec4(0,                                 0,                               -2.0 / (far - near),           0),
//	    vec4(-(right + left) / (right - left), -(top + bottom) / (top - bottom), -(far + near) / (far - near),  1)
//	);
//	
//	gl_Position = orthoMat * vec4(vPosition, 0.0, 1.0);
//	
////	gl_Position = ftransform();
//}