package org.graphics;

import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import org.engine.GameLoop;
import org.input.Keyboard;
import org.input.Mouse;
import org.world.World;

public class Render implements GLEventListener {
    public static final int virtual_width = 1280, virtual_height = 720;
    private static GLWindow window;
    private static GLProfile profile;
    // Determines whether VSync is enabled
    public static boolean enableVsync = true;
    private static GL2 gl2;
    public static float unitsWide = 100, unitsTall;
    private static float cameraX = 0, cameraY = 0;
    private static final GameLoop gameLoop = new GameLoop();

    public Render() {
        profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        window = GLWindow.create(capabilities);
        window.addGLEventListener(this);
        window.setSize(virtual_width, virtual_height);
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

    public static GLWindow getWindow(){
        return window;
    }

    public static void render(){
        if(window!=null)window.display();
    }

    static GL2 getGL2(){
        return gl2;
    }

    public void init(GLAutoDrawable glAutoDrawable) {
        GL2 gl = glAutoDrawable.getGL().getGL2();
        gl.glClearColor(0f, 0f, 0f, 1f);
        gl.glEnable(GL2.GL_BLEND);
        gl.glEnable(GL2.GL_TEXTURE_2D);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void dispose(GLAutoDrawable glAutoDrawable) {
        gameLoop.setRunning(false);
    }

    public void display(GLAutoDrawable glAutoDrawable) {
        gl2 = glAutoDrawable.getGL().getGL2();
        gl2.setSwapInterval(enableVsync ? 1 : 0);
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl2.glTranslatef(-cameraX, -cameraY, 0);
        World.INSTANCE.render();
        gl2.glTranslatef(cameraX, cameraY, 0);
    }

    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {
        GL2 gl=glAutoDrawable.getGL().getGL2();
        unitsTall=window.getHeight()/(window.getWidth()/unitsWide);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0.0f, unitsWide, 0.0f, unitsTall, 0.0f, 1.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
    }

    public static GameLoop getGameLoop() {
        return gameLoop;
    }

    public static void setCameraX(float cameraX) {
        Render.cameraX = cameraX;
    }

    public static void setCameraY(float cameraY) {
        Render.cameraY = cameraY;
    }

    public static float getCameraX() {
        return cameraX;
    }

    public static float getCameraY() {
        return cameraY;
    }
}
