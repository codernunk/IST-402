package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * This class holds the information for a basic GameObject.
 * This class holds enough information to be drawn on the screen.
 * Created by Jesse on 4/15/2015.
 */
public abstract class GameObject implements Comparable<GameObject> {
    private int depth = 0;
    private Bitmap image;

    protected float x;
    protected float y;

    public GameObject(Bitmap image, float x, float y, int depth){
        this.image = image;
        this.x = x;
        this.y = y;
        this.depth = depth;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getDepth() {
        return depth;
    }

    public int compareTo(GameObject other){
        return this.depth - other.getDepth();
    }

    public boolean collision(GameObject other, boolean isPrecise){

        if (isPrecise){

        }else{
            float sourceTop = getY();
            float sourceLeft = getX();
            float sourceRight = getImage().getWidth();
            float sourceBottom = getImage().getHeight();

            float targetTop = other.getY();
            float targetLeft = other.getX();
            float targetRight = other.getImage().getWidth();
            float targetBottom = other.getImage().getHeight();


        }

        return false;
    }

    public abstract void update();
}
