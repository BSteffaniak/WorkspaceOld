#version 120

// Per-vertex Phong lighting model
varying vec3 color;

varying vec3 varyingColour;

void main()
{
//	gl_FragColor = vec4(color, 1);

	// Turns the varying color into a 4D color and stores in the built-in output gl_FragColor.
    gl_FragColor = vec4(varyingColour, 1);
}
