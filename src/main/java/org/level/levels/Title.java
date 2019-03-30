package org.level.levels;

import org.graphics.Graphics;
import org.graphics.Render;
import org.level.Level;
import org.loader.ImageResource;

public class Title extends Level {
    private float logoBright=0f;
    private boolean logoDirection=true;//Whether logo fades in or out
    public Title(ImageResource[] backgrounds,ImageResource[] foregrounds){
        super(backgrounds);
        super.foregrounds=foregrounds;
    }

    public void update(int subLevel) {

    }

    public void render(int subLevel) {
        switch (subLevel){
            case 0:loadingScreen();
            break;
        }
    }

    private void loadingScreen(){
        Graphics.setColor(1,1,1,1);
        Graphics.drawImage(backgrounds[0],0,0);
        Graphics.setColor(1,1,1,logoBright);
        Graphics.drawImageCentered(foregrounds[0], Render.unitsWide/2,55);
    }
}
