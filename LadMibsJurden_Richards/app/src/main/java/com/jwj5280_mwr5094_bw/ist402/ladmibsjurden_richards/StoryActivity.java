package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/**
 * StoryActivity.java
 * Jesse Jurden, Mike Richards
 *
 * March 6, 2015
 *
 * This is the final activity that will display the finished story.
 */
public class StoryActivity extends ActionBarActivity {

    private Story currentStory;

    private Button btnDone;
    private TextView txtStoryTitle;
    private TextView txtStoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        currentStory = (Story)getIntent().getSerializableExtra(TitleScreenActivity.KEY_STORY);

        btnDone = (Button)findViewById(R.id.btnDone);
        txtStoryTitle = (TextView) findViewById(R.id.txtStoryTitle);
        txtStoryText = (TextView) findViewById(R.id.txtStoryText);

        updateUI();

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StoryActivity.this,TitleScreenActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_story, menu);
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
     * Updates the UI to reflect the story.
     */
    private void updateUI(){
        txtStoryTitle.setText(currentStory.getTitle());
        String completedStory = currentStory.getCompletedStory();
        txtStoryText.setText(completedStory);
    }
}
