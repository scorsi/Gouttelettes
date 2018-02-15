#version 330 core

//in vec2 texCoord;

out vec4 FragColor;

//uniform sampler2D sampler1;
//uniform sampler2D sampler2;
uniform vec3 objectColor;
uniform vec3 lightColor;

void main()
{
//    FragColor = mix(texture(sampler1, texCoord), texture(sampler2, texCoord), 0.5f);
    FragColor = vec4(objectColor * lightColor, 1);
}