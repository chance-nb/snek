package org.chance_nb.snek.spaceinvaders;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import org.chance_nb.snek.*;

public class SpaceInvadersGameScreen implements Screen {
    Player player;
    public static float playerHeight = 1f;
    Main main;
    Array<Enemy> enemies = new Array<Enemy>();
    Array<Bullet> bullets = new Array<Bullet>();

    BitmapFont font;

    int points = 0;
    float time = 0f;
    float difficulty = 1f;

    float[][][] layouts = { { { 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }, { 4, 5, 6, 7, 8, 9, 10 }, { 5, 6, 7, 8, 9 } },
            { { 7 }, { 6, 8 }, { 5, 7, 9 } } };

    public SpaceInvadersGameScreen(Main main) {
        this.main = main;

        main.mainFontParam.size = 30;
        main.mainFontParam.color = Color.GREEN;
        font = main.mainFontGen.generateFont(main.mainFontParam);
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.02f);

        player = new Player(main, main.worldWidth / 2, playerHeight);

        spawnRandomlyGeneratedEnemyLayout();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        time += delta;
        // # Update
        player.update(this, delta);
        for (Enemy enemy : enemies) {
            enemy.update(this, delta);
        }
        for (Bullet bullet : bullets) {
            bullet.update(this, delta);
        }

        // # Render
        ScreenUtils.clear(Color.BLACK);
        main.viewport.apply();
        main.spriteBatch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.spriteBatch.begin();
        player.draw(main.spriteBatch);
        for (Enemy enemy : enemies) {
            enemy.draw(main.spriteBatch);
        }
        for (Bullet bullet : bullets) {
            bullet.draw(main.spriteBatch);
        }

        font.draw(main.spriteBatch, "Points: " + points, 2, 2);
        main.spriteBatch.end();
    }

    void spawnEnemyLayout(float[][] layout) {
        for (int i = 0; i < layout.length; i++) {
            for (int ii = 0; ii < layout[i].length; ii++) {
                enemies.add(new Enemy(main, layout[i][ii], main.worldHeight - (i + 1), 0.5f));
            }
        }
    }

    void spawnRandomlySelectedEnemyLayout() {
        spawnEnemyLayout(layouts[(int) MathUtils.random((float) layouts.length)]);
    }

    void spawnRandomlyGeneratedEnemyLayout() {
        int limy = (int) Math.clamp(Math.floor(difficulty * 0.5f), 1f, 5f);
        int limx = (int) Math.floor(difficulty * 2f)/limy;
        float spawnWidth = Math.clamp(difficulty * 0.3f, 0f, 7f);
        float[][] layout = new float[limy][limx];

        for (int i = 0; i < limy; i++) {
            for (int ii = 0; ii < limx; ii++) {
                layout[i][ii] = MathUtils.random(-spawnWidth, spawnWidth) + 7f;
            }
        }

        spawnEnemyLayout(layout);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}
