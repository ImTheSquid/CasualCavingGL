package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import org.loader.ImageResource;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import static org.graphics.Render.unitsTall;
import static org.graphics.Render.unitsWide;

public class Graphics {
    //Font vars
    public static final int TITLE_FONT=0,REGULAR_FONT=1,SMALL_FONT=2,DEBUG_SMALL=3;

    private static float red=0,green=0,blue=0,alpha=0;
    private static float rotation=0,scaleFactor=1;
    private static int textSelector=0;
    private static boolean ignoreScale=false;
    private static TextRenderer title=new TextRenderer(new Font("Merriweather",Font.PLAIN,96));
    private static TextRenderer regular=new TextRenderer(new Font("Merriweather",Font.PLAIN,36));
    private static TextRenderer small=new TextRenderer(new Font("Merriweather",Font.PLAIN,18));
    private static TextRenderer debugSmall=new TextRenderer(new Font("Inconsolata",Font.PLAIN,22));
    private static TextRenderer[] fonts={title,regular,small,debugSmall};

    public static void fillRectCentered(float x, float y, float width, float height){
        fillRect(x-width/2f,y-height/2f,width,height);
    }

    public static void fillRect(float x, float y, float width, float height){
        float scaleSave=scaleFactor;
        if(ignoreScale){
            scaleFactor=1;
        }

        GL2 gl=Render.getGL2();
        gl.glRotatef(-rotation,0,0,1);//Rotation needed to be reversed
        gl.glColor4f(red,green,blue,alpha);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(x,y);
        gl.glVertex2f(x+width*scaleFactor,y);
        gl.glVertex2f(x+width*scaleFactor,y+height*scaleFactor);
        gl.glVertex2f(x,y+height*scaleFactor);
        gl.glEnd();
        gl.glFlush();
        gl.glRotatef(rotation,0,0,1);
        scaleFactor=scaleSave;
    }

    public static float convertToWorldHeight(float height){
        return height/(Render.getWindow().getHeight()/ unitsTall);
    }

    public static float convertToWorldWidth(float width){
        return width/(Render.getWindow().getWidth()/ unitsWide);
    }

    public static float convertFromWorldWidth(float width){return (Render.getWindow().getWidth()*width)/unitsWide;}

    public static float convertFromWorldHeight(float height){return (Render.getWindow().getHeight()*height)/unitsTall;}

    public static float convertToWorldY(float y){
        return y/(Render.getWindow().getWidth()/ unitsWide);
    }

    public static float convertToWorldX(float x){
        return x/(Render.getWindow().getWidth()/ unitsWide);
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
        float scaleSave=scaleFactor;
        if(ignoreScale){
            scaleFactor=1;
        }

        GL2 gl=Render.getGL2();
        Texture tex=image.getTexture();
        if(tex!=null)gl.glBindTexture(GL2.GL_TEXTURE_2D,tex.getTextureObject());
        gl.glColor4f(red,green,blue,alpha);
        gl.glBegin(GL2.GL_QUADS);
        gl.glTexCoord2f(0,1);
        gl.glVertex2f(x,y);
        gl.glTexCoord2f(1,1);
        gl.glVertex2f(x+width*scaleFactor,y);
        gl.glTexCoord2f(1,0);
        gl.glVertex2f(x+width*scaleFactor,y+height*scaleFactor);
        gl.glTexCoord2f(0,0);
        gl.glVertex2f(x,y+height*scaleFactor);
        gl.glEnd();
        gl.glFlush();
        gl.glBindTexture(GL2.GL_TEXTURE_2D,0);
        gl.glRotatef(-rotation,0,0,1);

        scaleFactor=scaleSave;
    }

    public static void drawText(String text,float x,float y,float wrapWidth){drawText(text, x, y, wrapWidth,false);}

    //Draws text with specified wrapping width
    public static void drawText(String text,float x, float y, float wrapWidth,boolean box){
        float wrap=convertFromWorldWidth(wrapWidth);
        StringBuilder currentString=new StringBuilder();
        ArrayList<String> strings=new ArrayList<>();
        for(int i=0;i<text.length();i++){
            StringBuilder testString=new StringBuilder();
            String s;
            if(text.charAt(i)==' '||i==0){
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
        strings.add(currentString.toString());//Clears buffer after loop
        strings.set(0,strings.get(0).substring(1));//Gets rid of space at beginning of first line
        int iteration=0;
        //Draw the text in the array
        if(box){
            float lengthMax=0;
            for(String s:strings){
                float newL=convertToWorldWidth((float)fonts[textSelector].getBounds(s).getWidth());
                if(newL>lengthMax)lengthMax=newL;
            }
            Graphics.setColor(0,0,0,0.5f);
            float yAdjustment=convertToWorldHeight((float)fonts[textSelector].getBounds(text).getHeight()*(strings.size()-1));
            Graphics.fillRect(x,y-yAdjustment,lengthMax,convertToWorldHeight((float)fonts[textSelector].getBounds(text).getHeight()*(strings.size())));
            Graphics.setColor(1,1,1,1);
        }
        for(String s:strings){
            drawText(s,x,y-convertToWorldY(fonts[textSelector].getFont().getSize()*iteration));
            iteration++;
        }
    }

    public static void drawTextCentered(String text,float x,float y){
        drawText(text,x-(convertToWorldWidth((float)fonts[textSelector].getBounds(text).getWidth())/2f),y-convertToWorldHeight((float)fonts[textSelector].getBounds(text).getHeight())/2f);
    }

    //Draws text
    public static void drawText(String text, float x, float y){
        fonts[textSelector].beginRendering(Render.getWindow().getWidth(),Render.getWindow().getHeight());
        fonts[textSelector].setColor(red,green,blue,alpha);
        fonts[textSelector].draw(text,(int)convertFromWorldX(x),(int)convertFromWorldY(y));
        fonts[textSelector].endRendering();
    }

    public static void drawTextWithBox(String text, float x, float y){
        Graphics.setColor(0,0,0,0.5f);
        Rectangle2D bounds=fonts[textSelector].getBounds(text);
        Graphics.fillRect(x,y-0.1f,Graphics.convertToWorldWidth((float)bounds.getWidth()),Graphics.convertToWorldHeight((float)bounds.getHeight())+0.1f);
        Graphics.setColor(1,1,1,1);
        drawText(text,x,y);
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

    public static void setFont(int text){
        textSelector=text;
    }

    public static TextRenderer getCurrentFont(){return fonts[textSelector];}

    public static void setScaleFactor(float scaleFactor) {
        Graphics.scaleFactor = scaleFactor;
    }

    public static float getScaleFactor() {
        return scaleFactor;
    }

    public static void setIgnoreScale(boolean ignore){ignoreScale=ignore;}

    public static boolean getIgnoreScale(){return ignoreScale;}
}
