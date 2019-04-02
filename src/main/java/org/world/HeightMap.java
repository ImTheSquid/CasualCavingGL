package org.world;

import org.entities.SmartRectangle;

public class HeightMap {
    private static float[][] heights;
    private static float soloHeight=0;
    private static boolean singleHeight=false;

    public static void setHeights(float[][] heights) {
        HeightMap.heights = heights;
    }

    private static void processHeights(){
        if(heights.length==1){
            soloHeight=heights[0][1];
            singleHeight=true;
        }else{
            singleHeight=false;
        }
    }

    public static HeightReturn onGround(SmartRectangle r){
        for(int i=0;i<heights.length;i++){
            if(singleHeight){

            }
        }
        return new HeightReturn(false);
    }
}
