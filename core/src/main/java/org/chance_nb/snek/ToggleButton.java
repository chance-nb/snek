package org.chance_nb.snek;

import com.badlogic.gdx.graphics.Texture;

public class ToggleButton extends Button {
    Texture onTexture;
    Texture offTexture;
    boolean value = false;

    public ToggleButton(Main main, float posx, float posy, float sizex, float sizey, Texture onTexture,
            Texture offTexture, boolean value) {
        super(main, posx, posy, sizex, sizey);
        this.value = value;
        this.onTexture = onTexture;
        this.offTexture = offTexture;
        toggleTexture();
    }

    private void toggleTexture() {
        if (value) {
            this.setTexture(onTexture);
        } else {
            this.setTexture(offTexture);
        }
    }

    public void update() {
        if (super.isPressed()) {
            value = !value;
            toggleTexture();
        }
    }
}
