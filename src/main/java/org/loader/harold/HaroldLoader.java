package org.loader.harold;

import org.loader.ImageResource;

public class HaroldLoader {
    private boolean direction=true;
    public void setDirection(boolean dir){
        direction=dir;
    }
    private ImageResource[] harold={new ImageResource("/CasualCaving/Entities/Harold/Face_Right/Normal/Harold.png"),
            new ImageResource("/CasualCaving/Entities/Harold/Face_Left/Normal/Harold.png")};
    private ImageResource[] haroldWalkRight={};
    private ImageResource[] haroldWalkLeft={};
    public ImageResource getHarold(){
        if(direction)return harold[0];
        else return harold[1];
    }

    public ImageResource[] getHaroldWalk() {
        if(direction)return haroldWalkRight;
        else return haroldWalkLeft;
    }
}
