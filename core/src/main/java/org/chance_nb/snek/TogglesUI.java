package org.chance_nb.snek;

import org.chance_nb.snek.spaceinvaders.SpaceInvadersGameScreen;

public class TogglesUI {
    Main main;
    ToggleButton movingAppleButton;
    ToggleButton mushroomButton;
    ToggleButton movingMushroomButton;
    ToggleButton difficultyButton;
    org.chance_nb.snek.Button spaceInvadersButton;
    // ToggleButton diagonalsButton;

    public TogglesUI(Main main) {
        // create all the buttons
        this.main = main;
        this.movingAppleButton = new ToggleButton(main, 1f, 1f, 1f, 1f, main.manager.get("movingAppleButtonOn.png"),
                main.manager.get("movingAppleButtonOff.png"), main.state.movingAppleModifier);
        this.mushroomButton = new ToggleButton(main, 2f, 1f, 1f, 1f, main.manager.get("mushroomToggleButtonOn.png"),
                main.manager.get("mushroomToggleButtonOff.png"), main.state.mushrooms);
        this.movingMushroomButton = new ToggleButton(main, 3f, 1f, 1f, 1f,
                main.manager.get("movingMushroomButtonOn.png"), main.manager.get("movingMushroomButtonOff.png"),
                main.state.movingMushroomModifier);

        // this.diagonalsButton = new ToggleButton(main, 2f, 1f, 1f, 1f,
        // main.manager.get("diagonalsButtonOn.png"),
        // main.manager.get("diagonalsButtonOff.png"),
        // main.state.diagonals);

        this.difficultyButton = new ToggleButton(main, main.worldWidth / 2, 1f, 2f, 1f,
                main.manager.get("hardButton.png"),
                main.manager.get("easyButton.png"), main.state.hardMode);

        spaceInvadersButton = new Button(main, 14f, 9f, 1f, 1f);

    }

    public void update() {
        // update (=check) all the buttons and set the global state toggles to their
        // values
        movingAppleButton.draw(main.spriteBatch);
        movingAppleButton.update();
        main.state.movingAppleModifier = movingAppleButton.value;
        mushroomButton.draw(main.spriteBatch);
        mushroomButton.update();
        main.state.mushrooms = mushroomButton.value;
        movingMushroomButton.draw(main.spriteBatch);
        movingMushroomButton.update();
        main.state.movingMushroomModifier = movingMushroomButton.value;
        // diagonalsButton.draw(main.spriteBatch);
        // diagonalsButton.update();
        // main.state.diagonals = diagonalsButton.value;

        difficultyButton.draw(main.spriteBatch);
        difficultyButton.update();
        main.state.hardMode = difficultyButton.value;

        spaceInvadersButton.draw(main.spriteBatch);
        if (spaceInvadersButton.isPressed()) {
            main.setScreen(new SpaceInvadersGameScreen(main));
        }
    }
}
