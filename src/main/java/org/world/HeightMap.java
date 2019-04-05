package org.world;

import org.entities.SmartRectangle;
import org.graphics.Render;

import java.util.ArrayList;

public class HeightMap {
    private static HeightVal[] heights={new HeightVal(0,7,Render.unitsWide)};
    private static boolean singleHeight=false;

    public static void setHeights(float[][] heights) {
        HeightMap.heights=convertHeights(heights);
        System.out.println(HeightMap.heights.length);
        processHeights();
    }

    private static HeightVal[] convertHeights(float[][] height){
        ArrayList<HeightVal> temp=new ArrayList<>();
        for (int i = 0; i < height.length; i++) {
            float x;
            if(i<height.length-1){
                x=height[i+1][0];
            }else{
                x= Render.unitsWide;
            }
            temp.add(new HeightVal(height[i][0],height[i][1],x));
        }
        HeightVal[] newHeights=new HeightVal[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            newHeights[i]=temp.get(i);
        }
        return newHeights;
    }

    private static void processHeights(){
        singleHeight= heights.length == 1;
    }

    public static HeightReturn onGround(SmartRectangle r){
        if(singleHeight){
            if(r.getY()<=heights[0].getHeight())return new HeightReturn(true,heights[0].getHeight());
        }else{
            ArrayList<HeightVal> temp=findBounds(r);
            for(HeightVal h:temp){
                if(r.getY()<=h.getHeight())return new HeightReturn(true,h.getHeight());
            }
        }
        return new HeightReturn(false);
    }

    private static ArrayList<HeightVal> findBounds(SmartRectangle r){
        ArrayList<HeightVal> temp=new ArrayList<>();
        for(HeightVal h:heights){
            boolean xBound=(r.getX()>h.getStartX()&&r.getX()<=h.getEndX());
            boolean widthBound=(r.getX()+r.getWidth()>h.getStartX()&&r.getX()+r.getWidth()<=h.getEndX());
            if(xBound||widthBound)temp.add(h);
        }
        return temp;
    }

}
