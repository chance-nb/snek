package org.chance_nb.snek;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Apple extends Consumable {
    private float lastUpdate = 0f;
    private Vector2 moveDir = new Vector2();

    public Apple(Main main, float posx, float posy) {
        super(main, main.manager.get("apple.png"), posx, posy, 1f, 1f, 0.65f, 0.5f, 0.5f);
    }

    @Override
    public void moreUpdates(GameScreen parent, float delta) {
        if (main.state.movingAppleModifier) {
            if (parent.time - lastUpdate > 0.7f) {
                lastUpdate = parent.time;
                moveDir.set((float) Math.random() * 4f - 2f, (float) Math.random() * 4f - 2f).nor();
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
