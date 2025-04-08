package org.chance_nb.snek;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public abstract class Consumable extends GameObject {
    private float collisionRadius;

    public Consumable(Main main, Texture texture, Vector2 pos, float sizex, float sizey,
            float collisionRadius, float originx, float originy) {
        super(main, texture, pos.x, pos.y, sizex, sizey, originx, originy);
        this.collisionRadius = collisionRadius;
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if (Util.checkCollision(main, this.pos, parent.head.pos, this.collisionRadius)) {
            onHeadCollision(parent, delta);
        }

        // get pushed by tail pieces
        TailPiece closestTailPiece = findClosestCollidingTailPiece(parent.lastPiece, null, 100f);
        if (closestTailPiece != null) {
            // get pos of the closest piece
            Vector2 target = Util.wrapClampVec2World(main, closestTailPiece.pos);
            // wrap our pos back on the normal screen
            Vector2 wrappos = Util.wrapClampVec2World(main, this.pos);
            // get pushed (why divide by 30? because it works)
            this.pos = wrappos.interpolate(target, (-collisionRadius - wrappos.dst(target)) / 30, Interpolation.linear);
        }
    }

    protected abstract void onHeadCollision(GameScreen parent, float delta);

    private TailPiece findClosestCollidingTailPiece(TailPiece currTailPiece, TailPiece closest, Float closestDist) {
        // if we're colliding with the current piece
        if (Util.checkCollision(main, currTailPiece.pos, this.pos, collisionRadius)) {
            // if the current piece is closer than any other one we've encountered
            if (this.pos.dst(currTailPiece.pos) < closestDist) {
                closest = currTailPiece;
                closestDist = this.pos.dst(currTailPiece.pos);
            }
        }
        // we've gone through all of them, return
        if (currTailPiece.prevPiece == null) {
            return closest;
        }
        // still more, continue recursively going through them
        return findClosestCollidingTailPiece(currTailPiece.prevPiece, closest, closestDist);
    }
}
