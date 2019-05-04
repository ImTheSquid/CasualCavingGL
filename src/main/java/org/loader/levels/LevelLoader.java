package org.loader.levels;

import org.loader.ImageResource;

public class LevelLoader {
    private final ImageResource[] title={new ImageResource("/CasualCaving/Levels/Title/LoadScreen.png"),
    new ImageResource("/CasualCaving/Levels/Title/TitleScreen.png")};
    private final ImageResource[] titleLogo ={new ImageResource("/CasualCaving/Levels/Title/LunanLogo.png")};
    private final ImageResource[] level1={new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg1.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg2.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg3.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg4.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg5.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg6.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg6Dusk.png")
    };
    private final ImageResource[] level1Sprites={new ImageResource("/CasualCaving/Levels/Level1/Sprites/BossMan.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/Crowd.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/Log.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/Bridge.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/RedTent.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/MintTent.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/LavenderTent.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/AquaTent.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/HaroldTent.png")};
    private final ImageResource[][] level2={{new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg1.png"),new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg1Foreground.png")},
            {new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg2.png")},
            {new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg3.png")},
            {new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg3Anchor.png")},
            {new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg4.png")},
            {new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg5.png")},
            {new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg6.png")},
            {new ImageResource("/CasualCaving/Levels/Level2/Sun Stone.png")}};
    private final ImageResource[] level2Sprites={new ImageResource("/CasualCaving/Levels/Level2/Sprites/Spark.png"),
            new ImageResource("/CasualCaving/Levels/Level2/Sprites/Miners1.png"),
            new ImageResource("/CasualCaving/Levels/Level2/Sprites/Lavender Savior.png"),
            new ImageResource("/CasualCaving/Levels/Level2/Sprites/QESunStone.png")};
    private final ImageResource[] level3={new ImageResource("/CasualCaving/Levels/Level3/Lvl3Bg1.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Lvl3Bg2.png"),
            new ImageResource("/CasualCaving/Levels/Level2/Lvl2Bg6.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Lv3Bg3.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Lv3Bg4.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Lv3Bg5.png")};
    private final ImageResource[] level3Sprites={new ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Isolsi_Eyes.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Hematus_Eyes.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_1.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_2.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_3.png"),
            new ImageResource("/CasualCaving/Levels/Level3/Sun Golem Fade/Igneox_Eyes_4.png")};

    public ImageResource[] getTitle(){
        return title;
    }

    public ImageResource[] getTitleLogo() {
        return titleLogo;
    }

    public ImageResource[] getLevel1() {
        return level1;
    }

    public ImageResource[] getLevel1Sprites() {
        return level1Sprites;
    }

    public ImageResource[][] getLevel2() {
        return level2;
    }

    public ImageResource[] getLevel2Sprites() {
        return level2Sprites;
    }

    public ImageResource[] getLevel3() {
        return level3;
    }

    public ImageResource[] getLevel3Sprites() {
        return level3Sprites;
    }
}
