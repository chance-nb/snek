package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameoverScreen implements Screen {
    Main main;
    BitmapFont font;
    int points;
    float timecounter = 0;
    static float blinkinterval = 0.6f;

    float time = 0f;

    boolean inputReleased = false;

    public GameoverScreen(Main main, int points, FreeTypeFontGenerator fontGen,
            FreeTypeFontGenerator.FreeTypeFontParameter fontParam) {
        this.main = main;
        this.points = points;
        fontParam.size = 55;
        fontParam.color = Color.PURPLE;
        this.font = fontGen.generateFont(fontParam);
        this.font.setUseIntegerPositions(false);
        this.font.getData().setScale(0.02f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        time += delta;

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)) {
            if (inputReleased) {
                main.setScreen(new GameScreen(main));
            }
        } else if (!inputReleased) {
            inputReleased = true;
        }

        ScreenUtils.clear(Color.BLACK);
        main.viewport.apply();
        main.spriteBatch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.spriteBatch.begin();

        main.starShader.bind();
        main.starShader.setUniformf("u_time", time);
        main.starShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.starShader.setUniformf("u_density", 1.0f);
        Util.drawWithTexShader(
                () -> font.draw(main.spriteBatch, "Game Over!", main.viewport.getWorldWidth() / 2 - 2.5f,
                        main.viewport.getWorldHeight() / 2 + 1, 5f, 1, false),
                main.starShader, main.spriteBatch);

        Util.drawWithTexShader(
                () -> font.draw(main.spriteBatch, "Points: " + points, main.viewport.getWorldWidth() / 2 - 2.5f,
                        main.viewport.getWorldHeight() / 2, 5f, 1, false),
                main.starShader, main.spriteBatch);

        if (timecounter < blinkinterval) {
            Util.drawWithTexShader(
                    () -> font.draw(main.spriteBatch, "CLICK to restart", main.viewport.getWorldWidth() / 2 - 2.5f,
                            main.viewport.getWorldHeight() / 2 - 1, 5f, 1, false),
                    main.starShader, main.spriteBatch);
        } else if (timecounter > blinkinterval * 2) {
            timecounter = 0;
        }

        timecounter += delta;

        main.spriteBatch.end();
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
