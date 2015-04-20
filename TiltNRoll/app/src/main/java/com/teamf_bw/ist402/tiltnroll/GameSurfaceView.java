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
    private Paint paint;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private ArrayList<GameObject> gameObjects;

    public GameSurfaceView(Context context) {
        super(context);

        paint = new Paint();
        this.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        gameLoopThread = new GameLoopThread(this);
        gameObjects = LevelManager.loadLevel(context,0);//new ArrayList<GameObject>();
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
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
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format,
                                       int width, int height) {
            }
        });

        //newLevel(0);
    }

    protected void newLevel(int level){
        gameObjects = LevelManager.loadLevel(getContext(),level);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Updates all the game objects based on their individual methods
        update();

        // Draw the base color
        canvas.drawColor(Color.CYAN);

        // Sort the game objects by depth and draw them in that order.
        Collections.sort(gameObjects);
        // Draw all the game objects
        for (GameObject g: gameObjects){
            canvas.drawBitmap(g.getImage(), g.getX(), g.getY(), null);
            paint.setTextSize(48);
            paint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText("Score: 0", 10, 40, paint);
            canvas.drawText("Level 1", 10, canvas.getHeight()-10, paint);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText("Time: 60", canvas.getWidth()/2, 40, paint);
        }
    }

    /**
     * Updates all the Game Objects.
     */
    protected void update(){
        for (GameObject g: gameObjects){
            g.update(gameObjects);
        }
    }
}