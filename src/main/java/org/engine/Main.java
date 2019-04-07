package org.engine;

import org.entities.Harold;
import org.graphics.Render;
import org.world.World;

public class Main {
    private static Harold harold=new Harold();
    public static void main(String[] args) {
        new Render();
        World.addEntity(harold);
    }

    public static Harold getHarold() {
        return harold;
    }
}
