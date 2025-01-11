package com.me.infprojectjava;

import static com.me.infprojectjava.GameScreen.speed;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class HeadPiece extends GameObject {
    public HeadPiece(Main main, float posx, float posy) {
        super(main, main.manager.get("head.png"), posx, posy, 1f, 1f, 0.5f, 0.375f);
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.pos.add(speed * delta * MathUtils.cosDeg(45), speed * delta * MathUtils.sinDeg(45));
            this.setRotation(-45);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.pos.add(speed * delta * MathUtils.cosDeg(45), speed * delta * MathUtils.sinDeg(-135));
            this.setRotation(-135);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.pos.add(speed * delta * MathUtils.cosDeg(-135), speed * delta * MathUtils.sinDeg(45));
            this.setRotation(45);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.pos.add(speed * delta * MathUtils.cosDeg(-135), speed * delta * MathUtils.sinDeg(-135));
            this.setRotation(135);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.pos.add(speed * delta, 0f);
            this.setRotation(-90);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.pos.add(-speed * delta, 0f);
            this.setRotation(90);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.pos.add(0f, speed * delta);
            this.setRotation(0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.pos.add(0f, -speed * delta);
            this.setRotation(180);
        }

        if (Gdx.input.isTouched()) {
            Vector2 target = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            main.viewport.unproject(target);
            this.pos = this.pos.interpolate(target, (speed/this.pos.dst(target))*delta, Interpolation.linear);
            this.setRotation(Util.getAngle(this.pos, target)-90);
        }
    }
}
