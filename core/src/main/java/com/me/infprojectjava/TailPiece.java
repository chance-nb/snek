package com.me.infprojectjava;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

import static com.me.infprojectjava.GameScreen.minDistance;

public class TailPiece extends GameObject {
    TailPiece prevPiece;
    public TailPiece(Main main, Vector2 pos, TailPiece prevPiece) {
        super(main, main.manager.get("wormcircle.png"));
        this.setSize(0.8f, 0.8f);
        this.setPos(pos);
        this.prevPiece = prevPiece;
    }

    @Override
    public void update(GameScreen parent) {
        Vector2 target;
        if (prevPiece != null) {
            target = prevPiece.getPos();
        } else {
            target = parent.head.getPos();
        }

        float interpolationFactor = Math.clamp(this.getPos().dst(target) * (this.getPos().dst(target) - minDistance), 0f, 0.6f);

        this.setPos(this.getPos().interpolate(target, interpolationFactor, Interpolation.linear));

        if (prevPiece != null) {
            prevPiece.update(parent);
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
