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
        float interpolationFactor = Math.clamp(this.pos.dst(target) * (this.pos.dst(target) - minDistance), 0f, 0.6f);
        this.pos = this.pos.interpolate(target, interpolationFactor, Interpolation.pow2Out);

        // collide
        if (headCollision) {
            if (Util.checkCollision(main, this.pos, parent.head.pos, 0.35f)) {
                main.setScreen(new GameoverScreen(main, parent.points, main.mainFontGen, main.mainFontParam));
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
