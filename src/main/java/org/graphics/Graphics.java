package org.graphics;

import com.jogamp.opengl.GL2;
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

    public void drawImage(ImageResource image,float x,float y){
        drawImage(image,x,y,image.getTexture().getWidth(),image.getTexture().getHeight());
    }

    public void drawImage(ImageResource image,float x,float y, float width,float height){

    }
}
