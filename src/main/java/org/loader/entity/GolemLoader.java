package org.loader.entity;

import org.loader.ImageResource;

public class GolemLoader {

    //REGULAR GOLEMS
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
    private ImageResource[] blueGolemKnockback={new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemKnockBackFR.png"),
            new ImageResource("/CasualCaving/Entities/BlueGolem/BlueGolemKnockBack.png")};

    public ImageResource getBlueGolem(boolean faceRight){
        return faceRight?blueGolem[0]:blueGolem[1];
    }

    public ImageResource[] getBlueGolemWalk(boolean faceRight){
        return faceRight?blueGolemRight:blueGolemLeft;
    }

    public ImageResource[] getBlueGolemAttack(boolean faceRight){
        return faceRight?blueGolemRightAttack:blueGolemLeftAttack;
    }

    public ImageResource getBlueGolemKnockback(boolean faceRight){return faceRight?blueGolemKnockback[0]:blueGolemKnockback[1];}

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

    //Red Golem

    private final ImageResource[] redGolem={new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemStill.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemStillFL.png")};
    private final ImageResource[] redGolemKnockback={new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemKnockBackFR.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemKnockBack.png")};
    private final ImageResource[] redGolemLeft={new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemW1FL.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemW2FL.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemW3FL.png")};
    private final ImageResource[] redGolemRight={new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemW1.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemW2.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemW3.png")};
    private final ImageResource[] redGolemAttackLeft={new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA1FL.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA2FL.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA3FL.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA4FL.png")};
    private final ImageResource[] redGolemAttackRight={new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA1.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA2.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA3.png"),
            new ImageResource("/CasualCaving/Entities/RedGolem/RedGolemA4.png")};

    public ImageResource getRedGolem(boolean faceRight){
        return faceRight?redGolem[0]:redGolem[1];
    }

    public ImageResource[] getRedGolemWalk(boolean faceRight){
        return faceRight?redGolemRight:redGolemLeft;
    }

    public ImageResource[] getRedGolemAttack(boolean faceRight){
        return faceRight?redGolemAttackRight:redGolemAttackLeft;
    }

    public ImageResource getRedGolemKnockback(boolean faceRight){
        return faceRight?redGolemKnockback[0]:redGolemKnockback[1];
    }

    //Purple Golem

    private final ImageResource[] purpleGolem={new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemStill.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemStillFL.png")};
    private final ImageResource[] purpleGolemKnockback={new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemKnockBackFR.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemKnockBack.png")};
    private final ImageResource[] purpleGolemLeft={new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemW1FL.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemW2FL.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemW3FL.png")};
    private final ImageResource[] purpleGolemRight={new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemW1.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemW2.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemW3.png")};
    private final ImageResource[] purpleGolemAttackLeft={new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemA1FL.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemA2FL.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemA3FL.png")};
    private final ImageResource[] purpleGolemAttackRight={new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemA1.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemA2.png"),
            new ImageResource("/CasualCaving/Entities/PurpleGolem/PurpleGolemA3.png")};

    public ImageResource getPurpleGolem(boolean faceRight){return faceRight?purpleGolem[0]:purpleGolem[1];}

    public ImageResource[] getPurpleGolemWalk(boolean faceRight){return faceRight?purpleGolemRight:purpleGolemLeft;}

    public ImageResource[] getPurpleGolemAttack(boolean faceRight){return faceRight?purpleGolemAttackRight:purpleGolemAttackLeft;}

    public ImageResource getPurpleGolemKnockback(boolean faceRight){return faceRight?purpleGolemKnockback[0]:purpleGolemKnockback[1];}

    //TALL GOLEMS
    //Tall Blue Golem

    private final ImageResource[] tbgStill={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueStillFR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueStillFL.png")};
    private final ImageResource[] tbgDamage={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueTakingDamageFR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueTakingDamage.png")};
    private final ImageResource[] tbgAttackLeft={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueA1FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueA2FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueA3FL.png")};
    private final ImageResource[] tbgAttackRight={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueA1FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueA2FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueA3FR.png")};
    private final ImageResource[] tbgJumpLeft={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ1FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ2FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ4FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ5FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ6FL.png")};
    private final ImageResource[] tbgJumpRight={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ1FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ2FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ4FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ5FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueJ6FR.png")};
    private final ImageResource[] tbgWalkLeft={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW1FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW2FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW3FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW4FL.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW5FL.png")};
    private final ImageResource[] tbgWalkRight={new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW1FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW2FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW3FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW4FR.png"),
            new ImageResource("/CasualCaving/Entities/BlueTallGolem/TallGolemBlueW5FR.png")};

    public ImageResource getTallBlueGolem(boolean faceRight){return faceRight?tbgStill[0]:tbgStill[1];}

    public ImageResource[] getTallBlueGolemAttack(boolean faceRight){return faceRight?tbgAttackRight:tbgAttackLeft;}

    public ImageResource[] getTallBlueGolemWalk(boolean faceRight){return faceRight?tbgWalkRight:tbgWalkLeft;}

    public ImageResource[] getTallBlueGolemJump(boolean faceRight){return faceRight?tbgJumpRight:tbgJumpLeft;}

    public ImageResource getTallBlueGolemKnockback(boolean faceRight){return faceRight?tbgDamage[0]:tbgDamage[1];}

    //Isolsi

    private final ImageResource[] isolsi={new ImageResource("/CasualCaving/Entities/Isolsi/Isolsi_Sun_Stone.png"),
            new ImageResource("/CasualCaving/Entities/Isolsi/Isolsi_Glowing.png"),
            new ImageResource("/CasualCaving/Entities/Isolsi/Isolsi_Base.png"),
            new ImageResource("/CasualCaving/Entities/Isolsi/Isolsi_Looking_Down.png"),
            new ImageResource("/CasualCaving/Entities/Isolsi/Isolsi_Gesturing.png"),
            new ImageResource("/CasualCaving/Entities/Isolsi/Isolsi_Slightly_Angry.png"),
            new ImageResource("/CasualCaving/Entities/Isolsi/Isolsi_Clenching_Fist.png")};

    public ImageResource[] getIsolsi() {
        return isolsi;
    }
}
