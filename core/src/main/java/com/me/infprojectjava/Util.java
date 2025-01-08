package com.me.infprojectjava;

import com.badlogic.gdx.math.Vector2;

public class Util {
    public static float wrapClamp(float num, float min, float max) {
        float span = max - min;
        if (num > max || num < min) {
            num -= (float) (Math.floor(num / span) * span) + 0.5f;
        }
        return num;
    }

    public static Vector2 wrapClampVec2(Vector2 vec2, float minx, float maxx, float miny, float maxy) {
        return new Vector2(wrapClamp(vec2.x, minx, maxx), wrapClamp(vec2.y, miny, maxy));
    }

    public static Vector2 wrapClampVec2World(Vector2 vec2, Main main) {
        return wrapClampVec2(vec2, 0f, main.worldWidth, 0f, main.worldHeight);
    }
}
