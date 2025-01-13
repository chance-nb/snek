package org.chance_nb.snek;

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

    public static Vector2 wrapClampVec2World(Main main, Vector2 vec2) {
        return wrapClampVec2(vec2, 0f, main.worldWidth+0.6f, 0f, main.worldHeight+0.6f);
    }

    public static float getAngle(Vector2 pos1, Vector2 pos2) {
        return pos2.sub(pos1).angleDeg();
    }

    public static boolean checkCollision(Main main, Vector2 pos1, Vector2 pos2, float range) {
        return Math.abs(Util.wrapClampVec2World(main, pos1).sub(Util.wrapClampVec2World(main, pos2)).len()) < range;
    }

    enum Direction {
        L, UL, U, UR, R, DR, D, DL;
    }
}
