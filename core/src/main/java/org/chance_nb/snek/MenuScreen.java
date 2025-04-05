package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {
    Main main;
    BitmapFont instructFont;
    BitmapFont titleFont;
    static float blinkinterval = 0.6f;

    float time = 0f;
    float blinktimer = 0;

    boolean gameStart = true;
    int points;
    int runTime;

    boolean inputReleased = false;

    TogglesUI toggleui;

    public MenuScreen(Main main) {
        this.main = main;
        toggleui = new TogglesUI(main);
        main.mainFontParam.size = 55;
        main.mainFontParam.color = Color.PURPLE;
        instructFont = main.mainFontGen.generateFont(main.mainFontParam);
        main.mainFontParam.size = 100;
        titleFont = main.mainFontGen.generateFont(main.mainFontParam);
        instructFont.setUseIntegerPositions(false);
        instructFont.getData().setScale(0.02f);
        titleFont.setUseIntegerPositions(false);
        titleFont.getData().setScale(0.02f);
    }

    public MenuScreen(Main main, int points, float runTime) {
        this(main);
        gameStart = false;
        this.points = points;
        this.runTime = Math.round(runTime);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        time += delta;

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
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

        if (gameStart) {
            Util.drawWithTexShader(
                    () -> titleFont.draw(main.spriteBatch, "SNEK", main.viewport.getWorldWidth() / 2 - 2.5f,
                            main.viewport.getWorldHeight() / 2 + 1.5f, 5f, 1, false),
                    main.starShader, main.spriteBatch);
        } else {
            Util.drawWithTexShader(
                    () -> instructFont.draw(main.spriteBatch, "Game Over!", main.viewport.getWorldWidth() / 2 - 2.5f,
                            main.viewport.getWorldHeight() / 2 + 1.5f, 5f, 1, false),
                    main.starShader, main.spriteBatch);

            Util.drawWithTexShader(
                    () -> instructFont.draw(main.spriteBatch, "Points: " + points,
                            main.viewport.getWorldWidth() / 2 - 2.5f,
                            main.viewport.getWorldHeight() / 2 + 0.5f, 5f, 1, false),
                    main.starShader, main.spriteBatch);

            Util.drawWithTexShader(
                    () -> instructFont.draw(main.spriteBatch, "Time: " + runTime + " Seconds",
                            main.viewport.getWorldWidth() / 2 - 2.5f,
                            main.viewport.getWorldHeight() / 2 - 0.5f, 5f, 1, false),
                    main.starShader, main.spriteBatch);
        }

        if (blinktimer < blinkinterval) {
            Util.drawWithTexShader(
                    () -> instructFont.draw(main.spriteBatch, "Press SPACE", main.viewport.getWorldWidth() / 2 - 2.5f,
                            main.viewport.getWorldHeight() / 2 - 1.5f, 5f, 1, false),
                    main.starShader, main.spriteBatch);
        } else if (blinktimer > blinkinterval * 2) {
            blinktimer = 0;
        }

        toggleui.update();

        blinktimer += delta;

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
