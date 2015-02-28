package com.jwj5280_mwr5094_bw.ist402.ladmibsjurden_richards;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class TitleScreenActivity extends ActionBarActivity {

    private Button btnStart;

    private Story chosenStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_screen);

        readStoryFile();

        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TitleScreenActivity.this,InputActivity.class);
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

                if (elementName.equals("story")){
                    if (story != null)
                        allStories.add(story);
                    story = new Story();
                }
                if (elementName.equals("title")) {
                    String title = xrp.nextText();
                    story.setTitle(title);
                }
                if (elementName.equals("text")) {
                    String text = xrp.nextText();
                    story.setText(text);
                }
            }

            return allStories;
        }catch (Exception e){
            Log.e("test",e.toString());
        }
        return null;
    }
}
