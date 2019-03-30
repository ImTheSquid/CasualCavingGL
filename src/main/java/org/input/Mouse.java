package org.input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import org.graphics.Render;

public class Mouse implements MouseListener {
    private static int x=0,y=0;
    private static boolean mousePressed=false;

    public static float getX(){
        return x*Render.unitsWide/Render.getWindow().getWidth();
    }

    public static float getY(){
        return Render.unitsTall-(y*Render.unitsTall/Render.getWindow().getHeight());
    }

    public static boolean isMousePressed() {
        return mousePressed;
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        //System.out.println(getWorldX()+"/"+getWorldY());
    }

    public void mouseEntered(MouseEvent mouseEvent) {

    }

    public void mouseExited(MouseEvent mouseEvent) {

    }

    public void mousePressed(MouseEvent mouseEvent) {
        mousePressed=true;
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        mousePressed=false;
    }

    public void mouseMoved(MouseEvent mouseEvent) {
        x=mouseEvent.getX();
        y=mouseEvent.getY();
    }

    public void mouseDragged(MouseEvent mouseEvent) {

    }

    public void mouseWheelMoved(MouseEvent mouseEvent) {

    }
}
