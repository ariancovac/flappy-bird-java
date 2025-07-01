package front;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class Config {
 
//  -----------------BOARD AND SCALES-----------------
    
    public static final int REF_WIDTH = 1080, REF_HEIGHT = 1920;
    public static final int BOARD_WIDTH = 360, BOARD_HEIGHT = 640;
    public static final float scaleX = (float) BOARD_WIDTH / REF_WIDTH; //escala sobre una resolucion de referencia
    public static final float scaleY = (float) BOARD_HEIGHT / REF_HEIGHT;

//  --------------------FRAMETIMES--------------------

    public static final int FPS = Math.max(getRefreshRate(), 60);
    public static final double TARGET_FRAMETIME_NS = 1_000_000_000.0 / FPS;
    public static final float TARGET_FRAMETIME_S= 1f / FPS;
    public long fpsTimer = 0;
    public int frames = 0, currentFPS = 0;

//  ------------------PIPES AND BIRD------------------

    public static final int PIPE_HEIGHT = (int) (1536 * scaleY);
    public static final int PIPE_WIDTH = PIPE_HEIGHT / 8;
    public static final int PIPE_VELOCITY = (int) (-scaleY * 1024);
    public static final int PIPE_VELOCITY_FPS = PIPE_VELOCITY / FPS;
    public static final int PIPE_INTERVAL = 1500;
    public static final int GAP = BOARD_HEIGHT / 4;
    public static final int PIPE_HEIGHT_DIV2 = PIPE_HEIGHT / 2;
    public static final int PIPE_HEIGHT_GAP = PIPE_HEIGHT + GAP;

//  ----------------------PHYSICS----------------------

    public static final float GRAVITY = 1500f * scaleY * TARGET_FRAMETIME_S;
    public static final float JUMP_VELOCITY     = -GAP * 2f;
    public static final float MAX_FALL_VELOCITY =  GAP * 3f;           //maxima velocidad a la que puede caer
    public static final float MAX_JUMP_VELOCITY = BOARD_HEIGHT;        //maxima velocidad a la que puede saltar

//  ------------------------MISC-----------------------

    public static final int MAX_ENTRIES = 10;

    public static int getRefreshRate() {    //obtiene la tasa de refresco de la pantalla
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        DisplayMode mode = device.getDisplayMode();
        return mode.getRefreshRate();
    }
}
