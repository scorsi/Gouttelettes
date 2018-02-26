# Gouttelettes Engine (WIP)

[![Travis Build Status](https://travis-ci.org/scorsi/Gouttelettes.svg?branch=master)](https://travis-ci.org/scorsi/Gouttelettes)

Gouttelettes is a 3D Game Engine written in full-Kotlin with high-performances designed to create powerful graphics using OpenGL.

This is the third time I restart my game-engine, so the project is under hard (and actually fast) development.

## Preview

One cube with a diffuse and specular texture. One source light.

We can see the source light reflection on the edge of the box.

![Preview 0](https://raw.githubusercontent.com/scorsi/Gouttelettes/master/.github/preview.png)

### Directional light
![Preview 1](https://raw.githubusercontent.com/scorsi/Gouttelettes/master/.github/preview1.png)
### Point light
![Preview 2](https://raw.githubusercontent.com/scorsi/Gouttelettes/master/.github/preview2.png)
    
## Roadmap

### 0.1

#### _Estimated for July 2018._

3D:
- [X] Window
- [X] Input
- [X] Textures with SBT
- [X] *First* Models
- [X] *First* Light
- [X] Light Map (ambient, diffuse and specular)
- [X] Different type light : directional and point lights
- [X] *First* Multiple light sources support
- [ ] Model loading with ASSIMP
- [ ] Depth testing
- [ ] Stencil testing
- [ ] Blending
- [ ] Face culling
- [ ] Framebuffers
- [ ] CubeMap
- [ ] Geometry Shader
- [ ] Anti aliasing
- [ ] Blinn-Phong
- [ ] Gamma correction
- [ ] Shadows
- [ ] Normal mapping
- [ ] Parallax mapping
- [ ] HDR
- [ ] Bloom
- [ ] Deferred Shading
- [ ] SSAO
- [ ] PBR

UI:
- [ ] UI with NanoVG

Audio:
- [ ] Audio Management
- [ ] Audio loading with files

Game engine:
- [ ] Scene and Entity management
- [ ] Store Scene and Entity in JSON files
