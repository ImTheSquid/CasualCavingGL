package org.engine;

import org.entities.Harold;
import org.graphics.Render;
import org.loader.ResourceHandler;

import java.awt.*;
import java.io.IOException;

public class Main {
    private static Harold harold=new Harold();
    public static void main(String[] args) {
        initFonts();
        AudioManager.setup();
        new Render();
    }

    public static Harold getHarold() {
        return harold;
    }

    private static void initFonts(){
        try{
            GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ResourceHandler.getMiscLoader().getInconsolata().openStream()));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,ResourceHandler.getMiscLoader().getMerriweather().openStream()));
        }catch (IOException e){
            System.out.println("ERROR! No file found");
        }catch (FontFormatException e){
            System.out.println("ERROR! Incorrect font type");
        }
    }
}
