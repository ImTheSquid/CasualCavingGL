package org.loader.levels;

import org.loader.ImageResource;

public class LevelLoader {
    private final ImageResource[] title={new ImageResource("/CasualCaving/Levels/Title/LoadScreen.png"),
    new ImageResource("/CasualCaving/Levels/Title/TitleScreen.png")};
    private final ImageResource[] titleForeground={new ImageResource("/CasualCaving/Levels/Title/LunanLogo.png")};
    private final ImageResource[] level1={new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg1.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg2.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg3.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg4.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg5.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg6.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Lvl1Bg6Dusk.png")
    };
    private final ImageResource[] level1Sprites={new ImageResource("/CasualCaving/Levels/Level1/Sprites/BossMan.png"),
            new ImageResource("/CasualCaving/Levels/Level1/Sprites/Crowd.png")};
    private final ImageResource[] level2={};
    private final ImageResource[] level3={};

    public ImageResource[] getTitle(){
        return title;
    }

    public ImageResource[] getTitleForeground() {
        return titleForeground;
    }

    public ImageResource[] getLevel1() {
        return level1;
    }

    public ImageResource[] getLevel1Sprites() {
        return level1Sprites;
    }
}
