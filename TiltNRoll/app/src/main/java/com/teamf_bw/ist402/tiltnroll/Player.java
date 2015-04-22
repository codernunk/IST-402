package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

/**
 * Represents the player in the game.
 * Created by Jesse on 4/15/2015.
 */
public class Player extends GameObject {

    private float xVel = 1f;
    private float yVel = 1f;

    public Player(Bitmap image, float x, float y){
        super(image,x,y,0);
    }

    public void update(ArrayList<GameObject> objectsInScene){
        x += xVel;
        y += yVel;

        for (GameObject go : objectsInScene){
            // Handle wall collisions
            if (go instanceof Wall){

                // Check for collisions on the y axis
                if (collision(go, false, 0, 0)){
                    if (go.getY() > y){
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
                    }
                }

                //Log.d(GameController.TAG_GAME,"Collision with Wall "+getId().toString());
            }
        }
    }
}
