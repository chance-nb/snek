package org.chance_nb.snek;

public class Mushroom extends Consumable {

    public Mushroom(Main main, float posx, float posy) {
        super(main, main.manager.get("mushroom.png"), posx, posy, 1f, 1f, 0.5f, 0.5f, 0.5f);
    }

    @Override
    protected void onCollision(GameScreen parent, float delta) {
        main.death.play();
        main.setScreen(new GameOverScreen(main, parent.points, parent.time - 5f));
    }

}
