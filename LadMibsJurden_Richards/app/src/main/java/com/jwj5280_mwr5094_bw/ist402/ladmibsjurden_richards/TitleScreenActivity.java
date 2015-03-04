package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Random;

/**
 * TitleScreenActivity.java
 * Jesse Jurden, Mike Richards
 *
 * March 6, 2015
 *
 * This activity greets the user and also gets the
 * story to use on the InputActivity.
 */

public class TitleScreenActivity extends ActionBarActivity {

    // Tags for LogCat
    public static final String KEY_STORY = "Story";

    // Title Screen Start button
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        // Button Event Listener
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                // After reading the stories from the file and choosing one at random, we will send the
                // chosen story to the next screen (MOVED TO INPUTACTIVITY).
                //chosenStory.parseStory();// Parse this story to get all of its words
                Intent i = new Intent(TitleScreenActivity.this,InputActivity.class);
                //i.putExtra(KEY_STORY,chosenStory);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_title_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
