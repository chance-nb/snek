package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {
    static int numTailPieces = 4;
    static float minDistance = 0.1f;
    static float speed = 5f;

    static float numApples = 4;
    final Main main;
    int points;
    TailPiece lastPiece;
    HeadPiece head;

    Array<Apple> apples;

    BitmapFont font;

    float time = 5f;

    public GameScreen(Main main) {
        this.main = main;
        main.mainFontParam.size = 30;
        main.mainFontParam.color = Color.BLACK;
        this.font = main.mainFontGen.generateFont(main.mainFontParam);
        this.font.setUseIntegerPositions(false);
        // this.font.getData().setScale(main.worldHeight / Gdx.graphics.getHeight());
        this.font.getData().setScale(0.02f);
        this.points = 0;
        Vector2 centre = new Vector2(main.worldWidth / 2, main.worldHeight / 2);
        this.head = new HeadPiece(main, centre.x, centre.y);
        this.apples = new Array<>();

        // initialize tail pieces
        Vector2 subVec = new Vector2(0f, minDistance);
        lastPiece = new TailPiece(main, head.pos.cpy().sub(subVec), null, true);
        for (int i = 0; i < numTailPieces - 1; i++) {
            lastPiece = new TailPiece(main, lastPiece.pos.cpy().sub(subVec), lastPiece, true);
        }

        // initialize apples
        for (int i = 0; i < numApples; i++) {
            apples.add(new Apple(main, MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight)));
        }

        // Load the shader


        // backgroundShader = starShader;
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
        head.update(this, delta);
        // update tail pieces (recursively)
        lastPiece.updateRecursive(this, delta);
        // apples
        for (Apple apple : apples) {
            apple.update(this, delta);
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        main.viewport.apply();
        main.spriteBatch.setProjectionMatrix(main.viewport.getCamera().combined);

        main.spriteBatch.begin();
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        main.backgroundShader.bind();
        main.backgroundShader.setUniformf("u_time", time);
        main.backgroundShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.spriteBatch.setShader(main.backgroundShader);
        main.spriteBatch.draw(main.white_pixel, 0, 0, main.worldWidth, main.worldHeight);
        main.spriteBatch.setShader(null);

        main.starShader.bind();
        main.starShader.setUniformf("u_time", time);
        main.starShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        main.starShader.setUniformf("u_density", 1.5f);
        Util.drawWithTexShader(() -> head.wrapDraw(main.spriteBatch), main.starShader, main.spriteBatch);
        Util.drawWithTexShader(() -> lastPiece.wrapDraw(main.spriteBatch), main.starShader, main.spriteBatch);

        font.draw(main.spriteBatch, "Points: " + points, 2, 2);
        for (Apple apple : apples) {
            apple.draw(main.spriteBatch);
        }
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
