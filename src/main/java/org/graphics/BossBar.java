package org.graphics;

import org.entities.Entity;

public class BossBar {
    //TODO Implement smoother movement using a quadratic or logarithmic function
    private Entity track;
    private int health,max;
    public BossBar(Entity e){
        track=e;
        health=track.getHealth();
        max=track.getMaxHealth();
    }

    public void update(){
        health=track.getHealth();
        max=track.getMaxHealth();
    }

    public void render(){
        Graphics.setIgnoreScale(true);
        float width=30*health/max;
        if(width==0)return;
        Graphics.setDrawColor(1,1,1,1);
        Graphics.setFont(Graphics.NORMAL);
        float y=Render.unitsTall-Graphics.convertToWorldHeight((float)Graphics.getCurrentFont().getBounds(track.getDisplayName()).getHeight());
        Graphics.drawTextCentered(track.getDisplayName(),50,y+1f);
        Graphics.setDrawColor(0.63f,0.53f,0.02f,0.5f);
        Graphics.fillRectCentered(50,y-2,30,1);
        Graphics.setDrawColor(0.94f,0.8f,0.09f,1);
        Graphics.fillRectCentered(50,y-2,width,1);
        Graphics.setDrawColor(1,1,1,1);
        Graphics.setIgnoreScale(false);
    }
}
