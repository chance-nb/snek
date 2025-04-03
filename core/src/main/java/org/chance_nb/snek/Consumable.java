package org.chance_nb.snek;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class Consumable extends GameObject {
    private float collisionRadius;

    public Consumable(Main main, Texture texture, float posx, float posy, float sizex, float sizey,
            float collisionRadius, float originx, float originy) {
        super(main, texture, posx, posy, sizex, sizey, originx, originy);
        this.collisionRadius = collisionRadius;
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if (Util.checkCollision(main, this.pos, parent.head.pos, this.collisionRadius)) {
            onCollision(parent, delta);
        }

        TailPiece closestTailPiece = findClosestCollidingTailPiece(parent.lastPiece, null, 100f);
        if (closestTailPiece != null) {
            Vector2 target = Util.wrapClampVec2World(main, closestTailPiece.pos);
            Vector2 wrappos = Util.wrapClampVec2World(main, this.pos);
            this.pos = wrappos.interpolate(target, (-0.6f - wrappos.dst(target)) / 30, Interpolation.linear);
        }

        moreUpdates(parent, delta);
    }

    protected void moreUpdates(GameScreen parent, float delta) {
    }

    protected void onCollision(GameScreen parent, float delta) {
    } // to Override

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
