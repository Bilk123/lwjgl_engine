#version 330

layout(location =0)out vec4 color;

uniform vec3 lightPos;

in DATA{
	vec4 position;
	vec4 color;
}fs_in;

void main()
{
    float intensity = (2.0/length(fs_in.position.xy-lightPos.xy));
	color = intensity*fs_in.color;
}