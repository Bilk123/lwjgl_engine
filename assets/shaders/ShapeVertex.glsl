#version 330

layout(location = 0)in vec4 position;
layout(location = 1)in vec4 color;

uniform mat4 prMat;
uniform mat4 vmMat = mat4(1);

out DATA{
    vec4 position;
    vec4 color;
}vs_out;

void main() {
       gl_Position = prMat * vmMat * position;
       vs_out.position = position;
       vs_out.color = color;
}
