package org.loader.entity;

import org.loader.ImageResource;

public class BossLoader {
    //Red Major
    private ImageResource[] redMajorWalkLeft={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW1FL.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW2FL.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW3FL.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW4FL.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW5FL.png")};
    private ImageResource[] redMajorWalkRight={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW1.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW2.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW3.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW4.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorW5.png")};
    private ImageResource[] redMajorReadyLeft={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorReadying1.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorReadying2.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorReadying3.png")};
    private ImageResource[] redMajorReadyRight={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorReadying1FR.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorReadying2FR.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorReadying3FR.png")};
    private ImageResource[] redMajorAttackLeft={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorA2.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorA3.png")};
    private ImageResource[] redMajorAttackRight={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorA2FR.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorA3FR.png")};
    private ImageResource[] redMajorStill={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorStillFL.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorStillFR.png")};
    private ImageResource[] redMajorDamage={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorTakingDamageFL.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorTakingDamage.png")};

    public ImageResource[] getRedMajorWalk(boolean faceRight){
        return faceRight?redMajorWalkRight:redMajorWalkLeft;
    }

    public ImageResource getRedMajorStill(boolean faceRight){
        return faceRight?redMajorStill[1]:redMajorStill[0];
    }

    public ImageResource getRedMajorDamage(boolean faceRight){
        return faceRight?redMajorDamage[1]:redMajorDamage[0];
    }

    public ImageResource[] getRedMajorReady(boolean faceRight){
        return faceRight?redMajorReadyRight:redMajorReadyLeft;
    }

    public ImageResource[] getRedMajorAttack(boolean faceRight){
        return faceRight?redMajorAttackRight:redMajorAttackLeft;
    }
}
