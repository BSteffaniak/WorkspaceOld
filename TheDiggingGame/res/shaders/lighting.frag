uniform sampler2D texture;

varying float v_Dot;
varying vec2 v_texCoord;

void main()
{
    vec2 texCoord = vec2(v_texCoord.s, 1.0 - v_texCoord.t);
    vec4 color = texture2D(texture, texCoord);
    color += vec4(0.1, 0.1, 0.1, 1);
    gl_FragColor = vec4(color.xyz * v_Dot, color.a);
}

//void main()
//{
//	gl_FragColor = texture2D(texture, gl_TexCoord[0].st);
//	
//	vec3 origColor = gl_FragColor.rgb;
//	vec3 amb = gl_LightModel.ambient.rgb;
//	vec3 finalColor = origColor * amb;
//	
//	// Apply lighting to the scene.
//	// finalColor *= applyLighting(amb, origColor);
//	
//	gl_FragColor = vec4(finalColor, 1);
//}