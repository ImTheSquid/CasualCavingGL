package org.level;

import org.entities.Entity;
import org.graphics.Render;
import org.loader.ImageResource;
import org.world.World;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Level {
    private int subLevels;
    protected float leftBound=0,rightBound=Render.unitsWide;//Points to trigger switch to next sublevel
    protected float leftLimit=-1,rightLimit=Render.unitsWide+1;//Points that entities can't go past
    protected ImageResource[] backgrounds,foregrounds;
    protected ConcurrentLinkedQueue<Entity> entityRegister=new ConcurrentLinkedQueue<>();

    public Level(ImageResource[] backgrounds,int subLevels){
        this.backgrounds=backgrounds;
        this.subLevels=subLevels;
    }

    public Level(ImageResource[][] backgrounds,int subLevels){
        ImageResource[] fore=new ImageResource[subLevels];
        ImageResource[] back=new ImageResource[subLevels];
        for(int i=0;i<fore.length;i++){
            back[i]=backgrounds[i][0];
            if(backgrounds[i].length>1)fore[i]=backgrounds[i][1];
        }
        this.backgrounds=back;
        this.foregrounds=fore;
        this.subLevels=subLevels;
    }

    public abstract void init();

    public abstract ImageResource[] getAssets();

    public abstract void update(int subLevel);

    public abstract void render(int subLevel);

    public abstract void renderForeground(int subLevel);

    //Execute on transition to next level
    public abstract void cleanup();

    public abstract void reset();

    public int getNumSublevels(){
        return subLevels;
    }

    public float getLeftBound() {
        return leftBound;
    }

    public float getRightBound() {
        return rightBound;
    }

    public float getLeftLimit() {
        return leftLimit;
    }

    public float getRightLimit() {
        return rightLimit;
    }

    public Entity[] getEntityRegisterArray() {
        Object[] registerArr=entityRegister.toArray();
        ArrayList<Entity> applicable=new ArrayList<>();
        for(Object o:registerArr){
            if(((Entity)o).getSubLevel()== World.getSubLevel()){
                applicable.add((Entity)o);
            }
        }
        Entity[] arr=new Entity[applicable.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i]=applicable.get(i);
        }
        return arr;
    }

    public ConcurrentLinkedQueue<Entity> getEntityRegister(){return entityRegister;}

    public void clearEntityRegister(){
        entityRegister.clear();
    }

    protected void checkHealthVals(){
        for(Entity e:entityRegister){
            if(e.getHealth()<=0||e.getY()+e.getHeight()<-10){
                e.handleDeath();
                entityRegister.remove(e);
            }
        }
        World.clearEntites();
        World.addEntities(entityRegister);
    }
}
