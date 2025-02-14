package org.chance_nb.snek;

public class TogglesUI {
    Main main;
    ToggleButton movingCollectableButton;

    public TogglesUI(Main main) {
        this.main = main;
        this.movingCollectableButton = new ToggleButton(main, 1f, 1f, 1f, 1f, main.manager.get("movingAppleButtonOn.png"),
                main.manager.get("movingAppleButtonOff.png"), main.state.movingCollectableModifier);
    }

    public void update() {
        movingCollectableButton.draw(main.spriteBatch);
        movingCollectableButton.update();
        main.state.movingCollectableModifier = movingCollectableButton.value;
    }
}
