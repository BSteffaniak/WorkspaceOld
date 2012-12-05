#version 120

// Per-vertex Phong lighting model
varying vec3 color;

varying vec3 varyingColour;

varying vec3 vertex_light_position;
varying vec3 vertex_normal;

void main()
{
	gl_FragColor = vec4(color, 1);

//	// Turns the varying color into a 4D color and stores in the built-in output gl_FragColor.
//    gl_FragColor = vec4(varyingColour, 1);
    
//    // Set the diffuse value (darkness). This is done with a dot product between the normal and the light
//   // and the maths behind it is explained in the maths section of the site.
//   float diffuse_value = max(dot(vertex_normal, vertex_light_position), 0.0);
//
//   // Set the output color of our current pixel
//   gl_FragColor = vec4(gl_Color.rgb * diffuse_value, 1);// + vec4(varyingColour, 1);
}
