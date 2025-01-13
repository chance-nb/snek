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

    boolean inputReleased = false;

    public GameoverScreen(Main main, int points, FreeTypeFontGenerator fontGen, FreeTypeFontGenerator.FreeTypeFontParameter fontParam) {
        this.main = main;
        this.points = points;
        fontParam.size = 30;
        fontParam.color = Color.PURPLE;
        this.font = fontGen.generateFont(fontParam);
        this.font.setUseIntegerPositions(false);
        this.font.getData().setScale(main.worldHeight / Gdx.graphics.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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

        font.draw(main.spriteBatch, "Game Over!", main.viewport.getWorldWidth()/2-2.5f, main.viewport.getWorldHeight()/2+1, 5f, 1,false);
        font.draw(main.spriteBatch, "Points: "+points, main.viewport.getWorldWidth()/2-2.5f, main.viewport.getWorldHeight()/2, 5f, 1, false);
        if (timecounter < blinkinterval) {
            font.draw(main.spriteBatch, "CLICK to restart", main.viewport.getWorldWidth()/2-2.5f, main.viewport.getWorldHeight()/2-1, 5f, 1, false);
        } else if (timecounter > blinkinterval*2) {
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
