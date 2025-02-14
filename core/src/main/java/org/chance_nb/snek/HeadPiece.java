package org.chance_nb.snek;

import static org.chance_nb.snek.GameScreen.speed;
import static org.chance_nb.snek.Util.Direction;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class HeadPiece extends GameObject {
    Direction direction = null;

    public HeadPiece(Main main, float posx, float posy) {
        super(main, main.manager.get("head.png"), posx, posy, 1f, 1f, 0.5f, 0.375f);
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.UP)) || Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            this.direction = Direction.UR;
            this.setRotation(-45);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.direction = Direction.DR;
            this.setRotation(-135);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.direction = Direction.UL;
            this.setRotation(45);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.direction = Direction.DL;
            this.setRotation(135);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.direction = Direction.R;
            this.setRotation(-90);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.direction = Direction.L;
            this.setRotation(90);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.direction = Direction.U;
            this.setRotation(0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.direction = Direction.D;
            this.setRotation(180);
        }

        switch (this.direction) {
            case Direction.UR:
                this.pos.add(speed * delta * MathUtils.cosDeg(45), speed * delta * MathUtils.sinDeg(45));
                break;
            case Direction.DR:
                this.pos.add(speed * delta * MathUtils.cosDeg(45), speed * delta * MathUtils.sinDeg(-135));
                break;
            case Direction.UL:
                this.pos.add(speed * delta * MathUtils.cosDeg(-135), speed * delta * MathUtils.sinDeg(45));
                break;
            case Direction.DL:
                this.pos.add(speed * delta * MathUtils.cosDeg(-135), speed * delta * MathUtils.sinDeg(-135));
                break;
            case Direction.R:
                this.pos.add(speed * delta, 0f);
                break;
            case Direction.L:
                this.pos.add(-speed * delta, 0f);
                break;
            case Direction.U:
                this.pos.add(0f, speed * delta);
                break;
            case Direction.D:
                this.pos.add(0f, -speed * delta);
                break;
            case null, default:
                break;
        }

        if (Gdx.input.isTouched()) {
            Vector2 target = new Vector2(Gdx.input.getX(), Gdx.input.getY());
            main.viewport.unproject(target);
            this.pos = this.pos.interpolate(target, (speed/this.pos.dst(target))*delta, Interpolation.linear);
            this.setRotation(Util.getAngle(this.pos, target)-90);
        }
    }
}
