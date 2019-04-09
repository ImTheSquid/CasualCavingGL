package org.world;

import org.entities.SmartRectangle;
import org.graphics.Render;

import java.util.ArrayList;

public class HeightMap {
    private static HeightVal[] heights={new HeightVal(0,7,Render.unitsWide)};
    private static boolean singleHeight=false;

    //TODO Add variable to store whether HM entry is opaque or transparent (able to be passed under/through), check on analysis

    public static void setHeights(float[][] heights) {
        HeightMap.heights=convertHeights(heights);
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

    /*
     * check if anywhere on player interferes with height map, stop if true
     */

    //Returns true if collision detected, false if not
    public static boolean checkRightCollision(SmartRectangle r){
        float xCheck=r.getX()+r.getWidth();
        HeightVal current=findApplicable(xCheck,true);
        if(current==null)return false;
        return !(r.getY()>current.getHeight());
    }

    public static boolean checkLeftCollision(SmartRectangle r){
        HeightVal current=findApplicable(r.getX(),false);
        if(current==null)return false;
        return !(r.getY()>current.getHeight());
    }

    public static HeightVal findApplicable(float xPos,boolean right){
        HeightVal val=null;
        for(int i=0;i<heights.length;i++){
            if(xPos>=heights[i].getStartX()&&xPos<=heights[i].getEndX()){
                if(right&&i+1<heights.length)val=heights[i+1];
                else if(!right&&i-1>=0)val=heights[i-1];
                break;
            }
        }
        return val;
    }


}
