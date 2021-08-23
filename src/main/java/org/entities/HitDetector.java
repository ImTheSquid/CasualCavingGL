package org.entities;

public class HitDetector extends SmartRectangle {
    private final Runnable hitEvent;
    private String attacker = null;
    public HitDetector(int subLevel,float spawnX, float spawnY,float width,float height,Runnable hitEvent,String attacker) {
        super(spawnX, spawnY, width, height, false, null);
        this.hitEvent = hitEvent;
        invincible=true;
        this.subLevel=subLevel;
        this.attacker=attacker;
    }

    public HitDetector(int subLevel,float spawnX, float spawnY,float width,float height,Runnable hitEvent) {
        super(spawnX, spawnY, width, height, false, null);
        this.hitEvent = hitEvent;
        invincible=true;
        this.subLevel=subLevel;
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void doDamage(Entity attacker, int damage) {
        if(attacker==null|| attacker.getDisplayName().equals(this.attacker))hitEvent.run();
    }
}
