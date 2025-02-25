package org.chance_nb.snek;

public class TogglesUI {
    Main main;
    ToggleButton movingAppleButton;
    ToggleButton diagonalsButton;
    ToggleButton mushroomButton;
    ToggleButton movingMushroomButton;

    public TogglesUI(Main main) {
        this.main = main;
        this.movingAppleButton = new ToggleButton(main, 1f, 1f, 1f, 1f, main.manager.get("movingAppleButtonOn.png"),
                main.manager.get("movingAppleButtonOff.png"), main.state.movingAppleModifier);
        this.diagonalsButton = new ToggleButton(main, 2f, 1f, 1f, 1f, main.manager.get("diagonalsButtonOn.png"),
                main.manager.get("diagonalsButtonOff.png"), main.state.diagonals);
        this.mushroomButton = new ToggleButton(main, 3f, 1f, 1f, 1f, main.manager.get("mushroomToggleButtonOn.png"),
                main.manager.get("mushroomToggleButtonOff.png"), main.state.mushrooms);
        this.movingMushroomButton = new ToggleButton(main, 4f, 1f, 1f, 1f,
                main.manager.get("movingMushroomButtonOn.png"), main.manager.get("movingMushroomButtonOff.png"),
                main.state.movingMushroomModifier);
    }

    public void update() {
        movingAppleButton.draw(main.spriteBatch);
        movingAppleButton.update();
        main.state.movingAppleModifier = movingAppleButton.value;
        diagonalsButton.draw(main.spriteBatch);
        diagonalsButton.update();
        main.state.diagonals = diagonalsButton.value;
        mushroomButton.draw(main.spriteBatch);
        mushroomButton.update();
        main.state.mushrooms = mushroomButton.value;
        movingMushroomButton.draw(main.spriteBatch);
        movingMushroomButton.update();
        main.state.movingMushroomModifier = movingMushroomButton.value;
    }
}
