#version 120

/* Per-vertex Phong lighting model
 *
 * Varying is set by the vertex program and read by the fragment
 * program.
 * Uniform is set by the Java code and read by the vertex and fragment
 * program.
 * Attribute is set by the Java code and read by the vertex program.
 */
uniform int   lightNumber;
uniform int   time;
uniform int   counter;
uniform int   screenWidth, screenHeight;

uniform float intensity;

uniform vec3  camPos;

uniform vec3  specColor;

varying vec4  fragScreenPos;
varying vec4  fragWorldPos;
varying vec3  fragNormal;

attribute vec2 texCoord;

/* Object Space: Vertices you specify with glVertex(Pointer)
 * Eye Space: Vertices transformed by the modelview matrix
 * (affected by glTranslate/glRotate)
 * Clip Space: Vertices transformed by the projection matrix
 * (affected by gluPerspective/glOrtho)
 *
 * Diffuse lighting: light is reflected in every direction evenly
 * To calculate the intensity of the reflected lighting we need the cosine
 * of the angle between the light and the surface normal. We could also use
 * diffuse attributes of the material and of the light.
 * specular lighting:
 */

// The colour we're going to pass to the fragment shader.
varying vec4 varyingColour;
// The normal we're going to pass to the fragment shader.
varying vec3 varyingNormal;
// The vertex we're going to pass to the fragment shader.
varying vec4 varyingVertex;

void main()
{
	fragScreenPos = gl_ModelViewMatrix * gl_Vertex;
	fragWorldPos  = gl_Vertex;

	gl_TexCoord[0] = gl_MultiTexCoord0;

	/* Pass the vertex normal attribute to the fragment shader.
	 * This value will be interpolated automatically by OpenGL
	 * if GL_SHADE_MODEL is GL_SMOOTH. (that's the default)
	 */
	fragNormal = (gl_NormalMatrix * gl_Normal).xyz;

	/* Send the vertex position, modified by glTranslate/glRotate/glScale
	 * and glOrtho/glFrustum/gluPerspective to primitive assembly.
	 */
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}