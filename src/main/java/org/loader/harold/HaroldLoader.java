package org.loader.harold;

import org.loader.ImageResource;

public class HaroldLoader {
    public static final int NORMAL=0,CHAINSAW=1,WOOD=2,LANTERN=3,ROPE=4,ATTACK=5,TURN=6;
    private boolean direction=true,attackPause=false;
    private int state=0;
    public void setDirection(boolean dir){
        direction=dir;
    }
    private ImageResource[] harold={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/Harold.png"),
    new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeft.png")};
    private ImageResource[] haroldWalkRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/HaroldW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/HaroldW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/HaroldW3.png")};
    private ImageResource[] haroldWalkLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/HaroldLeftW3.png")};
    private ImageResource[] chainsaw={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsaw.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeft.png")};
    private ImageResource[] chainsawRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsawW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsawW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsawW3.png")};
    private ImageResource[] chainsawLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Chainsaw/HaroldChainsawLeftW3.png")};
    private ImageResource[] wood={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWood.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeft.png")};
    private ImageResource[] woodRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWoodW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWoodW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Wood/HaroldWoodW3.png")};
    private ImageResource[] woodLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Wood/HaroldWoodLeftW3.png")};
    private ImageResource[] lantern={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLL.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeft.png")};
    private ImageResource[] lanternRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLLW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLLW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Normal/HaroldLLW3.png")};
    private ImageResource[] lanternLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Normal/HaroldLLLeftW3.png")};
    private ImageResource[] rope={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRope.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeft.png")};
    private ImageResource[] ropeRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRopeW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRopeW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/LanternLight/Rope/HaroldRopeW3.png")};
    private ImageResource[] ropeLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeftW1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeftW2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/LanternLight/Rope/HaroldRopeLeftW3.png")};
    private ImageResource[] attackRight={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA3.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Attack/HaroldA4.png")};
    private ImageResource[] attackLeft={new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA1Left.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA2Left.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA3Left.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Attack/HaroldA4Left.png")};
    private ImageResource[] turn={new ImageResource("/CasualCaving/Entities/Harold/Turn/HaroldTurn1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Turn/HaroldTurn2.png")};
    private ImageResource health=new ImageResource("/CasualCaving/Entities/Harold/Health_Heart.png");
    private ImageResource infiniteHealth=new ImageResource("/CasualCaving/Entities/Harold/Health_Heart_Infinite.png");
    public ImageResource getHarold(){
        int dir;
        if(direction)dir=0;
        else dir=1;
        switch(state){
            default:return harold[dir];
            case 1:return chainsaw[dir];
            case 2:return wood[dir];
            case 3:return lantern[dir];
            case 4:return rope[dir];
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
}
