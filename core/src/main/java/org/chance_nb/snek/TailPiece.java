package org.chance_nb.snek;

import static org.chance_nb.snek.GameScreen.minDistance;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public class TailPiece extends GameObject {
    TailPiece prevPiece;
    boolean headCollision = true;

    public TailPiece(Main main, Vector2 pos, TailPiece prevPiece) {
        super(main, main.manager.get("wormcircle.png"), pos.x, pos.y, 0.6f, 0.6f, 0.5f, 0.5f);
        this.pos = pos;
        this.prevPiece = prevPiece;
    }

    public TailPiece(Main main, Vector2 pos, TailPiece prevPiece, boolean dontCollideWithHead) {
        super(main, main.manager.get("wormcircle.png"), pos.x, pos.y, 0.6f, 0.6f, 0.5f, 0.5f);
        this.pos = pos;
        this.prevPiece = prevPiece;
        headCollision = !dontCollideWithHead;
    }

    @Override
    public void update(GameScreen parent, float delta) {
        // move
        Vector2 target;
        // set target to head or next piece
        if (prevPiece != null) {
            target = prevPiece.pos;
        } else {
            target = parent.head.pos;
        }
        // get distance between us and target
        float distance = this.pos.dst(target);

        // the factor should be 0 <= α <= 0.6
        // d * (d- d_m) is to ensure the minimun distance
        // and to scale the factor based on the distance
        float interpolationFactor = Math.clamp(distance * (distance - minDistance), 0f, 0.6f);
        // interpolate (why multiply by 150? idk it works)
        this.pos = this.pos.interpolate(target, interpolationFactor * delta * 150, Interpolation.pow2Out);

        // collide with head
        if (headCollision) {
            if (Util.checkCollision(main, this.pos, parent.head.pos, 0.35f)) {
                main.death.play();
                main.setScreen(new MenuScreen(main, parent.points, parent.time - 5f));
            }
        }
    }

    /**
     * recursively update this piece and all pieces before it
     */
    public void updateRecursive(GameScreen parent, float delta) {
        this.update(parent, delta);
        if (prevPiece != null) {
            prevPiece.updateRecursive(parent, delta);
        }
    }

    @Override
    // override to recursively draw every piece
    public void wrapDraw(Batch batch) {
        super.wrapDraw(batch);
        if (prevPiece != null) {
            prevPiece.wrapDraw(batch);
        }
    }
}
