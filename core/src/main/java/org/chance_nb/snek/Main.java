package org.chance_nb.snek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main extends com.badlogic.gdx.Game {
    public final float worldWidth = 15;
    public final float worldHeight = 10;

    public AssetManager manager;
    public SpriteBatch spriteBatch;
    public FitViewport viewport;
    public GlobalState state;

    public FreeTypeFontGenerator mainFontGen;
    public FreeTypeFontGenerator.FreeTypeFontParameter mainFontParam;

    public Screen currentScreen;

    public Texture white_pixel;

    public Sound collect;
    public Sound death;

    public ShaderProgram backgroundShader;
    public ShaderProgram rainbowShader;
    public ShaderProgram starShader;

    @Override
    public void create() {
        viewport = new FitViewport(worldWidth, worldHeight);
        spriteBatch = new SpriteBatch();
        manager = new AssetManager();
        state = new GlobalState();

        mainFontGen = new FreeTypeFontGenerator(Gdx.files.internal("ZenDots-Regular.ttf"));
        mainFontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // autoload all the .png, .mp3 and .wav assets
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(Gdx.files.internal("assets.txt").read()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(".png")) {
                    manager.load(line, Texture.class);
                } else if (line.endsWith(".mp3")) {
                    manager.load(line, Sound.class);
                } else if (line.endsWith(".wav")) {
                    manager.load(line, Sound.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        manager.finishLoading();

        this.white_pixel = manager.get("white_pixel.png");
        this.collect = manager.get("collect.wav");
        this.death = manager.get("death.wav");

        // compile and load shaders
        ShaderProgram.pedantic = false;
        backgroundShader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vert"),
                Gdx.files.internal("shaders/background.frag"));
        if (!backgroundShader.isCompiled()) {
            throw new IllegalArgumentException("Error compiling shader: " + backgroundShader.getLog());
        }

        rainbowShader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vert"),
                Gdx.files.internal("shaders/rainbow.glsl"));
        if (!rainbowShader.isCompiled()) {
            throw new IllegalArgumentException("Error compiling shader: " + rainbowShader.getLog());
        }

        starShader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vert"),
                Gdx.files.internal("shaders/stars.glsl"));
        if (!starShader.isCompiled()) {
            throw new IllegalArgumentException("Error compiling shader: " + starShader.getLog());
        }

        // go to menuscreen
        currentScreen = new MenuScreen(this);
        this.setScreen(currentScreen);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        manager.clear();
        mainFontGen.dispose();
    }
}
