package com.teamf_bw.ist402.tiltnroll;

/**
 * This class provides purely static utility methods for
 * common operations.
 * Created by Jesse on 4/22/2015.
 */
public class GameUtilities {

    /**
     * Returns a string with the time formatted in seconds.
     * @param millis
     * @return
     */
    public static String formatTime(long millis){
        float seconds = (float)millis/1000;
        return String.format("%.2f",seconds);
    }
}
