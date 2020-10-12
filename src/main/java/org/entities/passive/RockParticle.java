package org.entities.passive;

import org.entities.Autonomous;
import org.graphics.Graphics;
import org.loader.ResourceHandler;
import org.world.World;

public class RockParticle extends Autonomous {
    int particle = (int) (Math.random() * 2);

    public RockParticle(int subLevel, float spawnX) {
        super(subLevel, spawnX, 60);
        invincible = true;
        displayName = "RockParticle";
    }

    @Override
    public void update(float deltaTime) {
        if (y + height < 0) health = 0;
        y -= World.getGravity() * 4;
    }

    @Override
    public void render() {
        height = Graphics.toWorldHeight(ResourceHandler.getMiscLoader().getRockParticles()[particle].getTexture().getHeight());
        Graphics.drawImage(ResourceHandler.getMiscLoader().getRockParticles()[particle], x, y);
    }

    @Override
    public void reset() {

    }
}
