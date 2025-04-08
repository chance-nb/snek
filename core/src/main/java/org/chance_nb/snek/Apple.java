package org.chance_nb.snek;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Apple extends Consumable {
    private float lastUpdate = 0f;
    private float nextMoveDirChange = 0.7f;
    private Vector2 moveDir = new Vector2();

    public Apple(Main main, Vector2 pos) {
        super(main, main.manager.get("apple.png"), pos, 1f, 1f, 0.6f, 0.5f, 0.5f);
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if (main.state.movingAppleModifier) { // when apples should move
            if (parent.time - lastUpdate > nextMoveDirChange) { // if we haven't changed direction in a bit
                lastUpdate = parent.time; // reset timer
                nextMoveDirChange = (float) Math.random() + 0.3f;
                // set new random direction
                moveDir.set((float) Math.random() * 4f - 2f, (float) Math.random() * 4f - 2f);
            }
            this.pos.add(moveDir.cpy().scl(delta));
        }
        super.update(parent, delta); // pass on to Consumable class update func
    }

    @Override
    protected void onHeadCollision(GameScreen parent, float delta) {
        // rerandomize position
        this.setPos(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight));
        // add a new TailPiece
        parent.lastPiece = new TailPiece(main, parent.lastPiece.pos.cpy(), parent.lastPiece);
        // play pickup sound
        main.collect.play();
        parent.points += 1;
    }
}
