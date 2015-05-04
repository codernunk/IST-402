package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;

/**
 * Represents the player in the game.
 * Created by Jesse on 4/15/2015.
 */
public class Player extends GameObject {

    public static final float DAMPING_COEFFICIENT = 0.9f;
    public static final float MAX_SPEED = 8f;

    private GameSurfaceView viewContext; // Used to call the next level and reset level functions

    private float lastX; // Keeps track of the player's previous X position so it doesn't get stuck in a wall
    private float lastY; // Keeps track of the player's previous Y position so it doesn't get stuck in a wall

    //private double mass = 1000; //superflous. Could be added in as a way to control ball response
    private double aX; //force of gravity from the accelerometer, X direction
    private double aY; //force of gravity from the accelerometer, Y direction

    private long timeStamp1 = 0;
    private long timeStamp2 = 0;

    private double xVel = 0; // The horizontal velocity of the player
    private double yVel = 0; // The vertical velocity of the player

    private boolean isDead = false;

    private float scale = 1;

    public Player(Bitmap image, float x, float y, GameSurfaceView viewContext){
        super(image,x,y,0);
        lastX = x;
        lastY = y;
        this.viewContext = viewContext;

        scale = 1;
    }

    public void reset(){
        xVel = 0;
        yVel = 0;
        timeStamp1 = 0;
        timeStamp2 = 0;
        lastX = x;
        lastY = y;
        isDead = false;
        scale = 1;
    }

    /**
     * Updates the player.  This method will accelerate and translate
     * the player, as well as handle its collisions and death
     * animation.
     * @param objectsInScene
     */
    public void update(ArrayList<GameObject> objectsInScene) {

        // If the player is dead, animate it
        if (isDead){
            scale -= 0.05;

            // We have shown the death animation.  Now reset the level (but don't lose a life)
            if (scale <= 0){
                viewContext.resetLevel(false);
                reset();
            }
            return;
        }

        // Keep track of previous positions
        lastX = x;
        lastY = y;

        double t = 0; // time

        // getting "t" (the time between calls of this method)
        if (timeStamp1 != 0) {
            timeStamp2 = System.nanoTime();
            t = (timeStamp2 - timeStamp1) * 0.00000001; // nanoseconds to seconds
            timeStamp1 = 0;
        }

        timeStamp1 = System.nanoTime();

        aX = (-Accelerometer.sensorValueX); // force of gravity, X direction
        aY = (-Accelerometer.sensorValueY); // force of gravity, Y direction

        // v = v_0 + at (t should be small enough s.t. assuming linear acceleration is reasonable)
        // Increment the velocity to the accelerometer values
        xVel += aX * t * 0.2;
        yVel -= aY * t * 0.2;

        // Add the velocity to the player's position to make it move
        x += xVel;
        y += yVel;

        // Calculate collision angle and new speed vector
        // This can be used for collisions or for setting max speed
        double angle = Math.atan2(yVel, xVel);
        float speed = (float) Math.sqrt(xVel * xVel + yVel * yVel);

        // Limit the velocity if the ball goes too fast (for control purposes)
        if (speed > MAX_SPEED)
        {
            xVel = (float) Math.cos(angle) * MAX_SPEED * DAMPING_COEFFICIENT;
            yVel = (float) Math.sin(angle) * MAX_SPEED * DAMPING_COEFFICIENT;
        }

        /*
        Log.d( "accelerometer", ((Double)Accelerometer.sensorValueX).toString() );
        Log.d( "xAccel", ((Double)aX).toString() );
        Log.d( "yAccel", ((Double)aY).toString() );
        Log.d( "XVel", ((Double)xVel).toString() );
        Log.d( "yVel", ((Double)yVel).toString() );
        Log.d( "xxx", ((Float)x).toString() );
        Log.d( "yyy", ((Float)y).toString() );
        Log.d( "time", ((Double)t).toString() );
        */
        for (GameObject go : objectsInScene) {
            if (go instanceof Wall) { // Handle wall collisions
                // Check for collisions on the y axis
                if (collision(go, 0, 0)) {
                    double colAngle = Math.PI-angle;
                    double deg1 = Math.toDegrees(angle);
                    double angleInDegrees = Math.toDegrees(colAngle);

                    // Assign the new speed calculations to the the velocity
                    xVel = (float) Math.cos(colAngle) * speed * DAMPING_COEFFICIENT;
                    yVel = (float) Math.sin(colAngle) * speed * DAMPING_COEFFICIENT;

                    // Move the player to its previous position so it does not get stuck in a wall
                    x = lastX;
                    y = lastY;
                }
            }else if (go instanceof Goal){ // Handle goal
                if (collisionCenterPixel(go)) {
                    viewContext.nextLevel();
                }
            }else if (go instanceof Death){ // Handle death
                if (collisionCenterPixel(go)) {
                    Log.d("Game","I am dead");
                    isDead = true;
                }
            }
        }
    }

    /**
     * We override the draw method for the player because
     * of the scaling death animation.
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas){
        Matrix mt = new Matrix();
        mt.postScale(scale, scale);
        mt.postTranslate(x, y);
        canvas.drawBitmap(getImage(), mt, null);
    }
}
