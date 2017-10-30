#version 330

layout(location = 0) in vec4 position;
layout(location = 1) in vec4 color;

uniform mat4 prMat;
uniform mat4 camTransMat;

out DATA{
	vec4 position;
	vec4 color;
}vs_out;


void main(){

    vs_out.position = position;
    vs_out.color =color;
    gl_Position = prMat * camTransMat * position;
}