package org.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.texture.Texture;
import org.loader.ImageResource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static com.jogamp.opengl.GL.*;
import static com.jogamp.opengl.GL2.GL_POLYGON;
import static java.lang.Math.cos;
import static java.lang.StrictMath.sin;
import static org.graphics.Render.unitsTall;
import static org.graphics.Render.unitsWide;

public class Graphics {
    //Font vars
    public static final int TITLE =0, NORMAL =1, SMALL =2,DEBUG_SMALL=3,SMALL_BOLD=4;

    private static float red=0,green=0,blue=0,alpha=0;
    private static float rotation=0,scaleFactor=1;
    private static int textSelector=0;
    private static boolean ignoreScale=false,followCamera=false;
    private static File screenshotOut=null,screenshotDir=null;
    private static TextRenderer title=new TextRenderer(new Font("Merriweather",Font.PLAIN,96),true,true);
    private static TextRenderer regular=new TextRenderer(new Font("Merriweather",Font.PLAIN,36),true,true);
    private static TextRenderer small=new TextRenderer(new Font("Merriweather",Font.PLAIN,18),true,true);
    private static TextRenderer smallBold=new TextRenderer(new Font("Merriweather",Font.BOLD,18),true,true);
    private static TextRenderer debugSmall=new TextRenderer(new Font("Inconsolata",Font.PLAIN,22),true,true);
    private static TextRenderer[] fonts={title,regular,small,debugSmall,smallBold};

    public static void fillRectCentered(float x, float y, float width, float height){
        fillRect(x-width/2f,y-height/2f,width,height);
    }

    public static void fillRect(float x, float y, float width, float height){
        float scaleSave=scaleFactor;
        if(ignoreScale){
            scaleFactor=1;
        }
        if(followCamera){
            x+=Render.getCameraX();
            y+=Render.getCameraY();
        }

        GL2 gl=Render.getGL2();
        gl.glTranslatef(x+width/2,y+height/2,0);
        gl.glRotatef(-rotation,0,0,1);
        gl.glTranslatef(-(x+width/2),-(y+height/2),0);
        gl.glColor4f(red,green,blue,alpha);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(x,y);
        gl.glVertex2f(x+width*scaleFactor,y);
        gl.glVertex2f(x+width*scaleFactor,y+height*scaleFactor);
        gl.glVertex2f(x,y+height*scaleFactor);
        gl.glEnd();
        gl.glFlush();
        gl.glTranslatef(x+width/2,y+height/2,0);
        gl.glRotatef(rotation,0,0,1);
        gl.glTranslatef(-(x+width/2),-(y+height/2),0);
        scaleFactor=scaleSave;
    }

    //TODO Add scaling support to draw and fill circle methods
    public static void drawCircle(float x, float y, float radius){
        final float DEG2RAD=(float)Math.PI/180;
        GL2 gl=Render.getGL2();
        gl.glBegin(GL_LINE_LOOP);

        for (int i=0; i<360; i++){
            float degInRad = i*DEG2RAD;
            gl.glVertex2f(x+(float)cos(degInRad)*radius,y+(float)sin(degInRad)*radius);
        }

        gl.glEnd();
    }

    public static void fillCircle(float x,float y,float radius){
        GL2 gl=Render.getGL2();
        gl.glBegin(GL_POLYGON);

        double angle1 = 0.0;
        gl.glVertex2d( x+radius * cos(0.0) , y+radius * sin(0.0));

        int i;
        for (i = 0; i < 360; i++)
        {
            gl.glVertex2d(x+(radius * cos(angle1)), y+(radius *sin(angle1)));
            angle1 += 2*Math.PI/360 ;
        }

        gl.glEnd();
        gl.glFlush();
    }

    public static float toWorldHeight(float height) {
        return height / (Render.getWindow().getHeight() / unitsTall);
    }

    public static float toWorldWidth(float width) {
        return width / (Render.getWindow().getWidth() / unitsWide);
    }

    public static float fromWorldWidth(float width) {
        return (Render.getWindow().getWidth() * width) / unitsWide;
    }

    public static float fromWorldHeight(float height) {
        return (Render.getWindow().getHeight() * height) / unitsTall;
    }

    public static float toWorldY(float y) {
        return y / (Render.getWindow().getWidth() / unitsWide);
    }

    public static float toWorldX(float x) {
        return x / (Render.getWindow().getWidth() / unitsWide);
    }

    private static float fromWorldX(float x) {
        return (Render.getWindow().getWidth() * x) / unitsWide;
    }

    private static float fromWorldY(float y) {
        return (Render.getWindow().getHeight() * y) / unitsTall;
    }

    //Draw an image with width and height of original image
    public static void drawImage(ImageResource image, float x, float y) {
        drawImage(image, x, y, toWorldWidth(image.getTexture().getWidth()), toWorldHeight(image.getTexture().getHeight()));
    }

    //Draw centered image
    public static void drawImageCentered(ImageResource image, float x, float y) {
        drawImage(image, x - toWorldWidth(image.getTexture().getWidth()) / 2f, y - toWorldHeight(image.getTexture().getWidth()) / 2f);
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
        if(followCamera){
            x+=Render.getCameraX();
            y+=Render.getCameraY();
        }

        GL2 gl=Render.getGL2();
        gl.glTranslatef(x+width/2,y+height/2,0);
        gl.glRotatef(-rotation,0,0,1);
        gl.glTranslatef(-(x+width/2),-(y+height/2),0);
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
        gl.glTranslatef(x+width/2,y+height/2,0);
        gl.glRotatef(rotation,0,0,1);
        gl.glTranslatef(-(x+width/2),-(y+height/2),0);

        scaleFactor=scaleSave;
    }

    public static void drawText(String text,float x,float y,float wrapWidth){drawText(text, x, y, wrapWidth,false);}

    //Draws text with specified wrapping width
    public static void drawText(String text,float x, float y, float wrapWidth,boolean box){
        StringBuilder currentString=new StringBuilder();
        ArrayList<String> strings=new ArrayList<>();
        ArrayList<Integer> lines=new ArrayList<>();//Lines to remove a character from in the front of the string
        lines.add(0);
        boolean newlineActivated=false;
        for(int i=0;i<text.length();i++){
            StringBuilder testString=new StringBuilder();
            String s;
            if(text.charAt(i)==' '||i==0||newlineActivated){
                if(i==0||newlineActivated)s=text.substring(i);
                else s=text.substring(i+1);
                int index=0;
                while(index<s.length()&&s.charAt(index)!=' '){
                    testString.append(s.charAt(index));
                    index++;
                }
                //TODO Fix bug with strings with no spaces not getting split properly
                float widthTest = toWorldWidth((float) (fonts[textSelector].getBounds(testString.toString()).getWidth() + fonts[textSelector].getBounds(currentString.toString()).getWidth()));
                if(widthTest<wrapWidth){
                    currentString.append(" ").append(testString);
                }else{
                    strings.add(currentString.toString());
                    currentString=testString;
                }
                newlineActivated=false;
            }else if(i+1<text.length()&&text.substring(i,i+2).equals("\\n")){//Deals with newline character (\\n) and adds following string to another line
                strings.add(currentString.substring(0,i+1));
                currentString=new StringBuilder();
                i+=1;
                newlineActivated=true;
                lines.add(strings.size());
            }
        }
        strings.add(currentString.toString());//Clears buffer after loop
        for(Integer i:lines){
            if(strings.get(i).length()>0)strings.set(i,strings.get(i).substring(1));
        }//Gets rid of space at beginning of first line
        int iteration=0;
        //Draw the text in the array
        if(box) {
            float lengthMax = 0;
            for (String s : strings) {
                float newL = toWorldWidth((float) fonts[textSelector].getBounds(s).getWidth());
                lengthMax = Math.max(lengthMax, newL);
            }
            setIgnoreScale(true);
            Graphics.setDrawColor(0, 0, 0, 0.5f);
            float yAdjustment = toWorldHeight((float) fonts[textSelector].getBounds(text).getHeight() * (strings.size() - 1));
            Graphics.fillRect(x, y - yAdjustment, lengthMax, toWorldHeight((float) fonts[textSelector].getBounds(text).getHeight() * (strings.size())));
            Graphics.setDrawColor(1, 1, 1, 1);
            setIgnoreScale(false);
        }
        for(String s:strings){
            drawText(s, x, y - toWorldY(fonts[textSelector].getFont().getSize() * iteration));
            iteration++;
        }
    }

    public static void drawTextCentered(String text,float x,float y){
        drawText(text, x - (toWorldWidth((float) fonts[textSelector].getBounds(text).getWidth()) / 2f), y - toWorldHeight((float) fonts[textSelector].getBounds(text).getHeight()) / 2f);
    }

    //Draws text
    public static void drawText(String text, float x, float y) {
        if (!followCamera) {
            x -= Render.getCameraX();
            y -= Render.getCameraY();
        }
        fonts[textSelector].beginRendering(Render.getWindow().getWidth(), Render.getWindow().getHeight());
        fonts[textSelector].setColor(red, green, blue, alpha);
        fonts[textSelector].draw(text, (int) fromWorldX(x), (int) fromWorldY(y));
        fonts[textSelector].endRendering();
    }

    public static void drawTextWithBox(String text, float x, float y) {
        Graphics.setDrawColor(0, 0, 0, 0.5f);
        Rectangle2D bounds = fonts[textSelector].getBounds(text);
        Graphics.fillRect(x, y - 0.1f, Graphics.toWorldWidth((float) bounds.getWidth()), Graphics.toWorldHeight((float) bounds.getHeight()) + 0.1f);
        Graphics.setDrawColor(1, 1, 1, 1);
        drawText(text, x, y);
    }

    public static void takeScreenshot(){
        GL2 gl=Render.getGL2();
        //Prepare an image object and read pixels from the currently bound texture
        BufferedImage screenshot=new BufferedImage(Render.virtual_width,Render.virtual_height, BufferedImage.TYPE_INT_RGB);
        java.awt.Graphics graphics=screenshot.getGraphics();
        ByteBuffer buffer= GLBuffers.newDirectByteBuffer(Render.virtual_width *Render.virtual_height *4);//Creates byte buffer with a size of the screen with 4 color slots each (RGBA)
        gl.glReadBuffer(GL_BACK);
        gl.glReadPixels(0,0,Render.virtual_width,Render.virtual_height,GL_RGBA,GL_UNSIGNED_BYTE,buffer);
        for(int h = 0; h<Render.virtual_height; h++){
            for(int w = 0; w<Render.virtual_width; w++){
                graphics.setColor(new Color((buffer.get()&0xff),(buffer.get()&0xff),(buffer.get()&0xff)));
                buffer.get();//Discard alpha
                graphics.drawRect(w,Render.virtual_height -h,1,1);//Fill one pixel of output image
            }
        }
        //Clear the buffer
        while(buffer.hasRemaining())buffer.get();
        //Format and save the image
        String dmy=Instant.now().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toString();
        String hms=Instant.now().atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS).toString().replace(':','-');
        hms=hms.substring(hms.indexOf("T") + 1,hms.indexOf("["));
        if(screenshotOut==null) {
            screenshotDir = selectDirectory();
        }
        screenshotOut = new File(screenshotDir.getPath() + "\\screenshot-" + dmy.substring(0, dmy.indexOf("T")) + "_" + hms.substring(0,hms.length()-6) + ".png");
        try {
            ImageIO.write(screenshot,"png",screenshotOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File selectDirectory(){
        JFileChooser chooser=new JFileChooser();
        chooser.setDialogTitle("Select screenshot output path (persistent)");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.showOpenDialog(null);
        return chooser.getSelectedFile();
    }

    public static void setDrawColor(float red, float green, float blue, float alpha){
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

    public static void setFollowCamera(boolean followCamera) {
        Graphics.followCamera = followCamera;
    }
}
