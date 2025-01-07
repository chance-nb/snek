package com.me.infprojectjava;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    final Main main;
    final Texture wormcircle;


    public GameScreen(Main main) {
        this.main = main;
        this.wormcircle = main.manager.get("wormcircle.png");
    }

    @Override
    public void show() {

    }

    public void render(float delta) {
        input(delta);
        logic(delta);
        draw();
    }

    private void input(float delta) {

//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//            bucketSprite.translateX(speed * delta);
//        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            bucketSprite.translateX(-speed * delta);
//        }
    }

    private void logic(float delta) {

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        main.viewport.apply();
        main.spriteBatch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.spriteBatch.begin();

        main.spriteBatch.draw(wormcircle, 2, 2, 1, 1);

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
