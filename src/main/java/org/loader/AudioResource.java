package org.loader;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class AudioResource {
    private Clip clip=null;
    private AudioInputStream ais=null;
    public AudioResource(String path){
        try {
            URL url=AudioResource.class.getResource(path);
            ais= AudioSystem.getAudioInputStream(url);
            clip=AudioSystem.getClip();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Clip getClip(){
        try {
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clip;
    }
}
