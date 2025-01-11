package com.me.infprojectjava;

import com.badlogic.gdx.math.MathUtils;

public class Apple extends GameObject {
    public Apple(Main main, float posx, float posy) {
        super(main, main.manager.get("apple.png"), posx, posy, 1f, 1f, 0.5f, 0.5f);
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if (Util.checkCollision(main, this.pos, parent.head.pos,0.65f)) {
            this.setPos(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight));
            parent.lastPiece = new TailPiece(main , parent.lastPiece.pos.cpy(), parent.lastPiece);
            parent.pop.play();
            parent.points += 1;
        }
    }
}
