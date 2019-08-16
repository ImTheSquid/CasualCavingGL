package org.engine;

import org.entities.Harold;
import org.graphics.Render;
import org.loader.ResourceHandler;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Main {
    private static Harold harold=new Harold();
    public static void main(String[] args) {
        initFonts();
        AudioManager.setup();
        DiscordHandler.init();
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

    public static void openURL(URL url){
        try {
            openURI(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static void openURI(URI uri){
        Desktop desktop=Desktop.isDesktopSupported()?Desktop.getDesktop():null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
