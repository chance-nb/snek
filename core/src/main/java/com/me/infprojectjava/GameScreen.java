package com.me.infprojectjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    static int numTailPieces = 50;
    static float minDistance = 0.1f;
    static float speed = 6f;

    static float numApples = 10;

    final Main main;
    final Texture wormcircle;
    final Texture apple;

    final Sound pop;

    final Vector2 headPos;
    Array<Vector2> tailPieces;

    Array<Vector2> apples;

    public GameScreen(Main main) {
        this.main = main;
        this.wormcircle = main.manager.get("wormcircle.png");
        this.apple = main.manager.get("apple.png");
        Vector2 centre = new Vector2(main.worldWidth / 2, main.worldHeight / 2);
        this.headPos = centre.cpy();
        this.pop = main.manager.get("pop-sound.mp3");

        this.tailPieces = new Array<>();
        this.apples = new Array<>();

        // initialize tail pieces
        for (int i = 0; i < numTailPieces; i++) {
            tailPieces.add(centre.cpy());
        }

        // initialize apples
        for (int i = 0; i < numApples; i++) {
            apples.add(new Vector2(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight)));
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
            headPos.add(speed * delta, 0f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            headPos.add(-speed * delta, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            headPos.add(0f, speed * delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            headPos.add(0f, -speed * delta);
        }

        if (Gdx.input.isTouched()) {
            headPos.set(Gdx.input.getX(), Gdx.input.getY());
            main.viewport.unproject(headPos);
        }


    }

    private void logic(float delta) {
        // snake tail stuff
        for (int i = 0; i < tailPieces.size; i++) {
            Vector2 current = tailPieces.get(i);
            Vector2 target = (i == 0) ? headPos.cpy() : tailPieces.get(i - 1).cpy();

            float interpolationFactor = current.dst(target);
            interpolationFactor *=  current.dst(target)-minDistance;
            interpolationFactor = Math.clamp(interpolationFactor, 0f, 0.5f);

            tailPieces.set(i, tailPieces.get(i).interpolate(
                target,
                interpolationFactor, Interpolation.linear));
        }

        // apples
        for (int i = 0; i < apples.size; i++) {
            if (Math.abs(apples.get(i).cpy().sub(headPos).len()) < 0.8) {
                apples.set(i, new Vector2(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight)));
                tailPieces.add(tailPieces.get(tailPieces.size-1).cpy());
                pop.play();
            }
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        main.viewport.apply();
        main.spriteBatch.setProjectionMatrix(main.viewport.getCamera().combined);
        main.spriteBatch.begin();

        tailPieces.reverse();
        for (Vector2 pos : tailPieces) {
            main.spriteBatch.draw(wormcircle, pos.x, pos.y, 0.9f, 0.9f);
        }
        tailPieces.reverse();

        main.spriteBatch.draw(wormcircle, headPos.x, headPos.y, 1, 1);


        for (Vector2 pos : apples) {
            main.spriteBatch.draw(apple, pos.x, pos.y, 1, 1);
        }

        main.font.draw(main.spriteBatch, "Points: "+(tailPieces.size-numTailPieces), 2, 2);

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
