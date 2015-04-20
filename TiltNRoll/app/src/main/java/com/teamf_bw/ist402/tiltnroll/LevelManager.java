package com.teamf_bw.ist402.tiltnroll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Jesse on 4/20/2015.
 */
public class LevelManager {

    public static final int TYPE_PLAYER = 0;
    public static final int TYPE_WALL = 1;
    public static final int TYPE_GOAL = 2;
    public static final int TYPE_DEATH = 3;

    public static final int MAP_TILES_X = 24;
    public static final int MAP_TILES_Y = 15;

    public static final int IMAGE_SIZE = 80;

    // Stores the list of levels in an array for later access
    private static int[] levels = {
            R.raw.level1,
            R.raw.level2
    };

    public static ArrayList<GameObject> loadLevel(Context con, int level){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();
        String leveldata;

        Bitmap ballImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.ball);
        Bitmap deathImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.death);
        Bitmap goalImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.goal);
        Bitmap wallImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.wall);

        try {
            Resources res = con.getResources();
            InputStream in_s = res.openRawResource(levels[level]);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);

            leveldata = new String(b);

            leveldata = leveldata.replace("\r","");
            leveldata = leveldata.replace("\n",",");

            String[] data = leveldata.split(",");

            for (int i = 0; i < data.length; i++){
                int objType = Integer.parseInt(data[i]);

                int xPos = (i % MAP_TILES_X) * IMAGE_SIZE;
                int yPos = (i / MAP_TILES_X) * IMAGE_SIZE;

                switch(objType){
                    case TYPE_PLAYER:
                        Player p = new Player(ballImage,xPos,yPos);
                        objects.add(p);
                        break;
                    case TYPE_WALL:
                        Wall w = new Wall(wallImage,xPos,yPos);
                        objects.add(w);
                        break;
                    case TYPE_GOAL:
                        Goal g = new Goal(goalImage,xPos,yPos);
                        objects.add(g);
                        break;
                    case TYPE_DEATH:
                        Death d = new Death(deathImage,xPos,yPos);
                        objects.add(d);
                        break;
                }
            }
            return objects;
        } catch (Exception e) {
            // The file doesn't exist
        }
        return null;
    }
}
