package com.me.infprojectjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    static int numTailPieces = 5;
    static float minDistance = 0.1f;
    static float speed = 6f;

    static float numApples = 10;
    final Main main;
    final Sound pop;
    int points;
    TailPiece lastPiece;
    HeadPiece head;

    Array<Apple> apples;

    public GameScreen(Main main) {
        this.main = main;
        this.points = 0;
        Vector2 centre = new Vector2(main.worldWidth / 2, main.worldHeight / 2);
        this.head = new HeadPiece(main, centre.x, centre.y);

        this.pop = main.manager.get("pop-sound.mp3");

        this.apples = new Array<>();

        // initialize tail pieces
        for (int i = 0; i < numTailPieces; i++) {
            lastPiece = new TailPiece(main, head.getPos().cpy(), lastPiece);
        }

        // initialize apples
        for (int i = 0; i < numApples; i++) {
            apples.add(new Apple(main, MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight)));
        }
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
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            head.getPos().add(speed * delta, 0f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            head.getPos().add(-speed * delta, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            head.getPos().add(0f, speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            head.getPos().add(0f, -speed * delta);
        }

        if (Gdx.input.isTouched()) {
            head.getPos().set(Gdx.input.getX(), Gdx.input.getY());
            main.viewport.unproject(head.getPos());
        }


    }

    private void logic(float delta) {
        // snake tail stuff
        lastPiece.update(this);
        // apples
        for (Apple apple : apples) {
            apple.update(this);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        main.viewport.apply();
        main.spriteBatch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.spriteBatch.begin();

        lastPiece.wrapDraw(main.spriteBatch);
        head.wrapDraw(main.spriteBatch);

        for (Apple apple : apples) {
            apple.draw(main.spriteBatch);
        }

        main.font.draw(main.spriteBatch, "Points: " + points, 2, 2);

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
