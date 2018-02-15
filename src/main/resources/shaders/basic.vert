#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec3 _normal;

out vec3 normal;
out vec3 fragLightPos;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    fragLightPos = vec3(model * vec4(pos, 1.0));
    normal = mat3(transpose(inverse(model))) * _normal;
    gl_Position = projection * view * vec4(fragLightPos, 1.0);
}