package org.loader;


import java.net.URL;

public class MiscLoader {
    private ImageResource lifeCrystal=new ImageResource("/CasualCaving/Entities/MiscEntities/Item_Stalagmite.png");

    public ImageResource getLifeCrystal(){return lifeCrystal;}

    private ImageResource healthHeart=new ImageResource("/CasualCaving/Entities/Harold/Health_Heart.png");

    public ImageResource getHealthHeart(){return healthHeart;}

    private ImageResource[] musicButton={new ImageResource("/CasualCaving/Menus/MusicON.png"),new ImageResource("/CasualCaving/Menus/MusicOFF.png")};

    public ImageResource getMusicButton(boolean on){return on?musicButton[0]:musicButton[1];}

    public URL getInconsolata() {
        return MiscLoader.class.getResource("/CasualCaving/Fonts/Inconsolata-Regular.ttf");
    }
}
