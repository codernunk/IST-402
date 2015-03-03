package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class InputActivity extends ActionBarActivity {

    private Story currentStory;
    private Story.StoryWord currentWord;
    private int currentWordIndex = 0;

    private TextView txtCommand;
    private EditText txtWordInput;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        currentStory = (Story)getIntent().getSerializableExtra(TitleScreenActivity.KEY_STORY);

        // Get the UI functions
        txtCommand = (TextView)findViewById(R.id.txtCommand);
        txtWordInput = (EditText)findViewById(R.id.txtWordInput);
        btnNext = (Button)findViewById(R.id.btnNext);

        // Updates the UI, plus, gets the current word we are working on
        updateUI();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtWordInput.getText().toString().equals("")){
                    // Get the value of the current word
                    currentWord.setValue(txtWordInput.getText().toString());

                    // Leave this activity and go to the story
                    if (currentWordIndex+1 == currentStory.getStoryWords().size())
                    {
                        gotoStoryActivity();
                    }else{
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
     * Updates the UI to show the current word type
     */
    private void updateUI(){
        // If we are on the last word, make sure to show "Finish" on the button
        if (currentWordIndex+1 == currentStory.getStoryWords().size()){
            btnNext.setText(getString(R.string.next_button_finish));
        }

        currentWord = currentStory.getStoryWords().get(currentWordIndex);

        txtCommand.setText("Please enter a "+currentWord.getWordType());
        txtWordInput.setText("");
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
