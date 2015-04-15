package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;

/**
 * Represents a wall in which the player can collide.
 * Created by Jesse on 4/15/2015.
 */
public class Wall extends GameObject {

    public Wall(Bitmap image, float x, float y){
        super(image,x,y,100);
    }

    public void update(){
    }
}
