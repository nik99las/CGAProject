#version 330 core

uniform sampler2D diff;
uniform sampler2D emit;
uniform sampler2D specular;
uniform float shininess;

uniform vec3 bikepointlight_col;
uniform vec3 spotlight_dir;
uniform float spot_inner;
uniform float spot_outer;
uniform vec3 spotlight_col;
uniform vec3 bikepointlight_attenuation;
uniform vec3 spotLightAttenuation;
uniform vec3 shadingcolor;



// Ambient +Emit
vec3 berechnungemit(vec3 diff, vec3 color){

    vec3 ambientEmit = diff *color;
    return ambientEmit;
}

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

// Intensity +Attenuation
float berechnungintensity(vec3 lightdir, vec3 splightdir, float outercutoff, float cutoff,float distance, vec3 attentuationvalue){
    float theta =dot(lightdir, -splightdir);
    float intensity = clamp((theta - outercutoff) / (cutoff -outercutoff), 0.0f, 1.0f);

    return intensity * berechnungattentuation(distance, attentuationvalue);
}


//input from vertex shader
in struct VertexData
{
    vec3 norm;
    vec2 tc;
    vec3 lightdir;
    vec3 lightdirsp;
    vec3 viewdir;
} vertexData;

//fragment shader output
out vec4 color;


void main(){
    vec3 normale = normalize(vertexData.norm);
    vec3 tocamera = normalize(vertexData.viewdir);
    float pointlength = length(vertexData.lightdir);
    vec3 tolight = vertexData.lightdir /pointlength;
    float spotlength = length(vertexData.lightdirsp);
    vec3 toSpotLight = vertexData.lightdirsp/spotlength;

    //Texturen
    vec3 texemit = texture(emit, vertexData.tc).rgb ;
    vec3 texdiff = texture(diff, vertexData.tc).rgb;
    vec3 texspec = texture(specular, vertexData.tc).rgb;

    // Emissive + Ambient
    vec3 ambientemit = berechnungemit(texemit, shadingcolor);
    // Diffuse +Sepcular
    vec3 diffspecpl = berechnungdifspec(normale, tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecsl = berechnungdifspec(normale, toSpotLight, tocamera, texdiff, texspec, shininess);

    //Intensity
    vec3 intensitypl =  berechnungattentuation(pointlength, bikepointlight_attenuation) *bikepointlight_col;

   vec3 internsitysl = berechnungintensity(toSpotLight, spotlight_dir, spot_outer, spot_inner,spotlength, spotLightAttenuation) *spotlight_col;

    //emissive term
    vec3 result = vec3(ambientemit);
    //pointlight
    result += diffspecpl *intensitypl;
    //spotlight
    result += diffspecsl * internsitysl;
   // result += ambientemit;


    color = vec4(result, 1.0f);

}


