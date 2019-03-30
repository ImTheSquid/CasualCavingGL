package org.entities;

import org.graphics.Graphics;
import org.input.Mouse;

public class SmartRectangle extends Entity{
    private boolean isPressed=false,isVisible=true;
    public SmartRectangle(float x,float y,float width,float height){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public SmartRectangle(float x,float y,float width,float height,boolean centered){
        if(centered){
            this.x=x-width/2;
            this.y=y-height/2;
        }else{
            this.x = x;
            this.y = y;
        }
        this.width=width;
        this.height=height;
    }
    public void update() {
        if(isVisible)isPressed=contains(Mouse.getX(),Mouse.getY())&&Mouse.isMousePressed();
        else isPressed=false;
    }

    public void render() {
        Graphics.setColor(1,1,1,1);
        Graphics.fillRect(x,y,width,height);
    }

    public boolean isPressed(){
        return isPressed;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
}
