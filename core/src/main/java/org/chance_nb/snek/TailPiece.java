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
        if (dontCollideWithHead) {
            this.headCollision = false;
        }
    }

    @Override
    public void update(GameScreen parent, float delta) {
        // move
        Vector2 target;
        if (prevPiece != null) {
            target = prevPiece.pos;
        } else {
            target = parent.head.pos;
        }
        float distance = this.pos.dst(target);
        float interpolationFactor = Math.clamp(distance * (distance - minDistance), 0f, 0.6f);
        this.pos = this.pos.interpolate(target, interpolationFactor*delta*150, Interpolation.pow2Out);

        // collide
        if (headCollision) {
            if (Util.checkCollision(main, this.pos, parent.head.pos, 0.35f)) {
                main.death.play();
                main.setScreen(new MenuScreen(main, parent.points, parent.time - 5f));
            }
        }
    }

    public void updateRecursive(GameScreen parent, float delta) {
        this.update(parent, delta);
        if (prevPiece != null) {
            prevPiece.updateRecursive(parent, delta);
        }
    }

    @Override
    public void wrapDraw(Batch batch) {
        super.wrapDraw(batch);
        if (prevPiece != null) {
            prevPiece.wrapDraw(batch);
        }
    }
}
