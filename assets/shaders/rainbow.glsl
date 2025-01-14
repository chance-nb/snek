#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;

uniform float u_time;
uniform vec2 u_resolution;
uniform sampler2D u_texture;

void main() {
    vec2 st = gl_FragCoord.xy / u_resolution;
    vec4 texColor = texture2D(u_texture, v_texCoords);

    vec3 color = cross(vec3(abs(cos(u_time)), abs(sin(u_time)), abs(sin(u_time))), texColor.rgb);
    gl_FragColor = vec4(color, texColor.a);
}
