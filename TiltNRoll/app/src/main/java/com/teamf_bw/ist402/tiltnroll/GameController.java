package com.teamf_bw.ist402.tiltnroll;

/**
 * Singleton class to access all the global game values
 * Created by Jesse on 4/17/2015.
 */
public class GameController {

    public static final String TAG_GAME = "Game";

    private GameController instance;

    private int score;
    private int lives;

    public GameController(){
        resetGame();
    }

    public GameController getInstance(){
        if (instance == null)
            instance = new GameController();

        return instance;
    }

    public void resetGame(){
        score = 0;
        lives = 3;
    }
}
