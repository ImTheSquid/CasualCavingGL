package org.engine;

import org.entities.Harold;
import org.graphics.Render;

public class Main {
    private static Harold harold=new Harold();
    public static void main(String[] args) {
        AudioManager.setup();
        new Render();
    }

    public static Harold getHarold() {
        return harold;
    }
}
