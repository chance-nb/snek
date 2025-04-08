package org.chance_nb.snek.spaceinvaders;

import org.chance_nb.snek.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Player extends SpaceGameObject {
    static float speed = 3f;
    float timeSinceLastShot = 0f;

    public Player(Main main, float posx, float posy) {
        super(main, main.manager.get("head.png"), posx, posy, 1f, 1f, 0.5f, 0.375f);
    }

    @Override
    public void update(SpaceInvadersGameScreen parent, float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.pos.add(-speed * delta, 0f);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.pos.add(speed * delta, 0f);
        }

        if (timeSinceLastShot > 1/parent.difficulty) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                parent.bullets.add(new Bullet(main, this.pos.x, this.pos.y));
                timeSinceLastShot = 0f;
            }
        }
        timeSinceLastShot += delta;
    }

}
