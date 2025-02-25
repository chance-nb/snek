package org.chance_nb.snek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Button extends GameObject {
    Vector2 size;
    boolean currentlyPressed = false;

    public Button(Main main, float posx, float posy, float sizex, float sizey) {
        super(main, main.manager.get("debug.png"), posx, posy, sizex, sizey, 0.5f, 0.5f);
        this.size = new Vector2(sizex, sizey);
    }

    public boolean isPressed() {
        if (Gdx.input.isTouched()) {
            if (!currentlyPressed) {
                Vector2 clickPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                main.viewport.unproject(clickPos);
                clickPos.set(clickPos.x - pos.x, clickPos.y - pos.y);
                // System.out.println(clickPos);
                if ((Math.abs(clickPos.x) < size.x*0.5f) && (Math.abs(clickPos.y) < size.y*0.5f)) {
                    currentlyPressed = true;
                    return true;
                }
            }
        } else if (currentlyPressed) {
            currentlyPressed = false;
        }
        return false;
    }

    public void update(GameScreen screen, float delta) {
    };
}
