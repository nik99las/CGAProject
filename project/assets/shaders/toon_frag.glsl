#version 330 core

//input from vertex shader
in struct VertexData
{
    vec3 norm;
    vec2 tc;
    vec3 lightdir;
    vec3 lightdirsp;
    vec3 viewdir;
} vertexData;

//uniforms
uniform sampler2D diff;
uniform sampler2D emit;
uniform sampler2D specular;
uniform float shininess;

//Bike
uniform vec3 bikepointlight_col;
uniform vec3 bikespotlight_dir;
uniform float bikespot_inner;
uniform float bikespot_outer;
uniform vec3 bikespotlight_col;
uniform vec3 bikepointlight_attenuation;
uniform vec3 bikespotLightAttenuation;
uniform vec3 shadingcolor;

//Bus
uniform vec3 buspointlight_col;
uniform vec3 busspotlight_dir;
uniform float busspot_inner;
uniform float busspot_outer;
uniform vec3 busspotlight_col;
uniform vec3 buspointlight_attenuation;
uniform vec3 busspotlightAttenuation;

//fragment shader output
out vec4 color;

//Diffuse +Specular
vec3 berechnungdifspec(vec3 normal, vec3 lightdir, vec3 viewdir, vec3 diff, vec3 spec, float shininess) {

    float cosa = max(dot(normal, lightdir), 0.0f);
    vec3 diffuse =cosa *diff;

    vec3 reflect = normalize(reflect(-lightdir, normal));
    float cosbeta = max(0.0f, dot(viewdir, reflect));
    float cosBetaK = pow(cosbeta, shininess);
    vec3 specr = spec * cosBetaK;

    return specr +diffuse;
}

// Attenuation
float berechnungattentuation(float distance, vec3 attentuationvalue){
    float attenuation = 1.0f/(attentuationvalue.x + attentuationvalue.y *distance + attentuationvalue.z * (distance *distance));

    return attenuation;
}

void main(){
    vec3 normale = normalize(vertexData.norm);
    vec3 tocamera = normalize(vertexData.viewdir);
    float pointlength = length(vertexData.lightdir);
    vec3 tolight = vertexData.lightdir /pointlength;
    //Farbwerte der Texturen
    vec3 texemit = texture(emit, vertexData.tc).rgb ;
    vec3 texdiff = texture(diff, vertexData.tc).rgb;
    vec3 texspec = texture(specular, vertexData.tc).rgb;

    //Ankommende Lichtintensität
    vec3 intensitypl =  berechnungattentuation(pointlength, bikepointlight_attenuation) *bikepointlight_col;
    //Emmissiver Part
    vec3 result = texemit * shadingcolor;

    result += bikepointlight_col * 0.1f * texdiff;

    //licht
    result += berechnungdifspec(normale, tolight, tocamera, texdiff, texspec, shininess) * intensitypl;
    color = vec4(floor(result[0]*10)/10, floor(result[1]*10)/10, floor(result[2]*10)/10, 1.0f);
    //color = vec4(res, 1.0f);
}
