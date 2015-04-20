package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Represents a wall in which the player can collide.
 * Created by Jesse on 4/15/2015.
 */
public class Death extends GameObject {

    public Death(Bitmap image, float x, float y){
        super(image,x,y,-20);
    }

    public void update(ArrayList<GameObject> objectsInScene){
    }
}
