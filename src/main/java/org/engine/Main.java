package org.engine;

import org.entities.Harold;
import org.graphics.Render;
import org.world.World;

public class Main {
    public static void main(String[] args) {
        new Render();
        World.addEntity(new Harold());
    }
}
