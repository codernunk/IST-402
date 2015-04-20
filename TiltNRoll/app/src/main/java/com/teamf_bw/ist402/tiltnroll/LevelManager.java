package com.teamf_bw.ist402.tiltnroll;

import android.content.res.Resources;

/**
 * Created by Jesse on 4/20/2015.
 */
public class LevelManager {

    public static void loadLevel(){
        String result;
        try {
            Resources res = getResources();
            InputStream in_s = res.openRawResource(R.raw.yourfile);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            result = new String(b);
        } catch (Exception e) {
            // e.printStackTrace();
            result = "Error: can't show file.";
        }
    }
}
