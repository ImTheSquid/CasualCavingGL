package org.engine;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import org.world.World;

import java.time.Instant;

class DiscordHandler{
    private static long startTime=0;
    static void init(){
        startTime= Instant.now().toEpochMilli();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler((discordUser -> System.out.println("Welcome " + discordUser.username + "#" + discordUser.userId + "!"))).build();
        DiscordRPC.discordInitialize("611303571421659137", handlers,true);
    }

    static void shutdown(){
        DiscordRPC.discordShutdown();
    }

    static void updatePresence(){
        DiscordRichPresence rich=new DiscordRichPresence.Builder((World.getLevel()>0?" Part "+World.getLevel():"Not In-Game")).setDetails(getDetails())
                .setBigImage(getBigImage(),getBigImageDetails()).setSmallImage(getSmallImage(),Main.getHarold().isInvincible()?"Infinite health":Main.getHarold().getHealth()+" health").setStartTimestamps(startTime).build();
        DiscordRPC.discordUpdatePresence(rich);
    }

    private static String getDetails(){
        return getBigImageDetails();
    }

    private static String getSmallImage(){
        if(Main.getHarold().isInvincible()&&Main.getHarold().getHealth()>0)return "infinitehearts";
        return Main.getHarold().getHealth()+(Main.getHarold().getHealth()==1?"heart":"hearts");
    }

    private static String getBigImage(){
        switch(World.getLevel()){
            case 1:return "overworld";
            case 2:
                if(World.getSubLevel()<=5)return "lantern";
                else return "sunstone";
            case 3:
                if(World.getSubLevel()<=1)return "insunstone";
                else return "inthecaves";
            case 4:
                if(World.getSubLevel()==5)return "redmajor";
                else return "inthecaves";
            case 5:
                if(World.getSubLevel()<2)return "waiting";
                else if(World.getSubLevel()>2)return "waiting";
                else return "waiting";
            case 6: return "inthecaves";
            default:return "waiting";
        }
    }

    private static String getBigImageDetails(){
        switch(World.getLevel()){
            case -1: return "Dead";
            case 0: return "On Title Screen";
            case 1: return "In the Overworld";
            case 2:
                if(World.getSubLevel()<=5)return "Entering the Caves";
                else return "Finding the Sun Stone";
            case 3:
                if(World.getSubLevel()<=1)return "In the Sun Stone";
                else return "Deep in the Caves";
            case 4:
                if(World.getSubLevel()==5)return "Fighting Red Major";
                else return "Deep in the Caves";
            case 5:
                if(World.getSubLevel()<2)return "Meeting Larano";
                else if(World.getSubLevel()>2)return "Meeting Isolsi";
                else return "Fighting Larano";
            case 6: return "Deep in the Caves";
            default: return "Error";
        }
    }
}
