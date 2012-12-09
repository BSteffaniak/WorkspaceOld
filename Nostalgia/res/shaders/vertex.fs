#version 120

// Per-vertex Phong lighting model

varying vec4  color;
varying vec4  fragScreenPos;
varying vec4  fragWorldPos;

uniform vec3  camPos;
uniform vec3  flashlightPos;

uniform sampler2D texture;

void main()
{
	gl_FragColor    = texture2D(texture, gl_TexCoord[0].st);

	vec3 origColor  = gl_FragColor.rgb;
	vec3 amb        = gl_LightModel.ambient.rgb;
	vec3 finalColor = origColor * amb;

	vec3 lightCol = vec3(0.6f, 0.6f, 0.6f);

	float dist = 0;

	/*
	 * Calculates the distance that the light shows for.
	 */
	dist      = sqrt(pow(flashlightPos.x - fragWorldPos.x,  2) + pow(flashlightPos.y - fragWorldPos.y,  2) + pow(flashlightPos.z - fragWorldPos.z,  2));

	float radius      = 3;
	float largeRadius = radius + (radius * 1);
	float radDist     = sqrt(pow(fragScreenPos.x - flashlightPos.x * 0, 2) + pow(fragScreenPos.y - flashlightPos.y * 0, 2));

//	radius  = 0.1f + dist;

	if (radDist <= largeRadius)
	{
//		lightCol.r = lightCol.r < 0 ? 0 : lightCol.r;
//		lightCol.g = lightCol.g < 0 ? 0 : lightCol.g;
//		lightCol.b = lightCol.b < 0 ? 0 : lightCol.b;

		vec3 thing = finalColor;

//		thing = origColor * (lightCol * 0.1f);

		if (radDist <= radius)
		{
			lightCol.rgb *= min(1, radius - (radDist * 1));

			thing = origColor * lightCol;
		}
		else
		{
			if (lightCol.r < amb.r || lightCol.g < amb.g || lightCol.b < amb.b)
			{
//				lightCol.rgb *= min(1, largeRadius - (radDist * 1));

				thing = origColor * (lightCol * 0.5f);
			}
		}

		thing.r = lightCol.r < amb.r ? finalColor.r : thing.r;
		thing.g = lightCol.g < amb.g ? finalColor.g : thing.g;
		thing.b = lightCol.b < amb.b ? finalColor.b : thing.b;

		finalColor = thing;
	}

	gl_FragColor = vec4(finalColor, 1);
}
