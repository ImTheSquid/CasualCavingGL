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

    private ImageResource[] laranoShimmerLeft=new ImageResource[14];
    private ImageResource[] laranoShimmerRight=new ImageResource[14];

    public ImageResource[] getLaranoShimmer(boolean faceRight) {
        if (faceRight){
            if (laranoShimmerRight[0] == null)
                for (int i = 0; i < laranoShimmerRight.length; i++)
                    laranoShimmerRight[i] = new ImageResource("/CasualCaving/Entities/Larano/SwordShimmer/CLaranoC"+(i+1)+".png");
            return laranoShimmerRight;
        }else{
            if (laranoShimmerLeft[0] == null)
                for (int i = 0; i < laranoShimmerLeft.length; i++)
                    laranoShimmerLeft[i] = new ImageResource("/CasualCaving/Entities/Larano/SwordShimmer/CLaranoC"+(i+1)+"FL.png");
            return laranoShimmerLeft;
        }
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

    private ImageResource[] laranoAttack={new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA1.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA2.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA3.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA4.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA5.png")};

    private ImageResource[] laranoAttackLeft={new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA1FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA2FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA3FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA4FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoA5FL.png")};

    public ImageResource[] getLaranoAttack(boolean faceRight){return faceRight?laranoAttack:laranoAttackLeft;}

    private ImageResource[] laranoAltAttackLeft={new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO1FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO2FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO3FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO4FL.png")};

    private ImageResource[] laranoAltAttackRight={new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO1.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO2.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO3.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Attack/CLaranoO4.png")};

    public ImageResource[] getLaranoAltAttack(boolean faceRight) {
        return faceRight?laranoAltAttackRight:laranoAltAttackLeft;
    }

    private ImageResource[] laranoDash={new ImageResource("/CasualCaving/Entities/Larano/CLaranoSwordDashing.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoSwordDashingFL.png")};

    public ImageResource getLaranoDash(boolean faceRight) {
        return faceRight?laranoDash[0]:laranoDash[1];
    }

    private ImageResource[] laranoDizzyLeft={new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy1FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy2FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy3FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy4FL.png")};

    private ImageResource[] laranoDizzyRight={new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy1.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy2.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy3.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Dizzy/CLaranoDizzy4.png")};

    public ImageResource[] getLaranoDizzy(boolean faceRight){return faceRight?laranoDizzyRight:laranoDizzyLeft;}

    private ImageResource[] laranoDamage={new ImageResource("/CasualCaving/Entities/Larano/CLaranoTakingDamage.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoTakingDamageFL.png")};

    public ImageResource getLaranoDamage(boolean faceRight){return faceRight?laranoDamage[0]:laranoDamage[1];}

    private ImageResource[] laranoJumpLeft={new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ1FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ2FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ1FL.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ4FL.png")};

    private ImageResource[] laranoJumpRight={new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ1.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ2.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ1.png"),
            new ImageResource("/CasualCaving/Entities/Larano/CLaranoJ4.png")};

    public ImageResource[] getLaranoJump(boolean faceRight){return faceRight?laranoJumpRight:laranoJumpLeft;}

    private ImageResource[] laranoDefeat={new ImageResource("/CasualCaving/Entities/Larano/Defeat/CinematicLaranoDefeated.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Defeat/CinematicLaranoDefeated2.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Defeat/CinematicLaranoDefeated3.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Defeat/CinematicLaranoDefeated4.png"),
            new ImageResource("/CasualCaving/Entities/Larano/Defeat/CinematicLaranoDefeated5.png")};

    public ImageResource[] getLaranoDefeat(){return laranoDefeat;}

    //Emerie

    private ImageResource[] emerieWalkLeft={new ImageResource("/CasualCaving/Entities/Emerie/EmerieW1FL.png"),
            new ImageResource("/CasualCaving/Entities/Emerie/EmerieW2FL.png"),
            new ImageResource("/CasualCaving/Entities/Emerie/EmerieW3FL.png"),
            new ImageResource("/CasualCaving/Entities/Emerie/EmerieW4FL.png")};

    private ImageResource[] emerieWalkRight={new ImageResource("/CasualCaving/Entities/Emerie/EmerieW1.png"),
            new ImageResource("/CasualCaving/Entities/Emerie/EmerieW2.png"),
            new ImageResource("/CasualCaving/Entities/Emerie/EmerieW3.png"),
            new ImageResource("/CasualCaving/Entities/Emerie/EmerieW4.png")};

    public ImageResource[] getEmerieWalk(boolean faceRight) {
        return faceRight?emerieWalkRight:emerieWalkLeft;
    }

    private ImageResource[] emerieGesture={new ImageResource("/CasualCaving/Entities/Emerie/EmerieGesturing.png"),
            new ImageResource("/CasualCaving/Entities/Emerie/EmeriePointingOut.png")};

    public ImageResource getEmerieGesture(boolean pointing){
        return pointing?emerieGesture[0]:emerieGesture[1];
    }

    private final ImageResource emerieForward=new ImageResource("/CasualCaving/Entities/Emerie/EmerieForward.png");

    public ImageResource getEmerieForward() {
        return emerieForward;
    }

    // Swolem

    private static final ImageResource swolemCenterDown = new ImageResource("/CasualCaving/Entities/Swolem/Swolem_Falling.png");

    private static final ImageResource[] swolemFace = {new ImageResource("/CasualCaving/Entities/Swolem/SwolemAggrodFL.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/SwolemAggrodFR.png")};

    public ImageResource getSwolemCenterDown() {
        return swolemCenterDown;
    }

    public ImageResource getSwolemFace(boolean direction) {return swolemFace[direction?1:0];}

    private static final ImageResource[] swolemWalkLeft = {new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking1FL.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking2FL.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking3FL.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking4FL.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking5FL.png")};

    private static final ImageResource[] swolemWalkRight = {new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking1FR.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking2FR.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking3FR.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking4FR.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Walk/SwolemWalking5FR.png")};

    public ImageResource[] getSwolemWalk(boolean direction) {
        return direction ? swolemWalkRight : swolemWalkLeft;
    }

    private static final ImageResource[] punchLeft = {new ImageResource("/CasualCaving/Entities/Swolem/Punch/SwolemPunch1FL.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Punch/SwolemPunch2FL.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Punch/SwolemPunch3FL.png")};

    private static final ImageResource[] punchRight = {new ImageResource("/CasualCaving/Entities/Swolem/Punch/SwolemPunch1FR.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Punch/SwolemPunch2FR.png"),
            new ImageResource("/CasualCaving/Entities/Swolem/Punch/SwolemPunch3FR.png")};

    public ImageResource[] getSwolemPunch(boolean direction){return direction ? punchLeft : punchRight;}
}
