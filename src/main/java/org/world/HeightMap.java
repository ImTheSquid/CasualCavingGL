package org.world;

import org.entities.SmartRectangle;
import org.graphics.Render;

import java.util.ArrayList;

public class HeightMap {
    private static HeightVal[] heights={new HeightVal(0,7,Render.unitsWide,true)};
    private static boolean singleHeight=false;

    public static void setHeights(HeightVal[] heights) {
        HeightMap.heights=heights;
        singleHeight= heights.length == 1;
    }

    //Checks for whether a SmartRectangle is on the ground
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

    //For being able to drop below platforms
    //Calculates the HeightVal below the platform, if there is one
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

    //Returns whether a SmartRectangle is on the edge of a platform
    public static boolean onEdge(SmartRectangle r,boolean right){
        ArrayList<HeightVal> applicable=findBounds(r);
        if(applicable.size()==0)return false;
        if(right){
            return applicable.size() == 1 && r.getX() + r.getWidth() > applicable.get(0).getEndX();
        }else{
            return applicable.size() == 1 && r.getX() < applicable.get(0).getStartX();
        }
    }

    //Find all HeightMaps that a SmartRectangle intersects with
    private static ArrayList<HeightVal> findBounds(SmartRectangle r) {
        ArrayList<HeightVal> temp = new ArrayList<>();
        for (HeightVal h : heights)
            if (new SmartRectangle(h.getStartX(), h.getHeight(), h.getEndX() - h.getStartX(), 1).intersects(r))
                temp.add(h);
        return sort(temp);
    }

    //Sorts an ArrayList of Heights from lowest to highest
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

    //Calculates the current height map (if opaque)
    public static HeightVal findApplicable(SmartRectangle r,boolean right){
        float xPos;
        if(right)xPos=r.getX()+r.getWidth();
        else xPos=r.getX();
        HeightVal val=null;
        for(int i=0;i<heights.length;i++){
            if(xPos>=heights[i].getStartX()&&xPos<=heights[i].getEndX()){
                if(right&&i+1<heights.length&&r.getY()<heights[i+1].getHeight())val=heights[i+1];
                else if(!right&&i-1>=0&&r.getY()<heights[i-1].getHeight())val=heights[i-1];
            }
        }
        if(val!=null&&!val.isOpaque())return null;
        return val;
    }


}
