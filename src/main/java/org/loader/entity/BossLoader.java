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
    private ImageResource[] redMajorDeath={new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorDeath.png"),
            new ImageResource("/CasualCaving/Entities/RedMajor/RedMajorDeathFR.png")};

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

    public ImageResource getRedMajorDeath(boolean faceRight) {
        return faceRight?redMajorDeath[1]:redMajorDeath[0];
    }

    //Cine Larano

    private ImageResource[] cineWalk={new ImageResource("/CasualCaving/Entities/CineLarano/LaranoW1.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoW2.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoW3.png")};
    private ImageResource[] cineWalkRight={new ImageResource("/CasualCaving/Entities/CineLarano/LaranoW1FR.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoW2FR.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoW3FR.png")};
    private ImageResource[] cineExpression={new ImageResource("/CasualCaving/Entities/CineLarano/LaranoAccusing.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoAsking.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoDescribing.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoGesturing.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoIntrigued.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoRealizing.png"),
            new ImageResource("/CasualCaving/Entities/CineLarano/LaranoStern.png")};

    public ImageResource[] getCineWalk(boolean faceRight) {
        return faceRight?cineWalkRight:cineWalk;
    }

    public ImageResource[] getCineExpression() {
        return cineExpression;
    }

    //Larano
    private ImageResource[] laranoReady=new ImageResource[26];

    public ImageResource[] getLaranoReadying(){
        if(laranoReady[0]==null){
            for(int i=0;i<laranoReady.length;i++){
                laranoReady[i]=new ImageResource("/CasualCaving/Entities/Larano/Readying/CLaranoReadying"+(i+1)+".png");
            }
        }
        return laranoReady;
    }

    private ImageResource[] laranoWalkLeft={new ImageResource("/CasualCaving/Entities/Larano/CLaranoW1FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoW2FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoW3FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoW4FL.png")};

    private ImageResource[] laranoWalk={new ImageResource("/CasualCaving/Entities/Larano/CLaranoW1.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoW2.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoW3.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoW4.png")};

    public ImageResource[] getLaranoWalk(boolean faceRight){return faceRight?laranoWalk:laranoWalkLeft;}

    private ImageResource[] laranoAttack={new ImageResource("/CasualCaving/Entities/Larano/CLaranoA1.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA2.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA3.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA4.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA5.png")};

    private ImageResource[] laranoAttackLeft={new ImageResource("/CasualCaving/Entities/Larano/CLaranoA1FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA2FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA3FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA4FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoA5FL.png")};

    public ImageResource[] getLaranoAttack(boolean faceRight){return faceRight?laranoAttack:laranoAttackLeft;}
}
