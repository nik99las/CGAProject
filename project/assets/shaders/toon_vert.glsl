#version 330 core

layout(location = 0) in vec3 position;
layout(location = 2) in vec3 normal;
layout(location = 1) in vec2 texcoords;


//uniforms
// translation object to world
uniform mat4 model_matrix;
uniform mat4 view_matrix;
uniform mat4 projection_matrix;
uniform vec2 tcMultiplier ;
uniform vec3 bikespotlight_pos;
uniform vec3 bikepointlight_pos;

uniform vec3 stern1pointlight_pos;
uniform vec3 stern2pointlight_pos;
uniform vec3 stern3pointlight_pos;
uniform vec3 stern4pointlight_pos;
uniform vec3 stern5pointlight_pos;
uniform vec3 stern6pointlight_pos;
uniform vec3 stern7pointlight_pos;
uniform vec3 stern8pointlight_pos;
uniform vec3 stern9pointlight_pos;
uniform vec3 stern10pointlight_pos;


out struct VertexData
{
    vec3 norm;
    vec2 tc;
    vec3 lightdir;
    vec3 lightdirsp;
    vec3 viewdir;
    vec3   stern1lightdir;
    vec3   stern2lightdir;
    vec3   stern3lightdir;
    vec3   stern4lightdir;
    vec3   stern5lightdir;
    vec3   stern6lightdir;
    vec3   stern7lightdir;
    vec3   stern8lightdir;
    vec3   stern9lightdir;
    vec3   stern10lightdir;


} vertexData;


void main(){
    mat4 modelview = view_matrix * model_matrix;
    vec4 pos = projection_matrix * modelview * vec4(position, 1.0f);


    gl_Position = pos;
    vertexData.norm =mat3(transpose(inverse(modelview))) * normal;
    vertexData.tc = texcoords;//* tcMultiplier;
    //Bike
    // toLight
    vec4 lpos = view_matrix * vec4(bikepointlight_pos, 1.0f);

    vec4 lposstern1 = view_matrix * vec4(stern1pointlight_pos,1.0f);
    vec4 lposstern2 = view_matrix * vec4(stern2pointlight_pos,1.0f);
    vec4 lposstern3 = view_matrix * vec4(stern3pointlight_pos,1.0f);
    vec4 lposstern4 = view_matrix * vec4(stern4pointlight_pos,1.0f);
    vec4 lposstern5 = view_matrix * vec4(stern5pointlight_pos,1.0f);
    vec4 lposstern6 = view_matrix * vec4(stern6pointlight_pos,1.0f);
    vec4 lposstern7 = view_matrix * vec4(stern7pointlight_pos,1.0f);
    vec4 lposstern8 = view_matrix * vec4(stern8pointlight_pos,1.0f);
    vec4 lposstern9 = view_matrix * vec4(stern9pointlight_pos,1.0f);
    vec4 lposstern10 = view_matrix * vec4(stern10pointlight_pos,1.0f);

    vec4 p = (view_matrix * model_matrix * vec4(position, 1.0f));

    vertexData.lightdir =(lpos-p).xyz;

    vertexData.stern1lightdir =(lposstern1-p).xyz;
    vertexData.stern2lightdir =(lposstern2-p).xyz;
    vertexData.stern3lightdir =(lposstern3-p).xyz;
    vertexData.stern4lightdir =(lposstern4-p).xyz;
    vertexData.stern5lightdir =(lposstern5-p).xyz;
    vertexData.stern6lightdir =(lposstern6-p).xyz;
    vertexData.stern7lightdir =(lposstern7-p).xyz;
    vertexData.stern8lightdir =(lposstern8-p).xyz;
    vertexData.stern9lightdir =(lposstern9-p).xyz;
    vertexData.stern10lightdir =(lposstern10-p).xyz;


    vec4 slpos =vec4(bikespotlight_pos, 1.0f);
    vertexData.lightdirsp = (slpos-p).xyz;
    // toCamera
    vertexData.viewdir =-p.xyz;
}

