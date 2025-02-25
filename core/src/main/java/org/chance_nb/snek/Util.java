package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
        return wrapClampVec2(vec2, 0f, main.worldWidth + 0.6f, 0f, main.worldHeight + 0.6f);
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

    @FunctionalInterface
    public interface Drawable {
        void draw();
    }

    public static void drawWithTexShader(Drawable drawable, ShaderProgram shader, SpriteBatch batch) {
        shader.setUniformi("u_texture", 1);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1);
        batch.setShader(shader);

        drawable.draw();

        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        batch.setShader(null);
    }
}
