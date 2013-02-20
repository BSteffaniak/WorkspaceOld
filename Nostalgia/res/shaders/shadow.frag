uniform sampler2DShadow shadowMap;

varying vec4 lightVertexPosition;

void main()
{
	float shadowValue = shadow2DProj(shadowMap, lightVertexPosition).r; // 1 if not in shadow, 0 else.
	
	gl_FragColor = vec4(shadowValue, shadowValue, shadowValue, 1.0);
}