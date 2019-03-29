package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import org.loader.ImageResource;

import java.awt.*;
import java.util.ArrayList;

import static org.graphics.Render.unitsTall;
import static org.graphics.Render.unitsWide;

public class Graphics {
    //Font vars
    public static int TITLE_FONT=0;
    public static int REGULAR_FONT=1;

    private static float red=0,green=0,blue=0,alpha=0;
    private static float rotation=0;
    private static int textSelector=0;
    private static TextRenderer title=new TextRenderer(new Font("Constantia",Font.PLAIN,100));
    private static TextRenderer regular=new TextRenderer(new Font("Constantia",Font.PLAIN,40));
    private static TextRenderer[] fonts={title,regular};

    public static void drawRect(float x,float y,float width,float height){
        GL2 gl=Render.getGL2();
        gl.glRotatef(-rotation,0,0,1);//Rotation needed to be reversed
        gl.glColor4f(red,green,blue,alpha);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(x,y);
        gl.glVertex2f(x+width,y);
        gl.glVertex2f(x+width,y+height);
        gl.glVertex2f(x,y+height);
        gl.glEnd();
        gl.glFlush();
        gl.glRotatef(rotation,0,0,1);
    }

    private static float convertToWorldHeight(float height){
        return height/(Render.getWindow().getHeight()/ unitsTall);
    }

    private static float convertToWorldWidth(float width){
        return width/(Render.getWindow().getWidth()/ unitsWide);
    }

    private static float convertToWorldY(float y){
        return y/(Render.getWindow().getWidth()/ unitsWide);
    }

    private static float convertFromWorldX(float x){
        return (Render.getWindow().getWidth()*x)/unitsWide;
    }

    private static float convertFromWorldY(float y){
        return (Render.getWindow().getHeight()*y)/unitsTall;
    }

    //Draw an image with width and height of original image
    public static void drawImage(ImageResource image,float x,float y){
        drawImage(image,x,y, convertToWorldWidth(image.getTexture().getWidth()),convertToWorldHeight(image.getTexture().getHeight()));
    }

    //Draw centered image
    public static void drawImageCentered(ImageResource image,float x,float y){
        drawImage(image,x- convertToWorldWidth(image.getTexture().getWidth())/2f,y-convertToWorldHeight(image.getTexture().getWidth())/2f);
    }

    //Draw centered image with preselected width and height
    public static void drawImageCentered(ImageResource image,float x,float y,float width,float height){
        drawImage(image,x- width/2f,y-height/2f,width,height);
    }

    //Draws an image
    public static void drawImage(ImageResource image,float x,float y, float width,float height){
        GL2 gl=Render.getGL2();
        Texture tex=image.getTexture();
        //TODO implement hiding code if image not needed
        if(tex!=null)gl.glBindTexture(GL2.GL_TEXTURE_2D,tex.getTextureObject());
        gl.glColor4f(red,green,blue,alpha);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0,1);
        gl.glVertex2f(x,y);
        gl.glTexCoord2f(1,1);
        gl.glVertex2f(x+width,y);
        gl.glTexCoord2f(1,0);
        gl.glVertex2f(x+width,y+height);
        gl.glTexCoord2f(0,0);
        gl.glVertex2f(x,y+height);
        gl.glEnd();
        gl.glFlush();
        gl.glBindTexture(GL2.GL_TEXTURE_2D,0);
        gl.glRotatef(-rotation,0,0,1);
    }

    //Draws text with specified wrapping width
    public static void drawText(String text,float x, float y, float wrap){
        StringBuilder currentString=new StringBuilder();
        ArrayList<String> strings=new ArrayList<String>();
        for(int i=0;i<text.length();i++){
            if(text.charAt(i)==' '||i==0){
                StringBuilder testString=new StringBuilder();
                String s;
                if(i==0)s=text.substring(i);
                else s=text.substring(i+1);
                int index=0;
                while(index<s.length()&&s.charAt(index)!=' '){
                    testString.append(s.charAt(index));
                    index++;
                }
                if(x+fonts[textSelector].getBounds(testString.toString()).getWidth()+fonts[textSelector].getBounds(currentString.toString()).getWidth()<wrap){
                    currentString.append(" ").append(testString);
                }else{
                    strings.add(currentString.toString());
                    currentString=testString;
                }
            }
        }
        int iteration=0;
        //Draw the text in the array
        for(String s:strings){
            drawText(s,x,y-convertToWorldY(fonts[textSelector].getFont().getSize()*iteration));
            iteration++;
        }
    }

    //Draws text
    public static void drawText(String text, float x, float y){
        fonts[textSelector].beginRendering(Render.getWindow().getWidth(),Render.getWindow().getHeight());
        fonts[textSelector].setColor(red,green,blue,alpha);
        fonts[textSelector].draw(text,(int)convertFromWorldX(x),(int)convertFromWorldY(y));
        fonts[textSelector].endRendering();
    }

    public static void setColor(float red,float green,float blue,float alpha){
        Graphics.red=red;
        Graphics.green=green;
        Graphics.blue=blue;
        Graphics.alpha=alpha;
    }

    public static void setRotation(float rotation){
        Graphics.rotation=rotation;
    }

    public static void setText(int text){
        textSelector=text;
    }
}
