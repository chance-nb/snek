package org.chance_nb.snek;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Collectable extends GameObject {
    private float lastUpdate = 0f;
    private Vector2 moveDir = new Vector2();

    public Collectable(Main main, float posx, float posy) {
        super(main, main.manager.get("coin.png"), posx, posy, 1f, 1f, 0.5f, 0.5f);
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if (Util.checkCollision(main, this.pos, parent.head.pos, 0.65f)) {
            this.setPos(MathUtils.random(main.worldWidth), MathUtils.random(main.worldHeight));
            parent.lastPiece = new TailPiece(main, parent.lastPiece.pos.cpy(), parent.lastPiece);
            main.collect.play();
            parent.points += 1;
        }

        TailPiece closestTailPiece = findClosestCollidingTailPiece(parent.lastPiece, null, 100f);
        if (closestTailPiece != null) {
            Vector2 target = Util.wrapClampVec2World(main, closestTailPiece.pos);
            Vector2 wrappos = Util.wrapClampVec2World(main, this.pos);
            this.pos = wrappos.interpolate(target, (-0.6f - wrappos.dst(target)) / 30, Interpolation.linear);
        }

        if (main.state.movingCollectableModifier) {
            if (parent.time - lastUpdate > 0.7f) {
                lastUpdate = parent.time;
                moveDir.set((float) Math.random() * 4f - 2f, (float) Math.random() * 4f - 2f).nor();
            }
            this.pos.add(moveDir.cpy().scl(delta));
        }
    }

    private TailPiece findClosestCollidingTailPiece(TailPiece currTailPiece, TailPiece closest, Float closestDist) {
        if (Util.checkCollision(main, currTailPiece.pos, this.pos, 0.6f)) {
            if (this.pos.dst(currTailPiece.pos) < closestDist) {
                closest = currTailPiece;
                closestDist = this.pos.dst(currTailPiece.pos);
            }
        }
        if (currTailPiece.prevPiece == null) {
            return closest;
        }
        return findClosestCollidingTailPiece(currTailPiece.prevPiece, closest, closestDist);
    }
}
