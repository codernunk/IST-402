package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This class holds the information for a basic GameObject.
 * This class holds enough information to be drawn on the screen.
 * Created by Jesse on 4/15/2015.
 */
public abstract class GameObject implements Comparable<GameObject> {
    private UUID id;
    private int depth = 0;
    private Bitmap image;

    protected float x;
    protected float y;

    public GameObject(Bitmap image, float x, float y, int depth){
        id = UUID.randomUUID();
        this.image = image;
        this.x = x;
        this.y = y;
        this.depth = depth;
    }

    public UUID getId() {
        return id;
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
            int sourceTop = (int)getY();
            int sourceLeft = (int)getX();
            int sourceRight = sourceLeft + (int)getImage().getWidth();
            int sourceBottom = sourceTop + (int)getImage().getHeight();

            Rect sourceBounds = new Rect(sourceLeft,sourceTop,sourceRight,sourceBottom);

            int targetTop = (int)other.getY();
            int targetLeft = (int)other.getX();
            int targetRight = targetLeft + (int)other.getImage().getWidth();
            int targetBottom = targetTop + (int)other.getImage().getHeight();

            Rect targetBounds = new Rect(targetLeft,targetTop,targetRight,targetBottom);

            return sourceBounds.intersect(targetBounds);
        }

        return false;
    }

    public abstract void update(ArrayList<GameObject> objectsInScene);
}
