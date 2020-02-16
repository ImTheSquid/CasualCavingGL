package org.entities.passive;

import org.entities.Autonomous;
import org.entities.Entity;
import org.entities.aggressive.Swolem;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.graphics.Timer;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.World;

public class Column extends Autonomous {

    // State variable
    private COLUMN_STATE cState = COLUMN_STATE.TREMBLE;
    // Texture holder variable
    private ImageResource texture = null;
    // Animator
    private Animator animator = new Animator(ResourceHandler.getGolemLoader().getColumnWorried(), 12);
    // Variable to track Swolem aggression
    private boolean swolemAggressive = false;
    // Timer for game kill time
    private Timer deathTimer = new Timer(0, 3, 0, 1, 1);

    public Column(float spawnX) {
        super(6, spawnX, 7);
        maxHealth = 2;
        health = maxHealth;
        animator.setWalkBack(false);
    }

    @Override
    public void update() {
        // Update stuff
        animator.update();
        deathTimer.update();
        // If death timer finishes, kill player
        if (deathTimer.getCurrent() == deathTimer.getMax()) {
            World.clearEntites();
            World.setLevel(-1);
        }
        // State calculation
        if (health == 1) {
            deathTimer.setActive(true);
            cState = COLUMN_STATE.CRUSHED;
        } else if (swolemAggressive) cState = COLUMN_STATE.TREMBLE;
        else cState = COLUMN_STATE.WORRY;

        // Sprite calculation
        switch (cState) {
            case CRUSHED:
                animator.setFrames(ResourceHandler.getGolemLoader().getColumnCrushed());
            case TREMBLE:
                animator.setFrames(ResourceHandler.getGolemLoader().getColumnTremble());
            case WORRY:
                animator.setFrames(ResourceHandler.getGolemLoader().getColumnWorried());
        }
        texture = animator.getCurrentFrame();
    }

    @Override
    public void render() {
        if (texture != null) Graphics.drawImage(texture, x, y);
    }

    @Override
    public void reset() {
        health = maxHealth;
        deathTimer.setActive(false);
        deathTimer.setCurrent(0);
    }

    @Override
    public void doDamage(Entity attacker, int damage) {
        if (!(attacker instanceof Swolem)) return;
        if (health > 1) super.doDamage(attacker, damage);
    }

    public void setSwolemAggressive(boolean swolemAggressive) {
        this.swolemAggressive = swolemAggressive;
    }

    private enum COLUMN_STATE {
        TREMBLE,
        WORRY,
        CRUSHED
    }
}
