package com.me.infprojectjava;

import com.badlogic.gdx.graphics.Texture;

public class HeadPiece extends GameObject {
    public HeadPiece(Main main, float posx, float posy) {
        super(main, main.manager.get("wormcircle.png"), posx, posy);
        this.setSize(1f, 1f);
    }
}
