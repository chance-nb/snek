package com.me.infprojectjava;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main extends com.badlogic.gdx.Game {
    public final float worldWidth = 30;
    public final float worldHeight = 20;

    public AssetManager manager;
    public SpriteBatch spriteBatch;
    public FitViewport viewport;
    public BitmapFont font;

    @Override
    public void create() {
        viewport = new FitViewport(worldWidth, worldHeight);
        spriteBatch = new SpriteBatch();
        manager = new AssetManager();
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale(worldHeight/Gdx.graphics.getHeight());


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Gdx.files.internal("assets.txt").read()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(".png")) {
                    manager.load(line, Texture.class);
                } else if (line.endsWith("sound.mp3")) {
                    manager.load(line, Sound.class);
                } else {
                    throw new Error("Unhandled Asset file found");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        manager.finishLoading();

        this.setScreen(new GameScreen(this));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        font.getData().setScale(worldHeight/Gdx.graphics.getHeight());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        manager.clear();
    }
}
