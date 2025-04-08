package org.chance_nb.snek;

import com.badlogic.gdx.graphics.Texture;

public class ToggleButton extends Button {
    Texture onTexture;
    Texture offTexture;
    boolean value;

    /**
     * @param onTexture The Texture for the Button being on
     * @param offTexture The Teture for the Button being off
     * @param value The initial value
     */
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
