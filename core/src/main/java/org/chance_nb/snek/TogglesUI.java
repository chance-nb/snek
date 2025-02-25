package org.chance_nb.snek;

public class TogglesUI {
    Main main;
    ToggleButton movingAppleButton;
    ToggleButton diagonalsButton;

    public TogglesUI(Main main) {
        this.main = main;
        this.movingAppleButton = new ToggleButton(main, 1f, 1f, 1f, 1f, main.manager.get("movingAppleButtonOn.png"),
                main.manager.get("movingAppleButtonOff.png"), main.state.movingAppleModifier);
        this.diagonalsButton = new ToggleButton(main, 2f, 1f, 1f, 1f, main.manager.get("diagonalsButtonOn.png"),
                main.manager.get("diagonalsButtonOff.png"), main.state.diagonals);
    }

    public void update() {
        movingAppleButton.draw(main.spriteBatch);
        movingAppleButton.update();
        main.state.movingAppleModifier = movingAppleButton.value;
        diagonalsButton.draw(main.spriteBatch);
        diagonalsButton.update();
        main.state.diagonals = diagonalsButton.value;
    }
}
