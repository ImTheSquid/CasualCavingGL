package org.entities.aggressive;

import org.entities.Entity;
import org.graphics.Graphics;
import org.loader.ResourceHandler;
import org.world.Attack;

public class SwolemWave extends Entity {
    // Tracks scale of entity/damage
    float scaleMultiplier = 3;
    // Swolem object to keep track of distance
    private Swolem swolem;

    SwolemWave(float spawnX, boolean direction, Swolem swolem) {
        this.swolem = swolem;
        this.direction = direction;
        x = spawnX;
        y = 7;
        subLevel = 6;
        invincible = true;
    }

    @Override
    public void update() {
        x += direction ? 1 : -1;
        Attack.melee(this, (int) scaleMultiplier, 1);
        float distance = Math.abs(x - swolem.getX());
        scaleMultiplier = 1;//(float) Math.pow(0.5, 0.1 * distance - 1.5);
        // Kill entity if offscreen
        if (x < -50 || x > 110) health = 0;
    }

    @Override
    public void render() {
        Graphics.setScaleFactor(scaleMultiplier);
        Graphics.drawImage(ResourceHandler.getBossLoader().getSwolemWave(direction), x, 7);
        Graphics.setScaleFactor(1);
    }

    @Override
    public void reset() {

    }
}
