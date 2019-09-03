package org.graphics;

import org.loader.ImageResource;

public class Notification implements Comparable{
    private String title,message;
    private ImageResource icon=null;
    private FadeIO timer=new FadeIO(0,5,0,1,1);
    private int status=0;//0=out,1=wait,2=in,3=done
    private float x=100;
    private static final float width=25,height=6;
    public Notification(String title, String message, ImageResource icon){
        this.title=title;
        this.message=message;
        this.icon=icon;
        init();
    }

    public Notification(String title,String message){
        this.title=title;
        this.message=message;
        init();
    }

    private void init(){
        timer.setActive(false);
        timer.setDirection(true);
    }

    public void update(){
        switch(status){
            case 0:
                if(x>75){
                    x-=0.5;
                }else{
                    timer.setActive(true);
                    status++;
                }
                break;
            case 1:
                if(timer.getCurrent()==timer.getMax()){
                    timer.setActive(false);
                    status++;
                }
                break;
            case 2:
                if(x<101){
                    x+=0.5;
                }else{
                    status++;
                }
                break;
        }
        timer.update();
    }

    public void render(float y){
        Graphics.setFollowCamera(true);
        Graphics.setDrawColor(0.15f,0.15f,0.15f,1);
        Graphics.fillRect(x,y,width,height);
        Graphics.setDrawColor(1,1,1,1);
        if(icon!=null)Graphics.drawImage(icon,x+1,y+1,4,4);
        Graphics.setFont(Graphics.SMALL_BOLD);
        Graphics.drawText(title,x+6,y+4);
        Graphics.setFont(Graphics.SMALL);
        Graphics.drawText(message,x+6,y+2.5f,20);
        Graphics.setFollowCamera(false);
    }

    public boolean isDone(){
        return status==3;
    }

    public static float getHeight(){
        return height;
    }

    public static float getWidth() {
        return width;
    }

    private ImageResource getIcon() {
        return icon;
    }

    private String getMessage() {
        return message;
    }

    private String getTitle() {
        return title;
    }

    //Returns an integer, with values corresponding to the specific difference
    //1=title mismatch, 3=message mismatch, and 5=image mismatch, with various sums also possible
    @Override
    public int compareTo(Object o) {
        Notification n=(Notification)o;
        int ans=0;
        if(!title.equals(n.getTitle()))ans++;
        if(!message.equals(n.getMessage()))ans+=3;
        if(icon.compareTo(n.getIcon())!=0)ans+=5;
        return ans;
    }
}
