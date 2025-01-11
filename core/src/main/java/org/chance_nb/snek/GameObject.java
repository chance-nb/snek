package org.chance_nb.snek;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class GameObject extends Sprite {
    Main main;
    Vector2 pos = new Vector2();

    public GameObject(Main main, Texture texture, float posx, float posy, float sizex, float sizey, float originx, float originy) {
        super(texture);
        this.main = main;
        this.setSize(sizex, sizey);
        this.setOrigin(sizex * originx, sizey * originy);
        this.setPos(posx, posy);
    }

    public void update(GameScreen parent, float delta) {}

    public void setPos(float x, float y) {
        this.pos.set(x, y);
    }

    @Override
    public void draw(Batch batch) {
        this.setOriginBasedPosition(pos.x, pos.y);
        super.draw(batch);
    }

    public void draw(Batch batch, float x, float y) {
        this.setOriginBasedPosition(x, y);
        super.draw(batch);
    }

    public void wrapDraw(Batch batch) {
        Vector2 drawPos = Util.wrapClampVec2World(main, pos);
        this.draw(batch, drawPos.x, drawPos.y);
    }
}
