package org.world;

import org.entities.SmartRectangle;

public class HeightMap {
    private static float[][] heights={{0,7}};
    private static boolean singleHeight=false;

    public static void setHeights(float[][] heights) {
        HeightMap.heights = heights;
        processHeights();
    }

    private static void processHeights(){
        singleHeight= heights.length == 1;
    }

    public static HeightReturn onGround(SmartRectangle r){
        for(int i=0;i<heights.length;i++){
            if(r.getX()>=heights[i][0]||singleHeight){
                if(singleHeight){
                    if(r.getY()<=7)return new HeightReturn(true,7);
                }
            }
        }
        return new HeightReturn(false);
    }
}
