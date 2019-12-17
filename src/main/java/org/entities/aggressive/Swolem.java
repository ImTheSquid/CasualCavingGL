package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.Entity;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.BossBar;
import org.graphics.Graphics;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.HeightMap;
import org.world.HeightReturn;
import org.world.World;

public class Swolem extends Autonomous {
    // Sprite states
    private static final int INTRO = 0, ACTIVE = 1;
    private boolean isActive = false;
    // Boss state
    private static SWOLEM_STATE swolem_state = SWOLEM_STATE.PUNCH;
    private BossBar bossBar = new BossBar(this);
    private Animator animator = new Animator(new ImageResource[]{ResourceHandler.getBossLoader().getSwolemCenterDown()}, 12);
    private ImageResource currentFrame = null;
    private SmartRectangle hitbox = new SmartRectangle(x, y, width, height);
    public Swolem() {
        super(6, 60, 75);
        displayName="Swolem";
        maxHealth = 6;
        health = maxHealth;
    }

    @Override
    public void update() {
        bossBar.update();
        /* Update State */
        isActive = y==7;
        if (isActive){
            state = ACTIVE;
        }else state = INTRO;

        /* Physics and Bounds*/
        HeightReturn heightMap = HeightMap.onGround(hitbox);
        // Y-calc
        if(heightMap.isOnGround()){
            y = heightMap.getGroundLevel();
            vY = 0;
        }else{
            vY = (state == INTRO ? 4 : 1) * World.getGravity();
            y -= vY;
        }

        // X-calc

        /* Attack Coordination */
        switch (swolem_state) {
            case PUNCH:
                // If player in reach, then punch to side
                break;
            case GROUND:
                // Ground pound independent of player position
                break;
            case VULNERABLE:
                // Allow for player to hit fist, lowering health
                break;
        }
        /* Animation */
        // Get the player position and set a target direction
        if (isActive) {
            float playerX = Main.getHarold().getX();
            boolean center = playerX > x && playerX < x + width;
            if(!center){
                direction = playerX>x+width;
                animator.setFrames(new ImageResource[]{ResourceHandler.getBossLoader().getSwolemFace(direction)});
            }
            else{
                animator.setFrames(new ImageResource[]{ResourceHandler.getBossLoader().getSwolemCenterDown()});
            }
        }
        currentFrame = animator.getCurrentFrame();
        animator.update();
    }

    @Override
    public void render() {
        width = Graphics.convertToWorldWidth(currentFrame.getTexture().getWidth());
        height = Graphics.convertToWorldHeight(currentFrame.getTexture().getHeight());
        hitbox.updateBounds(x,y,width,height);
        if(currentFrame!=null) Graphics.drawImage(currentFrame, x, y);
    }

    @Override
    public void reset() {
        x = 60;
        y = 75;
    }

    @Override
    public void handleDeath(){
        bossBar.update();
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    @Override
    public void doDamage(Entity attacker, int damage) {
        if (swolem_state != SWOLEM_STATE.VULNERABLE) {
            return;
        }
        super.doDamage(attacker, damage);
    }

    /* Offset functions */

    private static float get_X_offset(){
        return 0;
    }

    private static float get_walk_offset(){
        return 0;
    }

    private static float get_punch_offset(){
        return 0;
    }


    private enum SWOLEM_STATE{
        PUNCH,
        GROUND,
        VULNERABLE
    }
}
