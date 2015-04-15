package com.teamf_bw.ist402.tiltnroll;

import android.content.res.Resources;
import android.graphics.Bitmap;

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

    public void update(){
        x += xVel;
        y += yVel;
    }
}
