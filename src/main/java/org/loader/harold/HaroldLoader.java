package org.loader.harold;

import org.loader.ImageResource;

public class HaroldLoader {
    private boolean direction=true;
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
    public ImageResource getHarold(){
        if(direction)return harold[0];
        else return harold[1];
    }

    public ImageResource[] getHaroldWalk() {
        if(direction)return haroldWalkRight;
        else return haroldWalkLeft;
    }
}
