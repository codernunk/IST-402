package com.teamf_bw.ist402.tiltnroll;

/**
 * Singleton class to access all the global game values
 * Created by Jesse on 4/17/2015.
 */
public class GameController {

    public static final String TAG_GAME = "Game";

    private static GameController instance;

    private int currentLevel;
    private int score;
    private int lives;

    public GameController(){
        resetGame();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public static GameController getInstance(){
        if (instance == null)
            instance = new GameController();

        return instance;
    }

    /**
     * Resets the game to the default values.
     */
    public void resetGame(){
        currentLevel = 0;
        score = 0;
        lives = 3;
    }

    /**
     * Goes to the next level in the sequence.
     */
    public void nextLevel(){
        if (LevelManager.levelExists(currentLevel))
            currentLevel ++;
        else
            currentLevel = 0; // Restart from the beginning
    }

    /**
     * Adds to the total score.
     * @param amount
     */
    public void addScore(int amount){
        score += amount;
    }

    /**
     * Subtracts a life from the player and also checks
     * for a Game Over condition.
     * @return true if the player is out of lives and it is now Game Over;
     * false if the player still has lives.
     */
    public boolean loseALife(){
        lives --;
        return lives < 0;
    }
}
