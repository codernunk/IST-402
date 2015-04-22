package com.teamf_bw.ist402.tiltnroll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * This class contains all the level objects as well
 * as the utility for loading levels.
 *
 * Created by Jesse on 4/20/2015.
 */
public class LevelManager {

    // Object type constants
    public static final int TYPE_PLAYER = 0; // The number in the level .csv file that represents the player object
    public static final int TYPE_WALL = 1; // The number in the level .csv file that represents the wall object
    public static final int TYPE_GOAL = 2; // The number in the level .csv file that represents the goal object
    public static final int TYPE_DEATH = 3; // The number in the level .csv file that represents the death object

    // Map constants
    public static final int MAP_TILES_X = 24; // The number of tiles to be placed horizontally
    public static final int MAP_TILES_Y = 15; // Note: This is unused, but kept because it is a good reference.
    public static final int IMAGE_SIZE = 80;

    // Stores the list of levels in an array for later access
    private static int[] levels = {
            R.raw.level1,
            R.raw.level2
    };

    // Variables for managing the levels and objects
    private int levelId;
    private ArrayList<GameObject> gameObjects;

    /**
     * The constructor is protected because we want to use the
     * loadLevel method to create LevelManagers instead.
     * @param levelId
     * @param objects
     */
    protected LevelManager(int levelId, ArrayList<GameObject> objects){
        this.levelId = levelId;
        gameObjects = objects;
    }

    /**
     * Getter for getting the game objects on the scene.
     * @return
     */
    public ArrayList<GameObject> getGameObjects(){
        return gameObjects;
    }

    /**
     * Loads a new level with new objects.
     * @param con The context in which the level is loaded, such as a View or an Activity
     * @param level The number in which level to load.  It is zero relative
     * @return a new LevelManager object
     */
    public static LevelManager loadLevel(Context con, int level){
        ArrayList<GameObject> objects = new ArrayList<GameObject>();

        Bitmap ballImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.ball);
        Bitmap deathImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.death);
        Bitmap goalImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.goal);
        Bitmap wallImage = BitmapFactory.decodeResource(con.getResources(), R.drawable.wall);

        try {
            Resources res = con.getResources();
            InputStream in_s = res.openRawResource(levels[level]);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);

            String levelData = new String(b);

            levelData = levelData.replace("\r","");
            levelData = levelData.replace("\n",",");

            String[] data = levelData.split(",");

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
            return new LevelManager(level,objects);
        } catch (Exception e) {
            // The file doesn't exist or another error occurred
        }
        return null;
    }

    /**
     * Checks to see if there are any more levels in the game.
     * @param levelId The level id in which to test.
     * @return true if that level exists; false if it does not.
     */
    public static boolean levelExists(int levelId){
        return levelId >= 0 && levelId < levels.length;
    }
}
