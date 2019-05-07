package org.loader.entity;

import org.loader.ImageResource;

public class GolemLoader {

    //Blue Golem

    private ImageResource[] blueGolem={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemStill.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemStillFL.png")};
    private ImageResource[] blueGolemLeft={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW1FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW2FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW3FL.png")};
    private ImageResource[] blueGolemRight={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW1.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW2.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemW3.png")};
    private ImageResource[] blueGolemLeftAttack={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA1FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA2FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA3FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA4FL.png")};
    private ImageResource[] blueGolemRightAttack={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA1.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA2.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA3.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemA4.png")};

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
