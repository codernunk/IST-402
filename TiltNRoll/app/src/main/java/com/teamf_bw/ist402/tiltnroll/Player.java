package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;
import android.os.Debug;
import android.util.Log;

import java.util.ArrayList;

/**
 * Represents the player in the game.
 * Created by Jesse on 4/15/2015.
 */
public class Player extends GameObject {

<<<<<<< Updated upstream
    private float xVel = 5f;
    private float yVel = 0.1f;

    private float lastX;
    private float lastY;
=======
    //private double mass = 1000; //superflous. Could be added in as a way to control ball response
    private double aX = (-Accelerometer.sensorValueX); //force of gravity, X direction
    private double aY = (-Accelerometer.sensorValueY); //force of gravity, Y direction

    private long timeStamp1 = 0;
    private long timeStamp2 = 0;

    private double xVel = .5;
    private double yVel = .1;
>>>>>>> Stashed changes

    public Player(Bitmap image, float x, float y){
        super(image,x,y,0);
        lastX = x;
        lastY = y;
    }

    public void update(ArrayList<GameObject> objectsInScene){
<<<<<<< Updated upstream
        lastX = x;
        lastY = y;
=======
        double t = 0; //time

        //getting "t" (the time between calls of this method)
        if(timeStamp1 != 0) {
            timeStamp2 = System.nanoTime();
            t = (timeStamp2 - timeStamp1) * 0.00000001; //nanoseconds to seconds
            timeStamp1 = 0;
        }

        timeStamp1 = System.nanoTime();

        aX = (-Accelerometer.sensorValueX); //force of gravity, X direction
        aY = (-Accelerometer.sensorValueY); //force of gravity, Y direction

        // v = v_0 + at (t should be small enough s.t. assuming linear acceleration is reasonable)
        xVel += aX * t;
        yVel += aY * t;
>>>>>>> Stashed changes

        x += xVel;
        y += yVel;

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



        for (GameObject go : objectsInScene){
<<<<<<< Updated upstream
            // Handle wall collisions
            if (go instanceof Wall){

                // Check for collisions on the y axis
                if (collision(go, false, (int)xVel, (int)yVel)){
/*                    if (go.getY() > y){
                        y = go.getY()-getImage().getHeight();
                        yVel *= -1;
                    }else{
                        y = go.getY()+go.getImage().getHeight();
                        yVel *= -1;
                    }
                    // Check for x axis collision - doesn't work
                    if (xVel > 0 && x < go.getX()+go.getImage().getWidth()){
                        xVel *= -1;
                    }
                    if (xVel < 0 && x+getImage().getWidth() > go.getX()){
                        xVel *= -1;
                    }*/
                    // Calculate collision angle
                    double angle = -Math.atan2(yVel,xVel);
                    float speed = (float)Math.sqrt(xVel * xVel + yVel * yVel);
                    //Log.d(GameController.TAG_GAME,"angle: "+angle);
                    xVel = (float)Math.cos(angle) * speed;
                    yVel = (float)Math.sin(angle) * speed;
                    x = lastX;
                    y = lastY;
=======
            if (go instanceof Wall && collision(go, false, 0, 0)){
                if (go.getY() > y){
                    y = go.getY()-getImage().getHeight();
                    yVel *= -0.7; //modified from 1 to provide some inelastic collision properties
                }else{
                    y = go.getY()+go.getImage().getHeight();
                    yVel *= -0.7;
>>>>>>> Stashed changes
                }

                //Log.d(GameController.TAG_GAME,"Collision with Wall "+getId().toString());
            }
        }
    }

}
