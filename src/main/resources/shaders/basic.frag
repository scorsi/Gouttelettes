#version 330 core

in vec3 Color;
in vec2 TexCoord;

out vec4 FragColor;

uniform sampler2D Sampler1;
uniform sampler2D Sampler2;

void main()
{
    FragColor = mix(texture(Sampler1, TexCoord), texture(Sampler2, TexCoord), 0.5f) * vec4(Color, 1f);
}