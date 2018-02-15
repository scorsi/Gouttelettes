#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec3 _normal;
layout (location = 2) in vec2 _texCoords;

out vec3 normal;
out vec3 fragLightPos;
out vec2 texCoords;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;

void main()
{
    fragLightPos = vec3(model * vec4(pos, 1.0));
    normal = mat3(transpose(inverse(model))) * _normal;
    texCoords = _texCoords;
    gl_Position = projection * view * vec4(fragLightPos, 1.0);
}