package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen implements Screen {
    Main main;
    BitmapFont instructFont;
    BitmapFont titleFont;
    static float blinkinterval = 0.6f;

    float time = 0f;
    float timecounter = 0;

    TogglesUI toggleui;

    public StartScreen(Main main) {
        this.main = main;
        this.toggleui = new TogglesUI(main);
        main.mainFontParam.size = 55;
        main.mainFontParam.color = Color.PURPLE;
        this.instructFont = main.mainFontGen.generateFont(main.mainFontParam);
        main.mainFontParam.size = 100;
        this.titleFont = main.mainFontGen.generateFont(main.mainFontParam);
        this.instructFont.setUseIntegerPositions(false);
        this.instructFont.getData().setScale(0.02f);
        this.titleFont.setUseIntegerPositions(false);
        this.titleFont.getData().setScale(0.02f);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        time += 2 * delta;

        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            main.setScreen(new GameScreen(main));
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
                () -> titleFont.draw(main.spriteBatch, "SNEK", main.viewport.getWorldWidth() / 2 - 2.5f,
                        main.viewport.getWorldHeight() / 2 + 1.5f, 5f, 1, false),
                main.starShader, main.spriteBatch);

        if (timecounter < blinkinterval) {
            instructFont.draw(main.spriteBatch, "Press SPACE", main.viewport.getWorldWidth() / 2 - 2.5f,
                    main.viewport.getWorldHeight() / 2 - 1.5f, 5f, 1, false);
        } else if (timecounter > blinkinterval * 2) {
            timecounter = 0;
        }

        toggleui.update();

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
