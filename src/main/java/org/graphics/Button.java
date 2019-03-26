package org.graphics;

public class Button extends Graphics{
    private float x,y,width,height;
    private String text;
    public Button(float x,float y,float width,float height,String text){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.text=text;
    }

    void setWidth(float width) {
        this.width = width;
    }

    void setHeight(float height) {
        this.height = height;
    }

    void setText(String text) {
        this.text = text;
    }
}
