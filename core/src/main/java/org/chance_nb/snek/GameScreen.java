package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    // FPSLogger fpslog = new FPSLogger();
    boolean isPaused = false;

    int numTailPieces;
    float speed;
    int numApples;
    int numMushrooms;
    Vector3 colorshift;

    static float minDistance = 0.1f;

    final Main main;
    int points;
    TailPiece lastPiece;
    HeadPiece head;

    Array<Apple> apples;
    Array<Mushroom> mushrooms;

    BitmapFont font;

    float time = 5f;

    public GameScreen(Main main) {
        this.main = main;

        // generate font
        main.mainFontParam.size = 30;
        main.mainFontParam.color = Color.BLACK;
        font = main.mainFontGen.generateFont(main.mainFontParam);
        font.setUseIntegerPositions(false);
        font.getData().setScale(0.02f);

        points = 0;
        Vector2 centre = new Vector2(main.worldWidth / 2, main.worldHeight / 2);

        head = new HeadPiece(main, centre.x, centre.y);
        apples = new Array<>();
        mushrooms = new Array<>();

        // Level: normal
        if (!main.state.hardMode) {
            numTailPieces = 3;
            speed = 4f;
            numApples = 9;
            numMushrooms = 3;
            main.state.diagonals = true;
            colorshift = new Vector3(0f, 0f, 0f);
        }
        // Level: hard
        else {
            numTailPieces = 10;
            speed = 6f;
            numApples = 5;
            numMushrooms = 6;
            main.state.diagonals = false;
            colorshift = new Vector3(0.3f, -0.4f, -0.2f); // reddish shift
        }

        // initialize tail pieces
        Vector2 subVec = new Vector2(0f, minDistance); // vector to init distance TailPieces by
        lastPiece = new TailPiece(main, head.pos.cpy().sub(subVec), null, true); // make the first one because it has
                                                                                 // special case of null
        // first make three pieces the head can't collide with for safety
        // always take last pieces pos, copy it and subtract init distance
        for (int i = 0; i < 2; i++) {
            lastPiece = new TailPiece(main, lastPiece.pos.cpy().sub(subVec), lastPiece, true);
        }
        for (int i = 0; i < numTailPieces - 4; i++) { // then do the rest
            lastPiece = new TailPiece(main, lastPiece.pos.cpy().sub(subVec), lastPiece, false);
        }

        // initialize apples
        Vector2 newPos = new Vector2();
        for (int i = 0; i < numApples; i++) {
            newPos.set(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight));
            if (newPos.dst(centre) <= 1) { // make sure it's not near the head
                i--;
                continue;
            }
            apples.add(new Apple(main, newPos));
        }

        if (main.state.mushrooms) { // if we have mushrooms enabled, init those
            for (int i = 0; i < numMushrooms; i++) {
                newPos.set(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight));
                if (newPos.dst(centre) <= 1.5) {
                    i--;
                    continue;
                }
                mushrooms.add(new Mushroom(main, newPos));
            }
        }
    }

    @Override
    public void show() {
    }

    public void render(float delta) {
        time += delta;
        update(delta);
        draw();
    }

    private void update(float delta) {
        if (isPaused) {
            delta = 0;
        }
        // update head
        head.update(this, delta);
        // update tail pieces (recursively)
        lastPiece.updateRecursive(this, delta);
        // update apples
        for (Apple apple : apples) {
            apple.update(this, delta);
        }
        // update mushrooms
        for (Mushroom mushroom : mushrooms) {
            mushroom.update(this, delta);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK); // clear screen
        main.viewport.apply();
        main.spriteBatch.setProjectionMatrix(main.viewport.getCamera().combined);

        main.spriteBatch.begin();
        // opengl stuff to allow shaders on textures w/ transparency
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        // # Background
        main.backgroundShader.bind();
        // set shader params
        main.backgroundShader.setUniformf("u_time", time);
        main.backgroundShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.backgroundShader.setUniformf("u_colorshift", colorshift);
        main.spriteBatch.setShader(main.backgroundShader);
        // draw white pixel texture across the entire screen w/ backgroundShader
        main.spriteBatch.draw(main.white_pixel, 0, 0, main.worldWidth, main.worldHeight);
        main.spriteBatch.setShader(null);

        // # Player
        main.starShader.bind();
        main.starShader.setUniformf("u_time", time);
        main.starShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.starShader.setUniformf("u_density", 1.5f);
        Util.drawWithTexShader(() -> head.wrapDraw(main.spriteBatch), main.starShader, main.spriteBatch);
        Util.drawWithTexShader(() -> lastPiece.wrapDraw(main.spriteBatch), main.starShader, main.spriteBatch);

        // # Apples
        main.rainbowShader.bind();
        main.rainbowShader.setUniformf("u_time", time);
        main.rainbowShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Apple apple : apples) {
            Util.drawWithTexShader(() -> apple.wrapDraw(main.spriteBatch), main.rainbowShader, main.spriteBatch);
        }

        // # Mushrooms
        if (main.state.mushrooms) {
            main.rainbowShader.bind();
            main.rainbowShader.setUniformf("u_time", time);
            main.rainbowShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            for (Mushroom mushroom : mushrooms) {
                Util.drawWithTexShader(() -> mushroom.wrapDraw(main.spriteBatch), main.rainbowShader, main.spriteBatch);
            }
        }

        // # Point Counter
        font.draw(main.spriteBatch, "Points: " + points, 2, 2);

        main.spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        isPaused = true;
    }

    @Override
    public void resume() {
        isPaused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
