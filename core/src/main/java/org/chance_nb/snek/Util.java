package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;

public class Util {
    /**
     * Clamps a number to a range by repeatedly subtracting the width of that range
     * so the number is in the range
     *
     * @param num the nuber to clamp
     * @param min the lower bound to clamp to
     * @param max the upper bound to clamp to
     * @return the clamped number
     */
    public static float wrapClamp(float num, float min, float max) {
        if (num > max || num < min) {
            float span = max - min;
            num -= (float) (Math.floor(num / span) * span);
        }
        return num;
    }

    /**
     * Wrap clamps a Vector2 by individually clamping its components
     */
    public static Vector2 wrapClampVec2(Vector2 vec2, float minx, float maxx, float miny, float maxy) {
        return new Vector2(wrapClamp(vec2.x, minx, maxx), wrapClamp(vec2.y, miny, maxy));
    }

    /**
     * WrapClamps a Vector2 using the world dimensions
     */
    public static Vector2 wrapClampVec2World(Main main, Vector2 vec2) {
        return wrapClampVec2(vec2, 0f, main.worldWidth + 0.2f, 0f, main.worldHeight + 0.2f);
    }

    /**
     * Calculates the angle the vector v2 - v1 has
     *
     * @return The Angle in degrees
     */
    public static float getAngle(Vector2 v1, Vector2 v2) {
        return v2.sub(v1).angleDeg();
    }

    /**
     * checks whether pos1's distane to pos2 is less than range
     */
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

    /**
     * Uses a given draw function with a shader on a batch
     *
     * @param drawable The draw function
     * @param shader   The Shader to use
     * @param batch    The SpriteBatch to draw on
     */
    public static void drawWithTexShader(Drawable drawable, ShaderProgram shader, SpriteBatch batch) {
        shader.setUniformi("u_texture", 1);
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE1);
        batch.setShader(shader);

        drawable.draw();

        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        batch.setShader(null);
    }
}
