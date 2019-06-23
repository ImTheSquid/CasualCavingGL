package org.engine;

import org.loader.AudioResource;
import org.world.World;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.ArrayList;

public class AudioManager {
    public static final float MUSIC_VOL=-10;
    public static final int PLAY=0,PAUSE=1,STOP=2,RESTART=3,RESUME=4;
    public static final int OVERWORLD=0;
    private static Clip music=null;
    private static ArrayList<Clip> album=new ArrayList<>();
    private static long musicPos=0;
    private static int musicStat=STOP;
    private static boolean musicEnabled=true;
    private static FloatControl volume=null;
    private static int currentTrack=0;
    private static final int[][] musicDirectory={{1,2},
            {4,5,6,7,8,9}};

    static void setup(){
        loadMusic();
    }

    private static void loadMusic(){
        album.add(new AudioResource("/CasualCaving/Audio/Overworld.wav").getClip());
    }

    public static void setMusic(int clip) {
        if(clip>album.size()-1)return;
        currentTrack=clip;
        music = album.get(clip);
        music.loop(-1);
    }

    public static void setMusicGain(float gain){
        if(music==null)return;
        if(volume==null)volume=(FloatControl)music.getControl(FloatControl.Type.MASTER_GAIN);
        if(gain>volume.getMaximum()||gain<volume.getMinimum())return;
        volume.setValue(gain);
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
        setMusicGain(MUSIC_VOL);
        setMusic(OVERWORLD);
    }

    public static void handlePause(boolean pause){
        if(pause&&musicStat==PLAY)setMusicPlayback(PAUSE);
        else if(!pause&&musicStat==PAUSE)setMusicPlayback(PLAY);
    }

    public static boolean isMusicEnabled(){return musicEnabled;}

    public static void setMusicEnabled(boolean musicEnabled) {
        AudioManager.musicEnabled = musicEnabled;
    }

    public static void fadeOut(){
        float x=-80+Math.abs(volume.getMinimum()-MUSIC_VOL)*World.getMaster().getCurrent();
        setMusicGain(x);
        if(x==-80)setMusicPlayback(PAUSE);
    }

    public static void handleLevelTransition(int nextLevel){
        setMusicGain(MUSIC_VOL);
        int next=findNextTrack(nextLevel);
        if(next==-1)setMusicPlayback(STOP);
        else{
            if(next!=currentTrack){
                setMusicPlayback(STOP);
                setMusic(next);
                setMusicPlayback(RESTART);
            }
            else setMusicPlayback(PLAY);
        }
    }

    private static int findNextTrack(int nextLevel){
        for(int i=0;i<musicDirectory.length;i++){
            for(int level:musicDirectory[i]){
                if(nextLevel==level){
                    return i;
                }
            }
        }
        return -1;
    }

    public static void handleDebugSwitch(int nextLevel){
        if(nextLevel!=1)setMusicPlayback(STOP);
        else{
            setMusic(0);
            setMusicPlayback(PLAY);
        }
    }
}
