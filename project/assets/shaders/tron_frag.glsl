#version 330 core

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

//Stern1
uniform vec3 stern1pointlight_col;
uniform vec3 stern1pointlight_attenuation;

//Stern2
uniform vec3 stern2pointlight_col;
uniform vec3 stern2pointlight_attenuation;

//Stern3
uniform vec3 stern3pointlight_col;
uniform vec3 stern3pointlight_attenuation;

//Stern4
uniform vec3 stern4pointlight_col;
uniform vec3 stern4pointlight_attenuation;

//Stern5
uniform vec3 stern5pointlight_col;
uniform vec3 stern5pointlight_attenuation;

//Stern6
uniform vec3 stern6pointlight_col;
uniform vec3 stern6pointlight_attenuation;

//Stern7
uniform vec3 stern7pointlight_col;
uniform vec3 stern7pointlight_attenuation;

//Stern8
uniform vec3 stern8pointlight_col;
uniform vec3 stern8pointlight_attenuation;

//Stern9
uniform vec3 stern9pointlight_col;
uniform vec3 stern9pointlight_attenuation;

//Stern10
uniform vec3 stern10pointlight_col;
uniform vec3 stern10pointlight_attenuation;

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

//fragment shader output
out vec4 color;


void main(){
    vec3 normale = normalize(vertexData.norm);
    vec3 tocamera = normalize(vertexData.viewdir);
    float pointlength = length(vertexData.lightdir);
    vec3 tolight = vertexData.lightdir /pointlength;
    float spotlength = length(vertexData.lightdirsp);
    vec3 toSpotLight = vertexData.lightdirsp/spotlength;

    float stern1pointlength = length(vertexData.stern1lightdir);
    vec3 stern1tolight = vertexData.stern1lightdir /stern1pointlength;

    float stern2pointlength = length(vertexData.stern2lightdir);
    vec3 stern2tolight = vertexData.stern2lightdir /stern2pointlength;

    float stern3pointlength = length(vertexData.stern3lightdir);
    vec3 stern3tolight = vertexData.stern3lightdir /stern3pointlength;

    float stern4pointlength = length(vertexData.stern4lightdir);
    vec3 stern4tolight = vertexData.stern4lightdir /stern4pointlength;

    float stern5pointlength = length(vertexData.stern5lightdir);
    vec3 stern5tolight = vertexData.stern5lightdir /stern5pointlength;

    float stern6pointlength = length(vertexData.stern6lightdir);
    vec3 stern6tolight = vertexData.stern6lightdir /stern6pointlength;

    float stern7pointlength = length(vertexData.stern7lightdir);
    vec3 stern7tolight = vertexData.stern7lightdir /stern7pointlength;

    float stern8pointlength = length(vertexData.stern8lightdir);
    vec3 stern8tolight = vertexData.stern8lightdir /stern8pointlength;

    float stern9pointlength = length(vertexData.stern9lightdir);
    vec3 stern9tolight = vertexData.stern9lightdir /stern9pointlength;

    float stern10pointlength = length(vertexData.stern10lightdir);
    vec3 stern10tolight = vertexData.stern10lightdir /stern10pointlength;

    //Texturen
    vec3 texemit = texture(emit, vertexData.tc).rgb ;
    vec3 texdiff = texture(diff, vertexData.tc).rgb;
    vec3 texspec = texture(specular, vertexData.tc).rgb;

    //Bike

    // Emissive + Ambient
    vec3 ambientemit = berechnungemit(texemit, shadingcolor);
    // Diffuse +Sepcular
    vec3 diffspecpl = berechnungdifspec(normale, tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecsl = berechnungdifspec(normale, toSpotLight, tocamera, texdiff, texspec, shininess);

    vec3 diffspecplstern1 = berechnungdifspec(normale, stern1tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern2 = berechnungdifspec(normale, stern2tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern3 = berechnungdifspec(normale, stern3tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern4 = berechnungdifspec(normale, stern4tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern5 = berechnungdifspec(normale, stern5tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern6 = berechnungdifspec(normale, stern6tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern7 = berechnungdifspec(normale, stern7tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern8 = berechnungdifspec(normale, stern8tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern9 = berechnungdifspec(normale, stern9tolight, tocamera, texdiff, texspec, shininess);
    vec3 diffspecplstern10 = berechnungdifspec(normale, stern10tolight, tocamera, texdiff, texspec, shininess);

    //Intensity
    vec3 intensitypl =  berechnungattentuation(pointlength, bikepointlight_attenuation) *bikepointlight_col;

    vec3 intensityplstern1 =  berechnungattentuation(stern1pointlength, stern1pointlight_attenuation) *stern1pointlight_col;
    vec3 intensityplstern2 =  berechnungattentuation(stern2pointlength, stern2pointlight_attenuation) *stern2pointlight_col;
    vec3 intensityplstern3 =  berechnungattentuation(stern3pointlength, stern3pointlight_attenuation) *stern3pointlight_col;
    vec3 intensityplstern4 =  berechnungattentuation(stern4pointlength, stern4pointlight_attenuation) *stern4pointlight_col;
    vec3 intensityplstern5 =  berechnungattentuation(stern5pointlength, stern5pointlight_attenuation) *stern5pointlight_col;
    vec3 intensityplstern6 =  berechnungattentuation(stern6pointlength, stern6pointlight_attenuation) *stern6pointlight_col;
    vec3 intensityplstern7 =  berechnungattentuation(stern7pointlength, stern7pointlight_attenuation) *stern7pointlight_col;
    vec3 intensityplstern8 =  berechnungattentuation(stern8pointlength, stern8pointlight_attenuation) *stern8pointlight_col;
    vec3 intensityplstern9 =  berechnungattentuation(stern9pointlength, stern9pointlight_attenuation) *stern9pointlight_col;
    vec3 intensityplstern10 =  berechnungattentuation(stern10pointlength, stern10pointlight_attenuation) *stern10pointlight_col;


    vec3 internsitysl = berechnungintensity(toSpotLight, bikespotlight_dir, bikespot_outer, bikespot_inner,spotlength, bikespotLightAttenuation) *bikespotlight_col;

    //emissive term
    vec3 result = vec3(ambientemit);
    //pointlight
    result += diffspecpl *intensitypl;
    result += diffspecplstern1 *intensityplstern1;
    result += diffspecplstern2 *intensityplstern2;
    result += diffspecplstern3 *intensityplstern3;
    result += diffspecplstern4 *intensityplstern4;
    result += diffspecplstern5 *intensityplstern5;
    result += diffspecplstern6 *intensityplstern6;
    result += diffspecplstern7 *intensityplstern7;
    result += diffspecplstern8 *intensityplstern8;
    result += diffspecplstern9 *intensityplstern9;
    result += diffspecplstern10 *intensityplstern10;

    //spotlight
    result += diffspecsl * internsitysl;

    color = vec4(result, 1.0f);

}

