attribute vec3 vertex;

uniform mat4 modelViewProjectionMatrix;
uniform mat4 lightModelViewProjectionMatrix;

varying vec4 lightVertexPosition;

void main()
{
	gl_Position = modelViewProjectionMatrix * vec4(vertex, 1.0);
	
	lightVertexPosition = lightModelViewProjectionMatrix * vec4(vertex, 1.0);
}