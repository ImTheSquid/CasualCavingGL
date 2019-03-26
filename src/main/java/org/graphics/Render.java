package org.graphics;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import org.engine.GameLoop;
import org.input.Keyboard;
import org.input.Mouse;
import org.world.World;

public class Render implements GLEventListener {
    private static GLWindow window;
    private static GLProfile profile;
    private static final int screenWidth=1280,screenHeight=720;
    private static GL2 gl2;
    private static float unitsWide=10,unitsTall;
    private static float cameraX=0,cameraY=0;
    private static GameLoop gameLoop=new GameLoop();
    public Render(){
        profile=GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities=new GLCapabilities(profile);
        window=GLWindow.create(capabilities);
        window.addGLEventListener(this);
        window.setSize(screenWidth,screenHeight);
        window.setTitle("Casual Caving");
        window.setDefaultCloseOperation(WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
        window.addKeyListener(new Keyboard());
        window.addMouseListener(new Mouse());
        window.setVisible(true);
        gameLoop.start();
    }

    public static GLProfile getProfile() {
        return profile;
    }

    public static void render(){
        if(window!=null)window.display();
    }

    public static GL2 getGL2(){
        return gl2;
    }

    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl=glAutoDrawable.getGL().getGL2();
        gl.glClearColor(.25f,.25f,.25f,1);
        gl.glEnable(GL2.GL_BLEND);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA,GL2.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {
        gameLoop.setRunning(false);
    }

    public void display(GLAutoDrawable glAutoDrawable) {
        gl2=glAutoDrawable.getGL().getGL2();
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl2.glTranslatef(-cameraX,-cameraY,0);
        World.render();
        gl2.glTranslatef(cameraX,cameraY,0);
    }

    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl=glAutoDrawable.getGL().getGL2();
        if(window.getWidth()!=screenWidth||window.getHeight()!=screenHeight)window.setSize(screenWidth,screenHeight);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        unitsTall=window.getHeight()/(window.getWidth()/unitsTall);
        gl.glOrthof(-unitsWide/2,unitsWide/2,-unitsTall/2,unitsTall/2,-1,1);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }
}
