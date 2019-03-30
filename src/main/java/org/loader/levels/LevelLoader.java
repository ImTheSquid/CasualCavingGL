package org.loader.levels;

import org.loader.ImageResource;

public class LevelLoader {
    private final ImageResource[] title={new ImageResource("/CasualCaving/Levels/Title/LoadScreen.png"),
    new ImageResource("/CasualCaving/Levels/Title/TitleScreen.png")};
    private final ImageResource[] titleForeground={new ImageResource("/CasualCaving/Levels/Title/LunanLogo.png")};
    private final ImageResource[] level1={};
    private final ImageResource[] level2={};
    private final ImageResource[] level3={};

    public ImageResource[] getTitle(){
        return title;
    }

    public ImageResource[] getTitleForeground() {
        return titleForeground;
    }
}
