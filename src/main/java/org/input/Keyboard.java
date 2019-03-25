package org.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import java.util.HashSet;
import java.util.Set;

public class Keyboard implements KeyListener {
    Set<Short> keys=new HashSet<Short>();
    public void keyPressed(KeyEvent keyEvent) {
        keys.add(keyEvent.getKeyCode());
    }

    public void keyReleased(KeyEvent keyEvent) {
        if(!keyEvent.isAutoRepeat())keys.remove(keyEvent.getKeyCode());
    }
}
