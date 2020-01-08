package org.entities.aggressive;

import org.engine.Main;
import org.entities.Autonomous;
import org.entities.Entity;
import org.entities.Harold;
import org.entities.SmartRectangle;
import org.graphics.Animator;
import org.graphics.BossBar;
import org.graphics.Graphics;
import org.graphics.Timer;
import org.level.Level;
import org.level.LevelController;
import org.loader.ImageResource;
import org.loader.ResourceHandler;
import org.world.Attack;
import org.world.HeightMap;
import org.world.HeightReturn;
import org.world.World;

public class Swolem extends Autonomous {
    // Activity states
    private static final int INTRO = 0, WALKING = 1;
    // How many times ground has been pounded; how many waves have been created on the current punching round
    private int poundCount = 0, wavesCreated = 0;
    private boolean isActive = false;
    // Boss state
    private static SWOLEM_STATE swolemState = SWOLEM_STATE.NONE;
    private BossBar bossBar = new BossBar(this);
    private Animator animator = new Animator(new ImageResource[]{ResourceHandler.getBossLoader().getSwolemCenterDown()}, 6);
    // How much time the swolem must wait before attempting to ground pound
    private Timer groundCooldown = new Timer(0, 15, 0, 1, 1);
    private Timer punchCooldown = new Timer(0, 7, 0, 1, 1);
    private ImageResource currentFrame = null;
    private SmartRectangle hitbox = new SmartRectangle(x, y, width, height);

    public Swolem() {
        super(6, 60, 75);
        displayName = "Swolem";
        maxHealth = 6;
        health = maxHealth;
        animator.setWalkBack(false);
        groundCooldown.setActive(true);
        punchCooldown.setActive(true);
    }

    @Override
    public void update() {
        groundCooldown.update();
        punchCooldown.update();
        bossBar.update();
        /* Update State */
        isActive = y == 7;

        /* Physics and Bounds*/
        HeightReturn heightMap = HeightMap.onGround(hitbox);
        // Y-calc
        if (heightMap.isOnGround()) {
            y = heightMap.getGroundLevel();
            vY = 0;
        } else {
            vY = (state == INTRO ? 4 : 1) * World.getGravity();
            y -= vY;
        }

        // X-calc
        float playerX = Main.getHarold().getX();
        boolean center = playerX > x && playerX < x + width;

        if (center || !isActive || swolemState != SWOLEM_STATE.NONE) vX = 0;
        else vX = direction ? .05f : -.05f;
        Level currentLevel = LevelController.getCurrentLevel();
        if(x < currentLevel.getLeftBound() || x + width > currentLevel.getRightBound()){
            direction = !direction;
        }

        x += vX;

        /* Attack Coordination */
        if (swolemState == SWOLEM_STATE.NONE && isActive) {
            Harold harold = Main.getHarold();
            if ((harold.getX() + harold.getWidth() + 2 > x && harold.getX() - 2 < x + width) && !center && punchCooldown.getCurrent() == punchCooldown.getMax())
                swolemState = SWOLEM_STATE.PUNCH;
        }

        /* Animation */
        // Get the player position and set a target direction
        if (isActive) {
            switch (swolemState) {
                case PUNCH:
                    // If player in reach, then punch to side
                    animator.setFrames(ResourceHandler.getBossLoader().getSwolemPunch(direction));
                    if (animator.onLastFrame()) {
                        Attack.melee(this, 1, 2);
                        swolemState = SWOLEM_STATE.NONE;
                        punchCooldown.setCurrent(punchCooldown.getMin());
                    }
                    break;
                case GROUND:
                    // Ground pound independent of player position
                    if (animator.onLastFrame()) {
                        if (wavesCreated == 3) {
                            wavesCreated = 0;
                            poundCount++;
                            if (poundCount == 3) {
                                swolemState = SWOLEM_STATE.VULNERABLE;
                                poundCount = 0;
                            }
                        } else {
                            wavesCreated++;
                        }
                    }
                    break;
                case VULNERABLE:
                    // Allow for player to hit fist, lowering health
                    break;
                case NONE:
                    if (!center) {
                        direction = playerX > x + width;
                        animator.setFrames(ResourceHandler.getBossLoader().getSwolemWalk(direction));
                    } else {
                        animator.setFrames(ResourceHandler.getBossLoader().getSwolemCenterDown());
                    }
                    break;
            }
        }
        currentFrame = animator.getCurrentFrame();
        animator.update();
    }

    @Override
    public void render() {
        width = Graphics.convertToWorldWidth(currentFrame.getTexture().getWidth());
        height = Graphics.convertToWorldHeight(currentFrame.getTexture().getHeight());
        float offset = Graphics.convertToWorldWidth(get_X_offset());
        hitbox.updateBounds(x + offset,y,width,height);
        if(currentFrame!=null) Graphics.drawImage(currentFrame, x + offset, y);
    }

    @Override
    public void reset() {
        x = 60;
        y = 75;
        poundCount = 0;
        health = 6;
        state = INTRO;
        isActive = false;
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
        if (swolemState != SWOLEM_STATE.VULNERABLE) {
            return;
        }
        super.doDamage(attacker, damage);
    }

    /* Offset functions */

    private float get_X_offset(){
        // Static facing directions
        float playerX = Main.getHarold().getX();
        if (state == WALKING && !(playerX > x && playerX < x + width))return 19;
        return 0;
    }

    private float get_walk_offset(){
        return 0;
    }

    private float get_punch_offset(){
        return 0;
    }

    private enum SWOLEM_STATE{
        PUNCH,
        GROUND,
        VULNERABLE,
        NONE
    }
}
