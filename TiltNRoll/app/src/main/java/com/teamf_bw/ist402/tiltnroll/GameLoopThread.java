package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Canvas;

/**
 * This class runs the game loop.
 *
 * Resource: http://www.edu4java.com/en/androidgame/androidgame3.html
 * Created by Jesse on 4/15/2015.
 */
public class GameLoopThread extends Thread {
    public static final long FPS = 60;

    private GameSurfaceView view;
    private boolean running = false;

    public GameLoopThread(GameSurfaceView view) {
        this.view = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        long ticksPS = 1000 / FPS;
        long startTime;
        long sleepTime;
        while (running) {
            Canvas c = null;
            startTime = System.currentTimeMillis();
            try {
                c = view.getHolder().lockCanvas();
                synchronized (view.getHolder()) {
                    // Updates all the game objects based on their individual methods
                    view.onUpdate();
                    // Draw the objects on the screen
                    view.onDraw(c);
                }
            } finally {
                if (c != null) {
                    view.getHolder().unlockCanvasAndPost(c);
                }
            }
            sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
            try {
                if (sleepTime > 0)
                    sleep(sleepTime);
                else
                    sleep(10);
            } catch (Exception e) {}
        }
    }
}