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
import com.badlogic.gdx.utils.viewport.FitViewport;


public class Main extends com.badlogic.gdx.Game {
    public final float worldWidth = 15;
    public final float worldHeight = 10;

    public AssetManager manager;
    public SpriteBatch spriteBatch;
    public FitViewport viewport;

    public FreeTypeFontGenerator mainFontGen;
    public FreeTypeFontGenerator.FreeTypeFontParameter mainFontParam;

    public Screen currentScreen;

    @Override
    public void create() {
        viewport = new FitViewport(worldWidth, worldHeight);
        spriteBatch = new SpriteBatch();
        manager = new AssetManager();

        mainFontGen = new FreeTypeFontGenerator(Gdx.files.internal("Jersey10-Regular.ttf"));
        mainFontParam = new FreeTypeFontGenerator.FreeTypeFontParameter();


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Gdx.files.internal("assets.txt").read()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(".png")) {
                    manager.load(line, Texture.class);
                } else if (line.endsWith("sound.mp3")) {
                    manager.load(line, Sound.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        manager.finishLoading();

        currentScreen = new GameScreen(this);
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
