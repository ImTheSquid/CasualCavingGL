package org.loader;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import org.graphics.Render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageResource implements Comparable{
    private Texture texture=null;
    private BufferedImage image=null;
    public ImageResource(File f){
        readFile(f.getPath());
    }

    public ImageResource(String path){
        readFile(path);
    }

    public ImageResource(URL url){
        readFile(url.getFile());
    }

    private void readFile(String p){
        URL url=ImageResource.class.getResource(p);
        try{
            image= ImageIO.read(url);
        }catch(IOException e){
            e.printStackTrace();
        }
        if(image!=null){
            image.flush();
        }else{
            System.err.println("Image with path \""+p+"\" is null.");
        }
    }

    public Texture getTexture(){
        if(image==null)return null;
        if(texture==null)texture= AWTTextureIO.newTexture(Render.getProfile(),image,true);
        return texture;
    }

    public void preloadTexture(){
        if(image==null)return;
        if(texture==null)texture= AWTTextureIO.newTexture(Render.getProfile(),image,true);
    }

    private BufferedImage getImage() {
        return image;
    }

    //Returns -1 if sizes are different, or any integer other than 0 corresponding to the number of pixel differences in the image
    @Override
    public int compareTo(Object o) {
        ImageResource imageResource=(ImageResource)o;
        int diffs=0;
        BufferedImage b=imageResource.getImage();
        if(image==null||b==null)return -1;
        if(image.getWidth()==b.getWidth()&&image.getHeight()==b.getHeight()){
            for(int x=0;x<b.getWidth();x++){
                for(int y=0;y<b.getHeight();y++){
                    if(image.getRGB(x,y)!=b.getRGB(x,y))diffs++;
                }
            }
        }else diffs--;
        return diffs;
    }
}
