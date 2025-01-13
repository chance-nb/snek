#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;

void main() {
    vec2 st = gl_FragCoord.xy / u_resolution;
    vec3 color = vec3(abs(sin(u_time + st.x)), abs(sin(u_time + st.y)), abs(sin(u_time)));
    gl_FragColor = vec4(color, 1.0);
}
