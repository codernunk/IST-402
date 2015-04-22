package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;
import android.graphics.Color;
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

    public boolean collision(GameObject other, boolean isPrecise, int xOffset, int yOffset){

        // Get the rectangle and bounds of the source object (the caller)
        int sourceTop = (int)getY()+xOffset;
        int sourceLeft = (int)getX()+yOffset;
        int sourceRight = sourceLeft + (int)getImage().getWidth()+xOffset;
        int sourceBottom = sourceTop + (int)getImage().getHeight()+yOffset;

        Rect sourceBounds = new Rect(sourceLeft,sourceTop,sourceRight,sourceBottom);

        // Get the rectangle and bounds of the target object
        int targetTop = (int)other.getY();
        int targetLeft = (int)other.getX();
        int targetRight = targetLeft + (int)other.getImage().getWidth();
        int targetBottom = targetTop + (int)other.getImage().getHeight();

        Rect targetBounds = new Rect(targetLeft,targetTop,targetRight,targetBottom);

        if (isPrecise){

            if (sourceBounds.intersect(targetBounds)){
                Rect collisionBounds = getCollisionBounds(sourceBounds, targetBounds);
                for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                    for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                        int bitmap1Pixel = getImage().getPixel(i, j);
                        int bitmap2Pixel = other.getImage().getPixel(i, j);
                        if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                            return true;
                        }
                    }
                }
            }
        }else{
            return sourceBounds.intersect(targetBounds);
        }

        return false;
    }

    public GameObject collisionAtPosition(ArrayList<GameObject> objectsInScene, int xOffset, int yOffset){
        // Get the rectangle and bounds of the source object (the caller)
        int sourceTop = (int)getY()+xOffset;
        int sourceLeft = (int)getX()+yOffset;
        int sourceRight = sourceLeft;
        int sourceBottom = sourceTop;

        Rect sourceBounds = new Rect(sourceLeft,sourceTop,sourceRight,sourceBottom);

        for (GameObject other: objectsInScene){
            // Get the rectangle and bounds of the target object
            int targetTop = (int)other.getY();
            int targetLeft = (int)other.getX();
            int targetRight = targetLeft + (int)other.getImage().getWidth();
            int targetBottom = targetTop + (int)other.getImage().getHeight();

            Rect targetBounds = new Rect(targetLeft,targetTop,targetRight,targetBottom);

            if (targetBounds.intersect(sourceBounds))
                return other;
        }
        return null;
    }

    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = (int) Math.max(rect1.left, rect2.left);
        int top = (int) Math.max(rect1.top, rect2.top);
        int right = (int) Math.min(rect1.right, rect2.right);
        int bottom = (int) Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }

    public abstract void update(ArrayList<GameObject> objectsInScene);
}
