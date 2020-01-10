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
    // Constant smash order
    private static final int[] smashOrder = {0, 1, 2, 4, 3, 4, 2, 1, 0};
    private boolean isActive = false;
    // How many times ground has been pounded
    private int poundCount = 0;
    // Boss state
    private static SWOLEM_STATE swolemState = SWOLEM_STATE.NONE;
    private BossBar bossBar = new BossBar(this);
    private Animator animator = new Animator(new ImageResource[]{ResourceHandler.getBossLoader().getSwolemCenterDown()}, 6);
    // How much time the swolem must wait before attempting to ground pound
    private Timer smashCooldown = new Timer(0, 15, 0, 1, 1);
    /* Since the game runs faster than the frame rate, it is possible to increment the smash counter
     * multiple times while only being on one frame. */
    private boolean smashAlt = true;
    // Above comment, but for punching
    private Timer punchCooldown = new Timer(0, 7, 0, 1, 1);
    private Timer smashAnimator = new Timer(0, smashOrder.length - 1, 0, 1, 6);
    private ImageResource currentFrame = null;
    private SmartRectangle hitbox = new SmartRectangle(x, y, width, height);

    public Swolem() {
        super(6, 60, 75);
        displayName = "Swolem";
        maxHealth = 6;
        health = maxHealth;
        animator.setWalkBack(false);
        smashCooldown.setActive(true);
        punchCooldown.setActive(true);
        smashAnimator.setLoop(true);
        smashAnimator.setActive(true);
    }

    @Override
    public void update() {
        smashCooldown.update();
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
            else if (((harold.getX() + harold.getWidth() + 2 < x && x + width < harold.getX() - 2) || center) && smashCooldown.getCurrent() == smashCooldown.getMax())
                swolemState = SWOLEM_STATE.SMASH;
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
                case SMASH:
                    // Ground pound independent of player position
                    smashAnimator.update();

                    if (smashOrder[(int) smashAnimator.getCurrent()] == 2 && smashAlt) {
                        // Don't let this execute again until next frame
                        smashAlt = false;
                        poundCount++;
                        if (poundCount == 6) {
                            swolemState = SWOLEM_STATE.VULNERABLE;
                            smashCooldown.setCurrent(smashCooldown.getMin());
                            poundCount = 0;
                        }
                    } else if (smashOrder[(int) smashAnimator.getCurrent()] != 2) smashAlt = true;
                    break;
                case VULNERABLE:
                    // Allow for player to hit fist, lowering health
                    swolemState = SWOLEM_STATE.NONE;
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
        if (swolemState != SWOLEM_STATE.SMASH) currentFrame = animator.getCurrentFrame();
        else
            currentFrame = ResourceHandler.getBossLoader().getSwolemSmash()[smashOrder[(int) smashAnimator.getCurrent()]];
        animator.update();
    }

    @Override
    public void render() {
        width = Graphics.toWorldWidth(currentFrame.getTexture().getWidth());
        height = Graphics.convertToWorldHeight(currentFrame.getTexture().getHeight());
        float offset = Graphics.toWorldWidth(get_X_offset());
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

    private enum SWOLEM_STATE {
        PUNCH,
        SMASH,
        VULNERABLE,
        NONE
    }
}
