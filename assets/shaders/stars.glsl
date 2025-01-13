#version 150

#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;

uniform float u_time;
uniform vec2 u_resolution;
uniform sampler2D u_texture;
uniform float u_density;

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
    vec2 uv = gl_FragCoord.xy / vec2(2.0);
    vec4 texColor = texture2D(u_texture, v_texCoords);

    float n = noise(uv);
    n = smoothstep(u_density, 2.0, n + noise(uv+u_time*0.5));
    vec3 color = vec3(n*random(uv+uv.y)*1.5, n*random(uv+uv.xy)*1.5, n*random(uv+uv.x))*1.5;
    gl_FragColor = vec4(color, texColor.a);
}
