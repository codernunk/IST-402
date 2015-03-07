package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * InputActivity.java
 * Jesse Jurden, Mike Richards
 *
 * March 6, 2015
 *
 * This activity is responsible for getting the user
 * input in order to fill out the lad mib.
 */

public class InputActivity extends ActionBarActivity {

    // Keys for the bundle
    public static final String KEY_CURRENT_STORY = "KeyCurrentStory";
    public static final String KEY_CURRENT_INDEX = "KeyCurrentIndex";

    // Tags for LogCat
    public static final String TAG_ERROR = "Error";

    // Story variables
    private Story currentStory; // The current Story object we are referencing
    private Story.StoryWord currentWord; // The current word that the player will input
    private int currentWordIndex = 0; // The current word index

    private TextView txtCommand; // The text that shows what kind of word to enter
    private EditText txtWordInput; // The user input area
    private Button btnPrevious; // The previous button
    private Button btnNext; // The next button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        // Check the saved instance state in case we changed the view's rotation
        // in order to get the current progress
        if (savedInstanceState != null){
            currentStory = (Story)savedInstanceState.getSerializable(KEY_CURRENT_STORY);
            currentWordIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }else{
            // Get the current story and store it in a variable for later use
            currentStory = getStory();
            // Parse the story to get the words out of it
            currentStory.parseStory();
        }

        // Get the UI functions
        txtCommand = (TextView)findViewById(R.id.txtCommand);
        txtWordInput = (EditText)findViewById(R.id.txtWordInput);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnPrevious = (Button)findViewById(R.id.btnPrevious);

        // Updates the UI, plus, gets the current word we are working on
        updateUI();

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Move to the previous selection if possible
                if (currentWordIndex > 0){
                    currentWordIndex--;
                    updateUI();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtWordInput.getText().toString().equals("")){
                    // Get the value of the current word
                    currentWord.setValue(txtWordInput.getText().toString());

                    // If we are finished with the lad mib, we should go to the story activity
                    if (currentWordIndex+1 == currentStory.getStoryWords().size())
                    {
                        // Leave this activity and go to the story
                        gotoStoryActivity();
                    }else{
                        // Or... Keep going
                        currentWordIndex++;
                        updateUI();
                    }

                }else{
                    // Display to the user that they must enter something!
                    new Toast(InputActivity.this).makeText(InputActivity.this,"You must enter something!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_input, menu);
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
     * In case the user rotates the screen, we want to preserve their progress
     * @param savedInstanceState
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Put the current story and the current index
        savedInstanceState.putSerializable(KEY_CURRENT_STORY,currentStory);
        savedInstanceState.putInt(KEY_CURRENT_INDEX, currentWordIndex);
    }

    /**
     * Updates the UI to show the current word type
     */
    private void updateUI(){
        // If we are on the first word, do not show the previous button
        if (currentWordIndex == 0){
            btnPrevious.setVisibility(View.INVISIBLE);
        }else{
            btnPrevious.setVisibility(View.VISIBLE);
        }

        // If we are on the last word, make sure to show "Finish" on the button
        if (currentWordIndex+1 == currentStory.getStoryWords().size()){
            btnNext.setText(getString(R.string.next_button_finish));
        }else{
            btnNext.setText(R.string.next_button);
        }

        // Get the current word we are trying to get input for
        currentWord = currentStory.getStoryWords().get(currentWordIndex);

        // Show the user the type of word we're looking for
        txtCommand.setText("Please enter a "+currentWord.getWordType());
        // Set the value of the EditText to the value of the word so far (in case the user is backtracking)
        txtWordInput.setText(currentWord.getValue());
    }

    /**
     * Reads the Story XML,builds an array of story objects,
     * and returns a random story form the list.
     * @return
     */
    private Story getStory(){
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
            int choice = new Random().nextInt(allStories.size());

            return allStories.get(choice);
        }catch (Exception e){
            Log.e(TAG_ERROR, e.toString());
        }
        return null;
    }

    /**
     * Goes to the final Story Activity
     */
    private void gotoStoryActivity(){
        Intent i = new Intent(InputActivity.this,StoryActivity.class);
        i.putExtra(TitleScreenActivity.KEY_STORY,currentStory);
        startActivity(i);
    }
}
