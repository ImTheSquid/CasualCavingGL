package org.entities;

import org.graphics.Graphics;
import org.input.Mouse;

public class SmartRectangle extends Entity{
    private boolean isPressed=false, isActive =true,isHovering=false;
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
        if(isActive){
            isPressed=contains(Mouse.getX(),Mouse.getY())&&Mouse.isMousePressed();
            isHovering=contains(Mouse.getX(),Mouse.getY());
        }
        else{
            isPressed=false;
            isHovering=false;
        }
    }

    public void render() {
        Graphics.setColor(red,green,blue,alpha);
        Graphics.fillRect(x,y,width,height);
    }

    public boolean isPressed(){
        return isPressed;
    }

    public boolean isHovering() {
        return isHovering;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {return isActive;}

    public void setColor(float r,float g,float b,float a){
        red=r;
        green=g;
        blue=b;
        alpha=a;
    }
}
