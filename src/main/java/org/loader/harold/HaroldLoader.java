package org.loader.harold;

import org.loader.ImageResource;

public class HaroldLoader {
    public static final int NORMAL = 0, CHAINSAW = 1, WOOD = 2, LANTERN = 3, ROPE = 4, ATTACK = 5, TURN = 6, GOLEM = 7, BLOCK = 8;
    private boolean direction = true, attackPause = false;
    private int state = 0;

    public void setDirection(boolean dir) {
        direction = dir;
    }

    private final ImageResource[] harold = {new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/Harold.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeft.png")};

    private final ImageResource[] haroldWalkRight = {new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/HaroldW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/HaroldW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/HaroldW3.png")};

    private final ImageResource[] haroldWalkLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeftW3.png")};

    private final ImageResource[] chainsaw={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsaw.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeft.png")};

    private final ImageResource[] chainsawRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsawW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsawW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsawW3.png")};

    private final ImageResource[] chainsawLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeftW3.png")};

    private final ImageResource[] wood={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWood.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeft.png")};

    private final ImageResource[] woodRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWoodW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWoodW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWoodW3.png")};

    private final ImageResource[] woodLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeftW3.png")};

    private final ImageResource[] lantern={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLL.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeft.png")};

    private final ImageResource[] lanternRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLLW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLLW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLLW3.png")};

    private final ImageResource[] lanternLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeftW3.png")};

    private final ImageResource[] rope={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRope.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeft.png")};

    private final ImageResource[] ropeRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRopeW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRopeW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRopeW3.png")};

    private final ImageResource[] ropeLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeftW3.png")};

    private final ImageResource[] attackRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA3.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA4.png")};

    private final ImageResource[] attackLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA1Left.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA2Left.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA3Left.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA4Left.png")};

    private final ImageResource[] turn={new ImageResource("/CasualCaving/Entities/Harold/Turn/HaroldTurn1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Turn/HaroldTurn2.png")};

    private final ImageResource[] boulder={new ImageResource("/CasualCaving/Entities/Harold/Carrying/HaroldCarrying1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Carrying/HaroldCarrying2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Carrying/HaroldCarrying3.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Carrying/HaroldCarrying4.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Carrying/HaroldCarrying5.png")};

    private final ImageResource[] falter={new ImageResource("/CasualCaving/Entities/Harold/Fumble/HaroldFumbling1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Fumble/HaroldFumbling2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Fumble/HaroldFumbling3.png")};

    private final ImageResource[] golem = {new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem3.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem4.png")};

    private final ImageResource[] golemLeft = {new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem1L.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem2L.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem3L.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Golem/HaroldBlueGolem4L.png")};

    private final ImageResource[] block = {new ImageResource("/CasualCaving/Entities/Harold/Block/HaroldBlock.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Block/HaroldBlockFL.png")};

    private ImageResource health = new ImageResource("/CasualCaving/Entities/Harold/Health_Heart.png");
    private ImageResource infiniteHealth = new ImageResource("/CasualCaving/Entities/Harold/Health_Heart_Infinite.png");

    public ImageResource getHarold() {
        int dir = direction ? 0 : 1;
        switch (state) {
            default:
                return harold[dir];
            case 1:
                return chainsaw[dir];
            case 2:
                return wood[dir];
            case GOLEM:
            case 3:
                return lantern[dir];
            case 4:
                return rope[dir];
            case BLOCK:
                return block[dir];
        }
    }

    public ImageResource getHealth() {
        return health;
    }

    public ImageResource getInfiniteHealth() {
        return infiniteHealth;
    }

    public ImageResource[] getHaroldWalk() {
        switch(state){
            case 1:
                if(direction)return chainsawRight;
                else return chainsawLeft;
            case 2:
                if(direction)return woodRight;
                else return woodLeft;
            case 3:
                if(direction)return lanternRight;
                else return lanternLeft;
            case 4:
                if(direction)return ropeRight;
                else return ropeLeft;
            case 5:
                if(direction)return attackRight;
                else return attackLeft;
            case GOLEM:
                if(direction)return golem;
                else return golemLeft;
            default:
                if(direction)return haroldWalkRight;
                else return haroldWalkLeft;
        }
    }

    public ImageResource[] getTurn() {
        return turn;
    }

    public boolean isFacingRight() {
        return direction;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        if(attackPause)return;
        this.state = state;
        if(state==ATTACK)attackPause=true;
    }

    public void disableAttackPause(){
        attackPause=false;
    }

    public ImageResource[] getBoulder(){
        return boulder;
    }

    public ImageResource[] getFalter() {
        return falter;
    }
}
