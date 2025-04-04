#ifdef GL_ES
precision mediump float;
#endif

uniform float u_time;
uniform vec2 u_resolution;
uniform vec3 u_colorshift;

// 2D Random
float random (in vec2 st) {
    return fract(sin(dot(st.xy,
                         vec2(12.9898,78.233)))
                 * 43758.5453123);
}

// 2D Noise based on Morgan McGuire @morgan3d
// https://www.shadertoy.com/view/4dS3Wd
float noise (in vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);

    // Four corners in 2D of a tile
    float a = random(i);
    float b = random(i + vec2(1.0, 0.0));
    float c = random(i + vec2(0.0, 1.0));
    float d = random(i + vec2(1.0, 1.0));

    // Smooth Interpolation

    // Cubic Hermine Curve.  Same as SmoothStep()
    vec2 u = f*f*(3.0-2.0*f);
    // u = smoothstep(0.,1.,f);

    // Mix 4 coorners percentages
    return mix(a, b, u.x) +
    (c - a)* u.y * (1.0 - u.x) +
    (d - b) * u.x * u.y;
}

void main() {
    vec2 st = gl_FragCoord.xy / u_resolution;

    float nn = noise(st-u_time+random(st));
    float n = noise(vec2(st.x*(nn*10.0*abs(sin(u_time*nn))), st.y*(nn*10.0*abs(cos(u_time*nn)))));
    vec3 color = vec3(smoothstep(0.0,0.6,abs(sin(n)))+u_colorshift.r, smoothstep(0.0,0.6,abs(cos(n*nn)))+u_colorshift.g, smoothstep(0.0,0.6,abs(sin(nn)))+u_colorshift.b);
    gl_FragColor = vec4(color, 1.0);
}
