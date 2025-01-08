package com.me.infprojectjava;

import com.badlogic.gdx.math.MathUtils;

public class Apple extends GameObject {
    public Apple(Main main, float posx, float posy) {
        super(main, main.manager.get("apple.png"), posx, posy);
        this.setSize(1, 1);
    }

    @Override
    public void update(GameScreen parent) {
        if (Math.abs(this.getPos().cpy().sub(Util.wrapClampVec2World(parent.head.getPos(), main)).len()) < 1) {
            this.setPos(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight));
            parent.lastPiece = new TailPiece(main , parent.lastPiece.getPos().cpy(), parent.lastPiece);
            parent.pop.play();
            parent.points += 1;
        }
    }
}
