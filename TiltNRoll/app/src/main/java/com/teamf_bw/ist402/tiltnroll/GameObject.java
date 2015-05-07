package com.teamf_bw.ist402.tiltnroll;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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

    public GameObject(Bitmap image, float x, float y, int depth) {
        id = UUID.randomUUID();
        this.image = image;
        this.x = x;
        this.y = y;
        this.depth = depth;
    }

    public UUID getId() { return id; }

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

    public void setY(float y) { this.y = y; }

    public int getDepth() { return depth; }

    /**
     * Compares other objects by depth for drawing.
     * @param other
     * @return
     */
    public int compareTo(GameObject other) {
        return this.depth - other.getDepth();
    }

    /**
     * Checks to see if there is a collision with another
     * object.
     * @param other
     * @param xOffset
     * @param yOffset
     * @return
     */
    public boolean collision(GameObject other, int xOffset, int yOffset) {

        // Get the rectangle and bounds of the source object (the caller)
        // The additional 1s are to shrink the collision box so the player can fit through
        // the blocks
        int sourceTop = (int) getY() + yOffset + 2;
        int sourceLeft = (int) getX() + xOffset + 2;
        int sourceRight = sourceLeft + (int) getImage().getWidth() + xOffset - 2;
        int sourceBottom = sourceTop + (int) getImage().getHeight() + yOffset - 2;

        Rect sourceBounds = new Rect(sourceLeft, sourceTop, sourceRight, sourceBottom);

        // Get the rectangle and bounds of the target object
        int targetTop = (int) other.getY();
        int targetLeft = (int) other.getX();
        int targetRight = targetLeft + (int) other.getImage().getWidth();
        int targetBottom = targetTop + (int) other.getImage().getHeight();

        Rect targetBounds = new Rect(targetLeft, targetTop, targetRight, targetBottom);

        if (sourceBounds.intersect(targetBounds)) {
            for (int i = sourceBounds.left; i < sourceBounds.right; i++) {
                for (int j = sourceBounds.top; j < sourceBounds.bottom; j++) {
                    if (getImage().getPixel(i - sourceBounds.left, j - sourceBounds.top) !=
                            Color.TRANSPARENT) {
                        if (other.getImage().getPixel(i - targetBounds.left, j - targetBounds.top) !=
                                Color.TRANSPARENT) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks to see if there is a rectangle collision
     * specified by the center pixel of the source object
     * (for situations such as colliding with Death and
     * Goal objects).  This prevents the player from
     * dying if it touches the every edge of the object.
     * Rather, this will return true if half of the object
     * is in the collision.
     * @param other
     * @return
     */
    public boolean collisionCenterPixel(GameObject other) {

        // Get the center of the source object
        int centerX = (int) getX() + getImage().getWidth()/2;
        int centerY = (int) getY() + getImage().getHeight()/2;

        // Get the rectangle and bounds of the target object
        int targetTop = (int) other.getY();
        int targetLeft = (int) other.getX();
        int targetRight = targetLeft + (int) other.getImage().getWidth();
        int targetBottom = targetTop + (int) other.getImage().getHeight();

        Rect targetBounds = new Rect(targetLeft, targetTop, targetRight, targetBottom);

        return (targetBounds.contains(centerX,centerY));
    }

    /**
     * Draws the object to the canvas.
     * Note: This can be overridden to do
     * more if necessary.
     * @param canvas
     */
    public void draw(Canvas canvas){
        Matrix mt = new Matrix();
        //mt.postScale(canvas.getWidth()/1920,canvas.getHeight()/1200);
        mt.postTranslate(x, y);
        canvas.drawBitmap(image, mt, null);
    }

    /**
     * Updates the GameObject to run some code every time
     * the game loop thread is updated.
     * @param objectsInScene
     */
    public abstract void update(ArrayList<GameObject> objectsInScene);
}
