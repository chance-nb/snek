package org.chance_nb.snek;

import com.badlogic.gdx.math.Vector2;

public class Mushroom extends Consumable {
    private float lastUpdate = 0f;
    private Vector2 moveDir = new Vector2();

    public Mushroom(Main main, float posx, float posy) {
        super(main, main.manager.get("mushroom.png"), posx, posy, 1f, 1f, 0.5f, 0.5f, 0.5f);
    }

    @Override
    protected void onCollision(GameScreen parent, float delta) {
        main.death.play();
        main.setScreen(new GameOverScreen(main, parent.points, parent.time - 5f));
    }

    @Override
    protected void moreUpdates(GameScreen parent, float delta) {
        if (main.state.movingMushroomModifier) {
            if (parent.time - lastUpdate > 0.7f) {
                lastUpdate = parent.time;
                moveDir.set((float) Math.random() * 4f - 2f, (float) Math.random() * 4f - 2f).nor();
            }
            this.pos.add(moveDir.cpy().scl(delta));
        }
    }
}
