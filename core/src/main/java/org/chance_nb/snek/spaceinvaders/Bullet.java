package org.chance_nb.snek.spaceinvaders;

import org.chance_nb.snek.Main;
import org.chance_nb.snek.Util;

import com.badlogic.gdx.math.Vector2;

public class Bullet extends SpaceGameObject {
    public static Vector2 moveVec = new Vector2(0f, 8f);
    static float size = 0.3f;
    public Bullet(Main main, float posx, float posy) {
        super(main, main.manager.get("wormcircle.png"), posx, posy, size, size, 0.5f, 0.5f);
    }

    @Override
    public void update(SpaceInvadersGameScreen parent, float delta) {
        this.pos = this.pos.add(moveVec.cpy().scl(delta));
        if (this.pos.y > main.worldHeight) {
            parent.bullets.removeValue(this, true);
        }

        for (Enemy enemy : parent.enemies) {
            if (Util.checkCollision(main, this.pos, enemy.pos, size)) {
                parent.enemies.removeValue(enemy, true);
                parent.points++;
                if (Math.random()>0.3f) {
                    parent.difficulty += Math.random() * 1.5f;
                }
                if (parent.enemies.isEmpty()) {
                    parent.spawnRandomlyGeneratedEnemyLayout();
                }
                parent.bullets.removeValue(this, true);
            }
        }
    }

}
