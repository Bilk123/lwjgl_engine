#version 330

layout(location =0)out vec4 color;

uniform vec3 lightPos;
uniform sampler2D textures[32];

in DATA{
	vec4 position;
	vec2 uv;
	float tid;
	vec4 color;
}fs_in;


void main()
{
    float intensity = 0.075+(1.0/length(fs_in.position.xy-lightPos.xy));

	vec4 texColor = fs_in.color;

	if(fs_in.tid > 0){
	    int tid = int(fs_in.tid - 0.5);
	    texColor = texture(textures[tid], fs_in.uv);
	}
    color = texColor;
}