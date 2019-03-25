package org.loader;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import org.graphics.Render;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResource {
    private Texture texture=null;
    private BufferedImage image=null;
    ImageResource(File f){
        try{
            image= ImageIO.read(f);
        }catch(IOException e){
            e.printStackTrace();
        }
        if(image!=null){
            image.flush();
        }else{
            System.err.println("Error! Image is null.");
        }
    }

    Texture getTexture(){
        if(image==null)return null;
        if(texture==null)texture= AWTTextureIO.newTexture(Render.getProfile(),image,true);
        return texture;
    }
}
