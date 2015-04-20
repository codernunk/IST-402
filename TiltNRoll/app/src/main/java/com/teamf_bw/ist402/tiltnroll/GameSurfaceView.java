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
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class runs the main game.
 * Created by Jesse on 4/15/2015.
 */
public class GameSurfaceView extends SurfaceView {
    private Bitmap ballImage;
    private Bitmap deathImage;
    private Bitmap goalImage;
    private Bitmap wallImage;

    private Paint paint;

    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private ArrayList<GameObject> gameObjects;

    private Player player;

    public GameSurfaceView(Context context) {
        super(context);
        paint = new Paint();
        gameLoopThread = new GameLoopThread(this);
        gameObjects = new ArrayList<GameObject>();
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

        // Load all the bitmaps
        ballImage = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        deathImage = BitmapFactory.decodeResource(getResources(), R.drawable.death);
        goalImage = BitmapFactory.decodeResource(getResources(), R.drawable.goal);
        wallImage = BitmapFactory.decodeResource(getResources(), R.drawable.wall);

        // Create a player
        player = new Player(ballImage, 160, 160);
        gameObjects.add(player);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        // Create a wall
        for (int i = 0; i < 15; i++){
            Wall w = new Wall(wallImage, 0, i*wallImage.getHeight());
            gameObjects.add(w);
            Wall w2 = new Wall(wallImage, display.getWidth()-80, i*wallImage.getHeight());
            gameObjects.add(w2);
        }
        for (int i = 0; i < 24; i++){
            Wall w = new Wall(wallImage, i * wallImage.getWidth(), 0);
            gameObjects.add(w);
            Wall w2 = new Wall(wallImage, i * wallImage.getWidth(), display.getHeight()-80);
            gameObjects.add(w2);
        }
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