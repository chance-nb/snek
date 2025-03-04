package org.chance_nb.snek;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Apple extends Consumable {
    private float lastUpdate = 0f;
    private float nextMoveDirChange = 0.7f;
    private Vector2 moveDir = new Vector2();

    public Apple(Main main, float posx, float posy) {
        super(main, main.manager.get("apple.png"), posx, posy, 1f, 1f, 0.65f, 0.5f, 0.5f);
    }

    @Override
    public void moreUpdates(GameScreen parent, float delta) {
        if (main.state.movingAppleModifier) { // when apples should move
            if (parent.time - lastUpdate > nextMoveDirChange) { // if we haven't changed direction in a bit
                lastUpdate = parent.time; // reset timer
                nextMoveDirChange = (float) Math.random() + 0.3f;
                moveDir.set((float) Math.random() * 4f - 2f, (float) Math.random() * 4f - 2f); // set new random move
                                                                                               // direction
            }
            this.pos.add(moveDir.cpy().scl(delta));
        }
    }

    @Override
    protected void onCollision(GameScreen parent, float delta) {
        this.setPos(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight));
        parent.lastPiece = new TailPiece(main, parent.lastPiece.pos.cpy(), parent.lastPiece);
        main.collect.play();
        parent.points += 1;
    }
}
