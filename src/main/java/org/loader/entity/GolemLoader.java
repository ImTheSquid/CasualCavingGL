package org.loader.entity;

import org.loader.ImageResource;

public class GolemLoader {
    private ImageResource[] blueGolem={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemStill.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemStillFL.png")};
    private ImageResource[] blueGolemLeft={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW1FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW2FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW3FL.png")};
    private ImageResource[] blueGolemRight={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW1.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW2.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW3.png")};
    private ImageResource[] blueGolemLeftAttack={};
    private ImageResource[] blueGolemRightAttack={};

    public ImageResource getBlueGolem(boolean faceRight){
        return faceRight?blueGolem[0]:blueGolem[1];
    }

    public ImageResource[] getBlueGolemWalk(boolean faceRight){
        return faceRight?blueGolemRight:blueGolemLeft;
    }

    public ImageResource[] getBlueGolemAttack(boolean faceRight){
        return faceRight?blueGolemRightAttack:blueGolemLeftAttack;
    }
}
