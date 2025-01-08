package com.me.infprojectjava;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class GameObject extends Sprite {
    Main main;
    private Vector2 pos = new Vector2();

    public GameObject(Main main, Texture texture) {
        super(texture);
        this.main = main;
    }

    public GameObject(Main main, Texture texture, float posx, float posy) {
        super(texture);
        this.main = main;
        this.setPos(posx, posy);
    }

    public GameObject(Main main) {
        this.main = main;
    }

    public void update(GameScreen parent) {
    }

    public Vector2 getPos() {
        return this.pos;
    }

    public void setPos(float x, float y) {
        this.pos.set(x, y);
        super.setPosition(x, y);
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
        super.setPosition(pos.x, pos.y);
    }

    public void wrapDraw(Batch batch) {
        Vector2 drawPos = Util.wrapClampVec2World(pos, main);
        batch.draw(this.getTexture(), drawPos.x+(1-this.getWidth()/2), drawPos.y+(1-this.getHeight()/2), this.getWidth(), this.getHeight());
    }
}
