package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
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
    final Sound pop;
    int points;
    TailPiece lastPiece;
    HeadPiece head;

    Array<Apple> apples;

    BitmapFont font;

    ShaderProgram shader;
    float time;

    public GameScreen(Main main) {
        this.main = main;
        main.mainFontParam.size = 30;
        this.font = main.mainFontGen.generateFont(main.mainFontParam);
        this.font.setUseIntegerPositions(false);
        // this.font.getData().setScale(main.worldHeight / Gdx.graphics.getHeight());
        this.font.getData().setScale(0.02f);
        this.points = 0;
        Vector2 centre = new Vector2(main.worldWidth / 2, main.worldHeight / 2);
        this.head = new HeadPiece(main, centre.x, centre.y);

        this.pop = main.manager.get("pop-sound.mp3");

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
        ShaderProgram.pedantic = false;
        shader = new ShaderProgram(Gdx.files.internal("shaders/background.vert"), Gdx.files.internal("shaders/background.frag"));
        if (!shader.isCompiled()) {
            throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
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
        shader.bind();
        shader.setUniformf("u_time", time);
        shader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Apply the shader
        main.spriteBatch.setShader(shader);


        main.spriteBatch.setShader(null);

        for (Apple apple : apples) {
            apple.draw(main.spriteBatch);
        }

        lastPiece.wrapDraw(main.spriteBatch);
        head.wrapDraw(main.spriteBatch);

        font.draw(main.spriteBatch, "Points: " + points, 2, 2);
        main.spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    // TODO IDEA tail drags apples

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
