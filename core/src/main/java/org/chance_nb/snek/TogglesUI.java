package org.chance_nb.snek;

public class TogglesUI {
    Main main;
    ToggleButton movingAppleButton;

    public TogglesUI(Main main) {
        this.main = main;
        this.movingAppleButton = new ToggleButton(main, 1f, 1f, 1f, 1f, main.manager.get("movingAppleButtonOn.png"),
                main.manager.get("movingAppleButtonOff.png"), main.state.movingAppleModifier);
    }

    public void update() {
        movingAppleButton.draw(main.spriteBatch);
        movingAppleButton.update();
        main.state.movingAppleModifier = movingAppleButton.value;
    }
}
