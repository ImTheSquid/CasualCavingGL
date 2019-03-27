package org.input;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import org.graphics.Render;

public class Mouse implements MouseListener {
    private static int x=0,y=0;

    public static float getWorldX(){
        return Render.unitsWide/Render.getWindow().getWidth()*x-Render.unitsWide/2 - Render.cameraX;
    }

    public static float getWorldY(){
        float unitsTall=Render.unitsWide*((float)Render.getWindow().getHeight()/Render.getWindow().getWidth());
        return -(unitsTall/ Render.getWindow().getHeight()*y-unitsTall/2)+Render.cameraY;
    }
    public void mouseClicked(MouseEvent mouseEvent) {
        x=mouseEvent.getX();
        y=mouseEvent.getY();
        System.out.println(getWorldX()+"/"+getWorldY());
    }

    public void mouseEntered(MouseEvent mouseEvent) {

    }

    public void mouseExited(MouseEvent mouseEvent) {

    }

    public void mousePressed(MouseEvent mouseEvent) {

    }

    public void mouseReleased(MouseEvent mouseEvent) {

    }

    public void mouseMoved(MouseEvent mouseEvent) {

    }

    public void mouseDragged(MouseEvent mouseEvent) {

    }

    public void mouseWheelMoved(MouseEvent mouseEvent) {

    }
}
