package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
import org.loader.ImageResource;

public class Graphics {
    private static float red=0,green=0,blue=0,alpha=0;
    private static float rotation=0;

    public static void drawRect(float x,float y,float width,float height){
        GL2 gl=Render.getGL2();
        gl.glTranslatef(x,y,0);
        gl.glRotatef(-rotation,0,0,1);//Rotation needed to be reversed
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(x,y);
        gl.glVertex2f(x+width,y);
        gl.glVertex2f(x+width,y+height);
        gl.glVertex2f(x,y+height);
        gl.glEnd();
        gl.glFlush();
        gl.glRotatef(rotation,0,0,1);
        gl.glTranslatef(-x,-y,0);
    }

    public static void drawImage(ImageResource image,float x,float y){
        drawImage(image,x,y,image.getTexture().getWidth(),image.getTexture().getHeight());
    }

    public static void drawImage(ImageResource image,float x,float y, float width,float height){
        GL2 gl=Render.getGL2();
        Texture tex=image.getTexture();
        //TODO implement hiding code if image not needed
        if(tex!=null)gl.glBindTexture(GL2.GL_TEXTURE_2D,tex.getTextureObject());
        gl.glTranslatef(x,y,0);
        gl.glRotatef(-rotation,0,0,1);
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
        gl.glTranslatef(-x,-y,0);
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
}
