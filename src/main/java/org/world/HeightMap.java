package org.world;

import org.entities.SmartRectangle;
import org.graphics.Render;

import java.util.ArrayList;

public class HeightMap {
    private static HeightVal[] heights={new HeightVal(0,7,Render.unitsWide,true)};
    private static boolean singleHeight=false;

    //TODO Add variable to store whether HM entry is opaque or transparent (able to be passed under/through), check on analysis

    /*
    Get height val (if not jumping)
    Go top to bottom to see what level player is on

    For collision check (LR)
    See if player x collides with OPAQUE object
    If so, stop
     */

    public static void setHeights(HeightVal[] heights) {
        HeightMap.heights=heights;
        processHeights();
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

    public static HeightVal onPlatform(SmartRectangle r){
        if(singleHeight||!onGround(r).isOnGround())return null;
        ArrayList<HeightVal> temp=findBounds(r);
        for(HeightVal h:temp){
            if(h.getHeight()<r.getY()){
                return h;
            }
        }
        return null;
    }

    private static ArrayList<HeightVal> findBounds(SmartRectangle r) {
        ArrayList<HeightVal> temp = new ArrayList<>();
        for (HeightVal h : heights) {
            boolean xBound = (r.getX() > h.getStartX() && r.getX() <= h.getEndX());
            boolean widthBound = (r.getX() + r.getWidth() > h.getStartX() && r.getX() + r.getWidth() <= h.getEndX());
            if (xBound || widthBound) temp.add(h);
        }
        return sort(temp);
    }

    private static ArrayList<HeightVal> sort(ArrayList<HeightVal> input){
        int n=input.size();
        for(int i=0;i<n;i++){
            HeightVal key=input.get(i);
            int j=i-1;
            while(j>=0&&input.get(j).getHeight()>key.getHeight()){
                input.set(j+1,input.get(j));
                j--;
            }
            input.set(j+1,key);
        }
        return input;
    }


    //Returns true if collision detected, false if not
    public static boolean checkRightCollision(SmartRectangle r){
        HeightVal current=findApplicable(r,true);
        if(current==null)return false;
        return !(r.getY()>current.getHeight());
    }

    public static boolean checkLeftCollision(SmartRectangle r){
        HeightVal current=findApplicable(r,false);
        if(current==null)return false;
        return !(r.getY()>current.getHeight());
    }

    public static HeightVal findApplicable(SmartRectangle r,boolean right){
        float xPos;
        if(right)xPos=r.getX()+r.getWidth();
        else xPos=r.getX();
        HeightVal val=null;
        for(int i=0;i<heights.length;i++){
            if(xPos>=heights[i].getStartX()&&xPos<=heights[i].getEndX()){
                if(right&&i+1<heights.length&&r.getY()<heights[i+1].getHeight())val=heights[i+1];
                else if(!right&&i-1>=0&&r.getY()<heights[i-1].getHeight())val=heights[i-1];
                //break;
            }
        }
        if(val!=null&&!val.isOpaque())return null;
        return val;
    }


}
