package org.chance_nb.snek.spaceinvaders;

import org.chance_nb.snek.Main;
import org.chance_nb.snek.MenuScreen;

import com.badlogic.gdx.math.Vector2;

public class Enemy extends SpaceGameObject {
    Vector2 moveVec;

    public Enemy(Main main, float posx, float posy, float speed) {
        super(main, main.manager.get("mushroom.png"), posx, posy, 1f, 1f, 0.5f, 0.5f);
        moveVec = new Vector2(0, -speed);
    }

    @Override
    public void update(SpaceInvadersGameScreen parent, float delta) {
        this.pos = this.pos.add(moveVec.cpy().scl(delta));

        if (this.pos.y < SpaceInvadersGameScreen.playerHeight) {
            main.setScreen(new MenuScreen(main, parent.points, parent.time));
        }
    }

}
