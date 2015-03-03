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


public class TitleScreenActivity extends ActionBarActivity {

    public static final String TAG_ERROR = "Error";

    public static final String KEY_STORY = "Story";

    private Button btnStart;

    private ArrayList<Story> stories;
    private Story chosenStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        // Get the stories from the XML file and choose one at random
        stories = readStoryFile();
        chosenStory = stories.get(new Random().nextInt(stories.size()));

        // Button Event Listener
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                // After reading the stories from the file and choosing one at random, we will send the
                // chosen story to the next screen.
                chosenStory.parseStory();// Parse this story to get all of its words
                Intent i = new Intent(TitleScreenActivity.this,InputActivity.class);
                i.putExtra(KEY_STORY,chosenStory);
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

    /**
     * Reads the Story XML and returns an array of Story objects.
     * @return
     */
    private ArrayList<Story> readStoryFile(){
        try{
            Resources res = getResources();
            XmlResourceParser xrp = res.getXml(R.xml.stories);

            ArrayList<Story> allStories = new ArrayList<Story>();
            Story story = null;

            while(xrp.next() != XmlResourceParser.END_DOCUMENT){
                if(xrp.getEventType() != XmlResourceParser.START_TAG)
                    continue;

                String elementName = xrp.getName();

                // If we find a "story" tag, this marks a new story.
                // We will create a new Story object.
                if (elementName.equals("story")){
                    if (story != null)
                        allStories.add(story);
                    story = new Story();
                }

                // We will set the title of the story
                if (elementName.equals("title")) {
                    String title = xrp.nextText();
                    story.setTitle(title);
                }

                // We will apply the story text to the current object
                if (elementName.equals("text")) {
                    String text = xrp.nextText();
                    story.setText(text);
                }
            }
            // Add the last story
            allStories.add(story);

            return allStories;
        }catch (Exception e){
            Log.e(TAG_ERROR, e.toString());
        }
        return null;
    }
}
