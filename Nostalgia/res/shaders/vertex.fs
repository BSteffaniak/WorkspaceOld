#version 120

// Per-vertex Phong lighting model

varying vec4  fragScreenPos;
varying vec4  fragWorldPos;

uniform vec3  camPos;
uniform vec3  flashlightPos;

uniform sampler2D texture;

vec3 flashlight(vec3);
vec3 applyLighting(vec3, vec3);

void main()
{
	gl_FragColor    = texture2D(texture, gl_TexCoord[0].st);

	vec3 origColor  = gl_FragColor.rgb;
	vec3 amb        = gl_LightModel.ambient.rgb;
	vec3 finalColor = origColor * amb;

	// Apply flashlight effect to the scene.
	finalColor = flashlight(origColor);

//	// Apply lighting to the scene.
//	finalColor *= applyLighting(amb, origColor);

	gl_FragColor = vec4(finalColor, 1);
}
