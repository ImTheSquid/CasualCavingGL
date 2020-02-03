package org.entities.passive;

import org.entities.Autonomous;
import org.entities.Entity;
import org.graphics.Animator;
import org.graphics.Graphics;
import org.graphics.Timer;
import org.loader.ImageResource;
import org.loader.ResourceHandler;

public class Column extends Autonomous {

    // State variable
    private COLUMN_STATE cState = COLUMN_STATE.TREMBLE;
    // Texture holder variable
    private ImageResource texture = null;
    // Animator
    private Animator animator = new Animator(ResourceHandler.getGolemLoader().getColumnWorried(), 12);
    // Death animation timer
    private Timer deathTimer = new Timer(0, 2, 0, 1, 1);
    // Variable to track Swolem aggression
    private boolean swolemAggressive = false;

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
        // State calculation
        if (health == 1) cState = COLUMN_STATE.CRUSHED;
        else if (swolemAggressive) cState = COLUMN_STATE.TREMBLE;
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
    }

    @Override
    public void render() {
        if (texture != null) Graphics.drawImage(texture, x, y);
    }

    @Override
    public void reset() {
        health = maxHealth;
    }

    @Override
    public void doDamage(Entity attacker, int damage) {
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
