package org.loader;


import java.net.URL;

public class MiscLoader {
    private ImageResource lifeCrystal=new ImageResource("/CasualCaving/Entities/MiscEntities/Item_Stalagmite.png");

    public ImageResource getLifeCrystal(){return lifeCrystal;}

    private ImageResource healthHeart=new ImageResource("/CasualCaving/Entities/Harold/Health_Heart.png");

    public ImageResource getHealthHeart(){return healthHeart;}

    private ImageResource[] musicButton={new ImageResource("/CasualCaving/Menus/MusicON.png"),new ImageResource("/CasualCaving/Menus/MusicOFF.png")};

    public ImageResource getMusicButton(boolean on){return on?musicButton[0]:musicButton[1];}

    private ImageResource laranoStalactite=new ImageResource("/CasualCaving/Levels/Level5/LaranoStalactite.png");

    public ImageResource getLaranoStalactite() {
        return laranoStalactite;
    }

    public URL getInconsolata() {
        return MiscLoader.class.getResource("/CasualCaving/Fonts/Inconsolata-Regular.ttf");
    }

    public URL getMerriweather(){ return MiscLoader.class.getResource("/CasualCaving/Fonts/Merriweather-Regular.ttf");}

    private ImageResource checkmark=new ImageResource("/CasualCaving/Menus/checkmark.png");

    public ImageResource getCheckmark() {
        return checkmark;
    }

    private ImageResource resolutionWarning=new ImageResource("/CasualCaving/Menus/ResolutionWarning.png");

    public ImageResource getResolutionWarning() {
        return resolutionWarning;
    }

    private ImageResource boulder = new ImageResource("/CasualCaving/Entities/Boulder/Boulder.png");

    public ImageResource getBoulder() {
        return boulder;
    }

    private ImageResource[] rockParticles = {new ImageResource("/CasualCaving/Levels/Level3/FallingRockParticle1.png"),
            new ImageResource("/CasualCaving/Levels/Level3/FallingRockParticle2.png")
    };

    public ImageResource[] getRockParticles() {
        return rockParticles;
    }
}
