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


//Bus
uniform vec3 buspointlight_pos;
uniform vec3 busspotlight_pos;


out struct VertexData
{
    vec3 norm;
    vec2 tc;
    vec3 lightdir;
    vec3 lightdirsp;
    vec3 viewdir;
} vertexData;


void main(){



    mat4 modelview = view_matrix * model_matrix;
    vec4 pos = projection_matrix * modelview * vec4(position, 1.0f);


    gl_Position = pos;

    vertexData.norm =mat3(transpose(inverse(modelview))) * normal;
    vertexData.tc = texcoords; //* tcMultiplier;


    //Bus
    // toLight
    vec4 buslpos = view_matrix * vec4(buspointlight_pos,1.0f);
    vec4 busp = (view_matrix * model_matrix * vec4(position,1.0f));

    vertexData.lightdir =(buslpos-busp).xyz;

    vec4 busslpos =vec4(busspotlight_pos,1.0f);
    vertexData.lightdirsp = (busslpos-busp ).xyz;
    // toCamera
    vertexData.viewdir =-busp.xyz;
}
