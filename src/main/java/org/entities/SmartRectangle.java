package org.entities;

import org.graphics.Graphics;
import org.input.Mouse;

public class SmartRectangle extends Entity{
    private boolean isPressed=false, isActive =true,isHovering=false,isCentered=false;
    private float originX,originY;
    public SmartRectangle(float x,float y,float width,float height){
        this.x=x;
        this.y=y;
        originX=x;
        originY=y;
        this.width=width;
        this.height=height;
    }
    public SmartRectangle(float x,float y,float width,float height,boolean centered){
        if(centered){
            this.x=x-width/2;
            this.y=y-height/2;
            isCentered=true;
        }else{
            this.x = x;
            this.y = y;
        }
        originX=x;
        originY=y;
        this.width=width;
        this.height=height;
    }

    public void updateBounds(float x,float y,float width,float height){
        this.x=x;
        this.y=y;
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

    public boolean contains(float x1,float y1){
        return (y1 >= y && y1 <= y + height)&&(x1>=x&&x1<=x+width);
    }

    public boolean intersects(SmartRectangle r){
        return x < r.getX() + r.getWidth() && x + width > r.getX() && y < r.getY() + r.getHeight() && y + height > r.getY();
    }

    public void render() {
        Graphics.setColor(red,green,blue,alpha);
        Graphics.fillRect(x,y,width,height);
    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        return "Smart Rectangle @ "+x+","+y;
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

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        if(isCentered)x=originX-width/2;
    }
}
