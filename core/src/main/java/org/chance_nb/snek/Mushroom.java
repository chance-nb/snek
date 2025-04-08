package org.chance_nb.snek;

import com.badlogic.gdx.math.Vector2;

public class Mushroom extends Consumable {
    private float lastUpdate = 0f;
    private Vector2 moveDir = new Vector2();

    public Mushroom(Main main, Vector2 pos) {
        super(main, main.manager.get("mushroom.png"), pos, 1f, 1f, 0.5f, 0.5f, 0.5f);
    }

    @Override
    protected void onHeadCollision(GameScreen parent, float delta) {
        main.death.play();
        main.setScreen(new MenuScreen(main, parent.points, parent.time - 5f));
    }

    @Override
    public void update(GameScreen parent, float delta) {
        if (main.state.movingMushroomModifier) { // if mushrooms should move
            if (parent.time - lastUpdate > 0.7f) { // if it's been 0.7s since we changed direction
                lastUpdate = parent.time;
                // randomly change direction
                moveDir.set((float) Math.random() * 4f - 2f, (float) Math.random() * 4f - 2f).nor();
            }
            this.pos.add(moveDir.cpy().scl(delta));
        }
        super.update(parent, delta);
    }
}
