package org.entities;

public abstract class Entity {
    protected float x=5,y=7,vX,vY,width,height;
    float red=1,green=1,blue=1,alpha=1;
    protected int health=1,maxHealth=-1,level=0,subLevel=0, damageTakenFrame =0,damageCooldown=0,attackCooldown=0;
    private boolean nonGameUpdate=false,nonGameRender=false,pauseUpdate=false,pauseRender=true;
    protected boolean movement=true,visible=true,direction=true,attackerBehind=false,invincible=false;
    protected String displayName="Entity";
    public abstract void update();
    public abstract void render();
    public abstract void reset();
    public String toString(){
        return this.getClass().getSimpleName()+" @ "+x+","+y;
    }

    protected void setNonGameUpdate(boolean update){
        nonGameUpdate=update;
    }

    protected void setNonGameRender(boolean render){
        nonGameRender=render;
    }

    public boolean getNonGameUpdate(){return nonGameUpdate;}

    public boolean getNonGameRender(){
        return nonGameRender;
    }

    public boolean getPauseUpdate(){return pauseUpdate;}

    public boolean getPauseRender(){return pauseRender;}

    public float getX(){return x;}

    public float getY(){return y;}

    public float getWidth(){return width;}

    public float getHeight(){return height;}

    public int getLevel() {
        return level;
    }

    public int getSubLevel() {
        return subLevel;
    }

    public int getHealth() {
        return health;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setMovement(boolean movement) {
        this.movement = movement;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setvX(float vX) {
        this.vX = vX;
    }

    public void setvY(float vY) {
        this.vY = vY;
    }

    public boolean isFacingRight(){return direction;}

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public void doDamage(Entity attacker, int damage){
        if(invincible||damageCooldown>0)return;
        health-=damage;
        damageTakenFrame =10;
        damageCooldown=20;
        if(direction)attackerBehind=attacker.getX()<x;
        else attackerBehind=attacker.getX()>x;
    }

    public void giveHealth(int health){
        if(maxHealth==-1||this.health+health<=maxHealth)this.health+=health;
    }

    public void handleDeath(){}

    public int getMaxHealth() {
        return maxHealth;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isMobile() {
        return movement;
    }

    public float getvX() {
        return vX;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
