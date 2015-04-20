package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Represents a wall in which the player can collide.
 * Created by Jesse on 4/15/2015.
 */
public class Goal extends GameObject {

    public Goal(Bitmap image, float x, float y){
        super(image,x,y,-10);
    }

    public void update(ArrayList<GameObject> objectsInScene){
    }
}
