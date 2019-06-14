package org.engine;

import org.loader.AudioResource;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.ArrayList;

public class AudioManager {
    public static final int PLAY=0,PAUSE=1,STOP=2,RESTART=3,RESUME=4;
    private static Clip music=null;
    private static ArrayList<Clip> album=new ArrayList<>();
    private static long musicPos=0;
    private static int musicStat=STOP;
    private static boolean musicEnabled=true;

    static void loadMusic(){
        album.add(new AudioResource("/CasualCaving/Audio/Overworld.wav").getClip());
    }

    public static void setMusic(int clip) {
        music = album.get(clip);
        music.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void setMusicVolume(int gain){
        if(music==null)return;
        FloatControl volume=(FloatControl)music.getControl(FloatControl.Type.MASTER_GAIN);
        if(gain>volume.getMaximum()||gain<volume.getMinimum())return;
        volume.setValue(gain);
        System.out.println(volume.getMinimum()+"/"+volume.getValue()+"/"+volume.getMaximum());
    }

    public static void setMusicPlayback(int type){
        if(music==null)return;
        if(!musicEnabled){
            music.stop();
            return;
        }
        switch(type){
            case PLAY:
                music.start();
                musicStat=PLAY;
                break;
            case PAUSE:
                musicPos=music.getMicrosecondPosition();
                music.stop();
                musicStat=PAUSE;
                break;
            case STOP:
                musicPos=0;
                music.stop();
                music.setMicrosecondPosition(0);
                break;
            case RESTART:
                music.stop();
                music.setMicrosecondPosition(0);
                musicPos=0;
                music.start();
                musicStat=PLAY;
            case RESUME:
                if(musicStat==PLAY)break;
                music.setMicrosecondPosition(musicPos);
                music.start();
                musicStat=PLAY;
        }
    }

    public static void cleanup(){
        if(music!=null)music.close();
        for(Clip c:album)if(c!=null)c.close();
    }

    public static void resetGame(){
        setMusicPlayback(STOP);
        setMusic(0);
    }

    public static void handlePause(boolean pause){
        if(pause&&musicStat==PLAY)setMusicPlayback(PAUSE);
        else if(!pause&&musicStat==PAUSE)setMusicPlayback(PLAY);
    }

    public static boolean isMusicEnabled(){return musicEnabled;}

    public static void setMusicEnabled(boolean musicEnabled) {
        AudioManager.musicEnabled = musicEnabled;
    }

    public static void handleLevelTransition(int nextLevel){
        if(nextLevel!=1)setMusicPlayback(STOP);
        else{
            setMusic(0);
            setMusicPlayback(PLAY);
        }
    }

    public static void handleDebugSwitch(int nextLevel){
        if(nextLevel!=1)setMusicPlayback(STOP);
        else{
            setMusic(0);
            setMusicPlayback(PLAY);
        }
    }
}
