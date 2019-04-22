package org.loader.harold;

import org.loader.ImageResource;

public class HaroldLoader {
    public static final int NORMAL=0,CHAINSAW=1,WOOD=2,LANTERN=3,ROPE=4,ATTACK=5;
    private boolean direction=true;
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
    private ImageResource[] fallDeath={new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling1.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling2.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling3.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling4.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling5.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling6.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling7.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling8.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling9.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling10.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling11.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling12.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling13.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling14.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling15.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling16.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling17.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling18.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling19.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling20.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling21.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling22.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling23.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling24.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling25.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Falling/HaroldFalling26.png"),};
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

    public boolean isFacingRight() {
        return direction;
    }

    public ImageResource[] getFallDeath() {
        return fallDeath;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
