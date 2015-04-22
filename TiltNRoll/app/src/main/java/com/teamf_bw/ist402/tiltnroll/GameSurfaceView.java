package com.teamf_bw.ist402.tiltnroll;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class runs the main game.
 * Created by Jesse on 4/15/2015.
 */
public class GameSurfaceView extends SurfaceView {
    private Paint paint; // Used for drawing text
    private SurfaceHolder holder; // Used to listen for surface events
    private GameLoopThread gameLoopThread; // The thread used to run the game loop
    private LevelManager level; // Contains all the game objects

    // Gameplay variables
    // May want to move this to a different class
    private static final long TIME_LIMIT = 30000; // The time limit for the level
    private long startTime; // The time in which the level started
    private long currentTime; // The current time left

    public GameSurfaceView(Context context) {
        super(context);

        // Set the UI visibility to support Immersive mode, which will
        // make the game fullscreen
        this.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Instantiate a new Paint object, which is used for drawing text
        paint = new Paint();

        // Start a new game loop thread to run the game loop.
        gameLoopThread = new GameLoopThread(this);

        // Load the first level by default
        newLevel(0);

        // The Holder object is used to determine state changes in the SurfaceView
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // We want to stop the thread in case the surface is no longer
                // used.
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                // When the surface is created, start the game loop thread.
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });
    }

    @Override
    public void onDraw(Canvas canvas) {
        // Draw the base color
        canvas.drawColor(Color.CYAN);

        // Sort the game objects by depth and draw them in that order.
        Collections.sort(level.getGameObjects());

        // Draw all the game objects
        for (GameObject g : level.getGameObjects()) {
            canvas.drawBitmap(g.getImage(), g.getX(), g.getY(), null);
        }

        // Draw the UI texts
        drawTextWithShadow(canvas, "Score: "+GameController.getInstance().getScore(), 10, 64, 3, 3, 64, Paint.Align.LEFT);
        drawTextWithShadow(canvas, "Level "+(GameController.getInstance().getCurrentLevel()+1), 10, canvas.getHeight() - 10, 3, 3, 64, Paint.Align.LEFT);
        drawTextWithShadow(canvas, "Time: "+GameUtilities.formatTime(currentTime), canvas.getWidth() / 2, 64, 3, 3, 64, Paint.Align.CENTER);
    }

    /**
     * Updates all the Game Objects by calling each object's
     * update method.
     */
    public void onUpdate() {
        // Call all the game objects' update methods
        for (GameObject g : level.getGameObjects()) {
            g.update(level.getGameObjects());
        }

        // Calculate the current time
        currentTime = TIME_LIMIT - (System.currentTimeMillis() - startTime);

        if (currentTime <= 0){
            newLevel(GameController.getInstance().getCurrentLevel());
        }
    }

    /**
     * Starts a new level by loading the level objects.
     * @param levelId The level in which to load
     */
    protected void newLevel(int levelId) {
        level = LevelManager.loadLevel(getContext(), levelId);
        startTime = System.currentTimeMillis();
    }

    /**
     * Draws some text on the screen using the font alignment parameters
     * and text shadow.
     *
     * @param canvas The parent canvas object in which to draw the text
     * @param text The text to display
     * @param x The x position of the text
     * @param y The y position of the text
     * @param shadowXOffset The x offset of the text shadow
     * @param shadowYOffset The y offset of the text shadow
     * @param fontSize The size of the font
     * @param textAlign The orientation of the text (Left, Center, Right)
     */
    protected void drawTextWithShadow(Canvas canvas, String text, int x, int y, int shadowXOffset, int shadowYOffset, int fontSize, Paint.Align textAlign) {
        // Set some of the values on the Paint object
        paint.setTextSize(fontSize);
        paint.setFakeBoldText(true);
        paint.setTextAlign(textAlign);

        // Draw the shadow first
        paint.setColor(Color.BLACK);
        canvas.drawText(text, x + shadowXOffset, y + shadowYOffset, paint);

        // Then draw the text
        paint.setColor(Color.LTGRAY);
        canvas.drawText(text, x, y, paint);
    }
}