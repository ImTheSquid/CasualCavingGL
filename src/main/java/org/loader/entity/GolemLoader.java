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

    //Green Golem

    private ImageResource[] greenGolem={new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemStill.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemStillFL.png")};
    private ImageResource[] greenGolemKnockback={new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemKnockBackFR.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemKnockBack.png")};
    private ImageResource[] greenGolemLeft={new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemW1FL.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemW2FL.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemW3FL.png")};
    private ImageResource[] greenGolemRight={new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemW1.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemW2.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemW3.png")};
    private ImageResource[] greenGolemLeftAttack={new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA1FL.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA2FL.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA3FL.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA4FL.png")};
    private ImageResource[] greenGolemRightAttack={new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA1.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA2.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA3.png"),
            new ImageResource("/CasualCaving/Entities/GreenGolem/GreenGolemA4.png")};

    public ImageResource getGreenGolem(boolean faceRight){return faceRight?greenGolem[0]:greenGolem[1];}

    public ImageResource[] getGreenGolemWalk(boolean faceRight){return faceRight?greenGolemRight:greenGolemLeft;}

    public ImageResource[] getGreenGolemAttack(boolean faceRight){return faceRight?greenGolemRightAttack:greenGolemLeftAttack;}

    public ImageResource getGreenGolemKnockback(boolean faceRight){return faceRight?greenGolemKnockback[0]:greenGolemKnockback[1];}
}
