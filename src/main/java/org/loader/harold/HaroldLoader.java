package org.loader.harold;

import org.loader.ImageResource;

public class HaroldLoader {
    private boolean direction=true,hasChainsaw=false,hasWood=false,inLantern=false,hasRope=false;
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
    private ImageResource[] chainsaw={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Chainsaw/HaroldChainsaw.png")};
    private ImageResource[] chainsawLeft={};
    private ImageResource[] chainsawRight={};
    public ImageResource getHarold(){
        if(direction)return harold[0];
        else return harold[1];
    }

    public ImageResource[] getHaroldWalk() {
        if(direction)return haroldWalkRight;
        else return haroldWalkLeft;
    }

    public boolean isFacingRight() {
        return direction;
    }

    public boolean hasChainsaw() {
        return hasChainsaw;
    }

    public boolean hasRope() {
        return hasRope;
    }

    public boolean hasWood() {
        return hasWood;
    }

    public boolean isInLantern() {
        return inLantern;
    }

    public void setHasChainsaw(boolean hasChainsaw) {
        this.hasChainsaw = hasChainsaw;
    }

    public void setHasRope(boolean hasRope) {
        this.hasRope = hasRope;
    }

    public void setHasWood(boolean hasWood) {
        this.hasWood = hasWood;
    }

    public void setInLantern(boolean inLantern) {
        this.inLantern = inLantern;
    }
}
